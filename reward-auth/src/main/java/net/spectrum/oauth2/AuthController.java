package net.spectrum.oauth2;

import java.security.Principal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;

    @GetMapping("/validateUser")
    public Principal user(Principal user) {
        return user;
    }

    @GetMapping("/auth/v1/userInfo")
    public ResponseEntity<UserInfo> getUserInfo(Principal principal) {
        try {
            log.info("Get user info by auth token received for userid : {}", principal.getName());
            ResponseEntity<UserInfo> userResponse = authService.getUserInfo(principal);
            log.info("Successfully get user information : {}", userResponse);
            return userResponse;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }

    }
}