package net.spectrum.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class CustomLoginTrailService {

    @Autowired
    LoginTrailRepository loginTrailRepository;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    ClientRegistrationService clientRegistrationService;


    @Autowired
    SpringSessionBackedSessionRegistry sessionRegistry;

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessions;

    public void customLoginTrail(OAuth2Authentication authentication, String clientId){
        log.info("Start removing user previous login session {}", authentication.getName());
        removeSession(authentication.getName());
        for (ClientDetails client : clientRegistrationService.listClientDetails()) {
            for (OAuth2AccessToken token : tokenStore.findTokensByClientIdAndUserName(client.getClientId(),  authentication.getName())) {
                tokenStore.removeRefreshToken(token.getRefreshToken());
                tokenStore.removeAccessToken(token);
            }
        }
        List<LoginTrailEntity> loggedInUsers = loginTrailRepository.findByUserIdAndStatusOrderBySignInTimeDesc(authentication.getName(), "Active");
        for (LoginTrailEntity loginTrailEntity : loggedInUsers){
            loginTrailEntity.setStatus("Terminate");
            loginTrailEntity.setSignOutTime(Timestamp.valueOf(LocalDateTime.now()));
            loginTrailRepository.save(loginTrailEntity);
        }
        String ipAddress = null;

        try {
            ipAddress = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        }catch (Exception e){
            log.error("ip address not found ", e);
        }

        LoginTrailEntity user = LoginTrailEntity.builder()
                .userId(authentication.getName())
                .machineIp(ipAddress)
                .clientId(clientId)
                .status("Active")
                .signInTime(Timestamp.valueOf(LocalDateTime.now())).build();

        log.info("New session data inserted for user: {}", authentication.getName());
        loginTrailRepository.save(user);
    }

    public void removeSession(String userId){

        Collection<? extends Session> usersSessions = sessions
                .findByPrincipalName(userId)
                .values();

        String currentSessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
        for (Session session : usersSessions){
            if(!session.getId().equalsIgnoreCase(currentSessionId)){
                sessionRegistry.getSessionInformation(session.getId()).expireNow();
                sessions.deleteById(session.getId());
            }
        }
        log.info("removed previous sessions for user : {}", userId);

    }
}
