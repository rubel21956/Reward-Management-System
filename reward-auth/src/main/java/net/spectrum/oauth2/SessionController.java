package net.spectrum.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RestController
//@RequestMapping("")
public class SessionController {

    @Autowired
    private AuthServiceImpl authService;

    @PutMapping("/auth/v1/logout")
    public ResponseEntity<Map<String, Object>> customLogout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authService.customLogout(request, response, authentication);

    }

    @GetMapping("/auth/v1/sessions")
    public ResponseEntity<Map<String, Object>> getUserSessions(@RequestParam(name = "offset", defaultValue = "0") int offset,
                                                               @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                               @RequestParam(name = "searchText", defaultValue = "") String searchText
                                                               ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Pageable pageable = PageRequest.of(offset, limit);
        return authService.getUserSessionList(authentication, pageable, searchText);
    }

    @PutMapping("/auth/v1/sessions")
    public ResponseEntity<Map<String, Object>> updateUserSessions(@RequestHeader(name = "Authorization") String token,
                                                                  @Valid @RequestBody SessionRequestDto request){
        return authService.updateUserSessionList(request);
    }
}
