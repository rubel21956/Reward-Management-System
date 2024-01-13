package net.spectrum.api.officesection.controller;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.officesection.dto.OfficeSectionRequestDto;
import net.spectrum.api.officesection.dto.OfficeSectionResponseDto;
import net.spectrum.api.officesection.entity.OfficeSectionEntity;
import net.spectrum.api.officesection.service.OfficeSectionService;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/v1/office-section")
@Slf4j
public class OfficeSectionController {
    @Autowired
    private OfficeSectionService officeSectionService;

    @PostMapping()
    public ResponseEntity<OfficeSectionResponseDto> saveOfficeSection(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal,
            @Valid @RequestBody OfficeSectionRequestDto requestDTO) {
        try {
            log.info("Request received for office section save by: {}", principal.getName());
            OfficeSectionResponseDto response = officeSectionService.saveOfficeSection(requestDTO, principal.getName());
            log.info("Response send for office section save: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<OfficeSectionEntity> getOfficeSectionByOid(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal,
            @PathVariable("id") @NotEmpty String oid) {
        try {
            log.info("Request received for office section by: {}", principal.getName());
            OfficeSectionEntity response = officeSectionService.getOfficeSectionByOid(oid);
            log.info("Response send for office section: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<OfficeSectionResponseDto> updateOfficeSection(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal,
            @Valid @RequestBody OfficeSectionRequestDto requestDTO,
            @PathVariable("id") @NotEmpty String oid) {
        try {
            log.info("Request received for office section update by: {}", principal.getName());
            OfficeSectionResponseDto response = officeSectionService.updateOfficeSection(requestDTO, oid, principal.getName());
            log.info("Response send for office section update: {}", response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getOfficeSections(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @RequestParam(name = "offset", required = true, defaultValue = "0") int offset,
            @RequestParam(name = "limit", required = true, defaultValue = "10") int limit,
            @RequestParam(name = "searchText", required = false, defaultValue = "") String searchText,
            @AuthenticationPrincipal Principal principal
    ) {
        try {
            log.info("Request received for Office section list by: {}", principal.getName());
            ResponseEntity<Map<String, Object>> response = officeSectionService.getOfficeSections(limit, offset, searchText);
            log.info("Response sent for Office Section list : {}", response);
            return response;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
}
