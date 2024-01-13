package net.spectrum.api.office.controller;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.office.dto.OfficeRequestDto;
import net.spectrum.api.office.dto.OfficeResponseDto;
import net.spectrum.api.office.entity.OfficeEntity;
import net.spectrum.api.office.service.OfficeService;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/office")
@Slf4j
public class OfficeController {

    @Autowired
    private OfficeService officeService;

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-sys-admin','role-nbr-investigation')")
    @GetMapping()
    public ResponseEntity<List<OfficeEntity>> getOffices(@RequestHeader(name = "Authorization") String token,
                                                         @AuthenticationPrincipal Principal principal,
                                                         @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                         @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                         @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime)
    {
        try {
            log.info("Request received for office list");
            List<OfficeEntity> response = officeService.getOffices();
            log.info("Response send for office list: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-sys-admin','role-nbr-investigation')")
    @GetMapping("/{id}")
    public ResponseEntity<OfficeEntity> getOfficeByOid(@RequestHeader(name = "Authorization") String token,
                                                       @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                       @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                       @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                       @AuthenticationPrincipal Principal principal,
                                                       @PathVariable("id") @NotEmpty String oid)
    {
        try {
            log.info("Request received for office list by: {}", principal.getName());
            OfficeEntity response = officeService.getOfficeByOid(oid);
            log.info("Response send for office list: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @PostMapping()
    public ResponseEntity<OfficeResponseDto> addOffice(@RequestHeader(name = "Authorization") String token,
                                                       @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                       @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                       @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                       @AuthenticationPrincipal Principal principal,
                                                       @Valid @RequestBody OfficeRequestDto requestDTO)
    {
        try {
            log.info("Request received for office saving by: {}", principal.getName());
            OfficeResponseDto response = officeService.saveOffice(requestDTO, principal.getName());
            log.info("Response send for office saving: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @PutMapping("/{id}")
    public ResponseEntity<OfficeResponseDto> updateOffice(@RequestHeader(name = "Authorization") String token,
                                                          @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                          @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                          @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                          @AuthenticationPrincipal Principal principal,
                                                          @Valid @RequestBody OfficeRequestDto officeRequestDto,
                                                          @PathVariable("id") @NotEmpty String oid)
    {
        try {
            log.info("Request received for office update by: {}", principal.getName());
            OfficeResponseDto response = officeService.updateOffice(officeRequestDto, oid, principal.getName());
            log.info("Response send for office update: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
    @PreAuthorize("hasAnyAuthority('role-sys-admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOffice(@RequestHeader(name = "Authorization") String token,
                                                            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                            @AuthenticationPrincipal Principal principal,
                                                            @PathVariable("id") @NotEmpty String oid)
    {
        try {
            log.info("Request received for office delete by: {}", principal.getName());
            ResponseEntity<Map<String, String>> response = officeService.deleteOffice(oid, principal.getName());
            log.info("Response send for office data delete: {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
}
