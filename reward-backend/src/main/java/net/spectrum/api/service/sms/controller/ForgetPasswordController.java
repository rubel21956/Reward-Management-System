package net.spectrum.api.service.sms.controller;

import java.security.Principal;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.service.sms.ForgetPasswordRequestDto;
import net.spectrum.api.service.sms.service.ForgetPasswordService;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping("/public/v1")
public class ForgetPasswordController {
    @Autowired
    private ForgetPasswordService forgetPasswordService;

    @PostMapping("/forget-password")
    public ResponseEntity<Map<String, Object>> setPassword(
        @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
        @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
        @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
        @Valid @RequestBody ForgetPasswordRequestDto request
    ) throws Exception {
        try {
            log.info("Request received for password change: {}", request);
            ResponseEntity<Map<String, Object>> response = forgetPasswordService.setPassword(request);
            log.info("Response send for password change: {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.message, ex);
        }
    }

    @GetMapping("/send-email")
    public ResponseEntity<Boolean> sendEmailToUser(
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestParam("oid") String oid
    ) {
        Boolean response = forgetPasswordService.sendEmailToUser(oid);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
