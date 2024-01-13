package net.spectrum.oauth2.pkce;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.oauth2.CustomLoginTrailService;
import net.spectrum.oauth2.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class PkceAuthorizationCodeServices implements AuthorizationCodeServices {

    private final RandomValueStringGenerator generator = new RandomValueStringGenerator();

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    ClientDetailsService clientDetailsService;

    @Autowired
    CustomLoginTrailService customLoginTrailService;

    @Autowired
    RedisClient redisClient;

    @Autowired
    Environment env;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        PkceProtectedAuthentication protectedAuthentication = getProtectedAuthentication(authentication);
        generator.setLength(32);
        String code = generator.generate();
        redisClient.set(code, protectedAuthentication, Long.parseLong(env.getProperty("auth.code.ttl")));
        return code;
    }

    private PkceProtectedAuthentication getProtectedAuthentication(OAuth2Authentication authentication) {
        Map<String, String> requestParameters = authentication.getOAuth2Request().getRequestParameters();

        if (isPublicClient(requestParameters.get("client_id")) && !requestParameters.containsKey("code_challenge")) {
            throw new InvalidRequestException("Code challenge required.");
        }

        if (requestParameters.containsKey("code_challenge")) {
            String codeChallenge = requestParameters.get("code_challenge");
            CodeChallengeMethod codeChallengeMethod = getCodeChallengeMethod(requestParameters);

            customLoginTrailService.customLoginTrail(authentication, requestParameters.get("client_id"));

            return new PkceProtectedAuthentication(codeChallenge, codeChallengeMethod, authentication);
        }
        return new PkceProtectedAuthentication(authentication);
    }

    private CodeChallengeMethod getCodeChallengeMethod(Map<String, String> requestParameters) {
        try {
            return Optional.ofNullable(requestParameters.get("code_challenge_method"))
                    .map(String::toUpperCase)
                    .map(CodeChallengeMethod::valueOf)
                    .orElse(CodeChallengeMethod.PLAIN);
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestException("Transform algorithm not supported");
        }
    }

    private boolean isPublicClient(String clientId) {
        String clientSecret = clientDetailsService.loadClientByClientId(clientId).getClientSecret();
        return clientSecret == null || passwordEncoder.matches("", clientSecret);
    }

    public OAuth2Authentication consumeAuthorizationCodeAndCodeVerifier(String code, String verifier) {
        log.info("getting data by authorization code from redis, {}", code);
        PkceProtectedAuthentication readValue = (PkceProtectedAuthentication) redisClient.get(code);
        log.info("received data by authorization code from redis, {}", readValue);
        if(readValue == null)
            throw new InvalidGrantException("Exception occurred during getting auth code from redis");
        return readValue.getAuthentication(verifier);
    }

    @Override
    public OAuth2Authentication consumeAuthorizationCode(String code) {
        throw new UnsupportedOperationException();
    }
}
