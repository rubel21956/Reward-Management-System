package net.spectrum.oauth2.pkce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.io.Serializable;

@Slf4j
public class PkceProtectedAuthentication implements Serializable{
    private final String codeChallenge;
    private final CodeChallengeMethod codeChallengeMethod;
    private final OAuth2Authentication authentication;

    public PkceProtectedAuthentication(OAuth2Authentication authentication) {
        this.codeChallenge = null;
        this.codeChallengeMethod = CodeChallengeMethod.NONE;
        this.authentication = authentication;
    }

    public PkceProtectedAuthentication(String codeChallenge, CodeChallengeMethod codeChallengeMethod, OAuth2Authentication authentication) {
        this.codeChallenge = codeChallenge;
        this.codeChallengeMethod = codeChallengeMethod;
        this.authentication = authentication;
    }

    public OAuth2Authentication getAuthentication(String codeVerifier) {
        log.info("code verifier: {}", codeVerifier);
        if (codeChallengeMethod == CodeChallengeMethod.NONE) {
            return authentication;
        } else if (codeChallengeMethod.transform(codeVerifier).equals(codeChallenge)) {
            return authentication;
        } else {
            throw new InvalidGrantException("Invalid code verifier.");
        }
    }
}
