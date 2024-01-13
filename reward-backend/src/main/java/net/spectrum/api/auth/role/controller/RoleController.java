package net.spectrum.api.auth.role.controller;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.auth.role.service.RoleService;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Map;

@Slf4j
@RestController
@Validated
@RequestMapping("/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/get-role-list")
    public ResponseEntity<Map<String, Object>> getRoleList(
            @RequestHeader(name="Authorization") String token,
            @RequestHeader(name="Request-Id", required= true) @NotEmpty String requestId,
            @RequestHeader(name="Request-Timeout-In-Seconds", required= true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name="Request-Time", required= true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal
    )
    {
        try{
            log.info("Request received for role list by: {}", principal.getName());
            ResponseEntity<Map<String, Object>> response = roleService.getRoleList(principal.getName());
            log.info("Response sent for role list : {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex){
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
}
