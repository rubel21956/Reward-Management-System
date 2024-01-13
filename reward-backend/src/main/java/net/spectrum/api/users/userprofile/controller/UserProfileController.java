package net.spectrum.api.users.userprofile.controller;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.users.userprofile.dto.UserProfileRequestDTO;
import net.spectrum.api.users.userprofile.dto.UserProfileResponseDTO;
import net.spectrum.api.users.userprofile.entity.UserProfileEntity;
import net.spectrum.api.users.userprofile.repository.UserProfileRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.security.Principal;
import java.util.List;
import java.util.Map;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

@EnableTransactionManagement
@RestController
@RequestMapping("/public/v1/userprofile")
@Slf4j
public class UserProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;
//    Get list

    @GetMapping("")
    public ResponseEntity<List<UserProfileEntity>> getUserProfileList(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal
    ) {
        log.info("Request received for user profile list by: {}", principal.getName());
        List<UserProfileEntity> response = userProfileRepository.findAll();
        log.info("Response send for user profile list: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{OID}")
    public ResponseEntity<UserProfileEntity> getUserProfilebyOid(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
            @AuthenticationPrincipal Principal principal,
            @PathVariable("OID") @NotEmpty String oid) {
        log.info("Request received for user profile list by: {}", principal.getName());
        UserProfileEntity response = userProfileRepository.findByLoginOid(oid);
        log.info("Response send for user profile list: {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @PostMapping("/save")
//    public ResponseEntity<UserProfileResponseDTO> createUserProfile(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @Valid @RequestBody UserProfileRequestDTO requestDTO) {
//        try {
//            log.info("Request received for user profile list by");
//            UserProfileResponseDTO response = userProfileService.saveUserProfile(requestDTO);
//            log.info("Response send for user profile list: {}", response);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @PutMapping("/{OID}")
//    public ResponseEntity<UserProfileResponseDTO> updateUserProfile(
//            @RequestHeader(name = "Authorization") String token,
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @AuthenticationPrincipal Principal principal,
//            @Valid @RequestBody UserProfileRequestDTO requestDTO,
//            @PathVariable("OID") @NotEmpty String oid) {
//        try {
//            log.info("Request received for user profile list by: {}", principal.getName());
//            UserProfileResponseDTO response = userProfileService.updateUserProfile(requestDTO, oid, principal.getName());
//            log.info("Response send for user profile list: {}", response);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }
    //Save-Update

//    @DeleteMapping("/{OID}")
//    public ResponseEntity<Map<String, String>> deleteUserProfile(
//            @RequestHeader(name = "Authorization") String token,
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @AuthenticationPrincipal Principal principal,
//            @PathVariable("OID") @NotEmpty String oid) {
//        try {
//            log.info("Request received for user profile list by: {}", principal.getName());
//            ResponseEntity<Map<String, String>> response = userProfileService.deleteUserProfile(oid, principal.getName());
//            log.info("Response send for user profile data delete: {}", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/approver-list")
//    public ResponseEntity<Map<String, Object>> getApproverList(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "officeName", required = true, defaultValue = "") String officeName,
//            @RequestParam(name = "umsRole", required = true, defaultValue = "") String umsRole
//    ) {
//        try {
//            log.info("Request received for approver list");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getApproverList(officeName, umsRole);
//            log.info("Response sent for approver list", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/check-license")
//    public ResponseEntity<Map<String, Object>> getLicenseCount(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "licenseNumber", required = true, defaultValue = "") String licenseNumber) {
//        try {
//            log.info("Request received for licernse");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getLicenseCount(licenseNumber);
//            log.info("Response sent for license list- ", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/check-userId")
//    public ResponseEntity<Map<String, Object>> getUserIdCount(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "userId", required = true, defaultValue = "") String userId) {
//        try {
//            log.info("Request received for user Id");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getUserIdCount(userId);
//            log.info("Response sent for user Id- ", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/check-email")
//    public ResponseEntity<Map<String, Object>> getemailCount(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "email", required = true, defaultValue = "") String email) {
//        try {
//            log.info("Request received for email");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getemailCount(email);
//            log.info("Response sent for email - ", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/check-mobileNo")
//    public ResponseEntity<Map<String, Object>> getmobileNoCount(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "mobileNo", required = true, defaultValue = "") String mobileNo) {
//        try {
//            log.info("Request received for mobile no.");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getmobileNoCount(mobileNo);
//            log.info("Response sent for mobile no. - ", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }

//    @GetMapping("/check-nidNumber")
//    public ResponseEntity<Map<String, Object>> getnidNumberCount(
//            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
//            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
//            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
//            @RequestParam(name = "nidNumber", required = true, defaultValue = "") String nidNumber) {
//        try {
//            log.info("Request received for nid Number");
//            ResponseEntity<Map<String, Object>> response = userProfileService.getnidNumberCount(nidNumber);
//            log.info("Response sent for nid Number - ", response);
//            return response;
//        } catch (ExceptionHandlerUtil ex) {
//            log.error(ex.getMessage(), ex);
//            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
//        }
//    }
}
