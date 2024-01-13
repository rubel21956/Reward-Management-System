package net.spectrum.api.application.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.application.converter.ApplicationConverter;
import net.spectrum.api.application.dto.*;
import net.spectrum.api.application.entity.ApplicationEntity;
import net.spectrum.api.application.repository.ApplicationRepository;
import net.spectrum.api.application.service.ApplicationService;
import net.spectrum.api.applicationattachment.repository.ApplicationAttachmentRepository;
import net.spectrum.api.rewardamount.repository.RewardAmountRepository;

import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@EnableTransactionManagement
@RestController
@Slf4j
@RequestMapping("/v1/application")
public class ApplicationController {

    @Autowired
    ApplicationConverter applicationConverter;

    @Autowired
    ApplicationAttachmentRepository applicationAttachmentRepository;

    @Autowired
    RewardAmountRepository rewardAmountRepository;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationRepository applicationRepository;

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping()
    public List<ApplicationDto> getApplicationDtos(@RequestHeader(name = "Authorization") String token,
                                                   @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                   @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                   @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                   @AuthenticationPrincipal Principal principal) {
          /**  S lint
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        return applicationDtos = applicationService.getApplicationDtos();
        Correction **/

        return applicationService.getApplicationDtos();
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/{id}")
    public ApplicationDto getApplicationDto(@PathVariable String id,
                                            @RequestHeader(name = "Authorization") String token,
                                            @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                            @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                            @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                            @AuthenticationPrincipal Principal principal) {
         /**  S lint
        ApplicationDto applicationDto = new ApplicationDto();
        return applicationDto = applicationService.getApplicationDtoByOid(id);
         Correction **/
        return applicationService.getApplicationDtoByOid(id);

    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @GetMapping("/search/operator/{operatorName}")
    public List<ApplicationDto> getApplicationByOperator(@PathVariable String operatorName,
                                                         @AuthenticationPrincipal Principal principal,
                                                         @RequestHeader(name = "Authorization") String token) {
          /**   S lint //
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        return applicationDtos = applicationService.getApplicationDtosByOperator(operatorName);
            Correction **/

        return applicationService.getApplicationDtosByOperator(operatorName);
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @GetMapping("/search/operator/withSattlement/{operatorName}")
    public List<ApplicationDto> getApplicationByOperatorOfSattlement(@PathVariable String operatorName,
                                                         @AuthenticationPrincipal Principal principal,
                                                         @RequestHeader(name = "Authorization") String token) {
        /**   S lint //
         List<ApplicationDto> applicationDtos = new ArrayList<>();
         return applicationDtos = applicationService.getApplicationDtosByOperator(operatorName);
         Correction **/

        return applicationService.getApplicationDtosByOperatorOfSattlement(operatorName);
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/search")
    public List<ApplicationDto> getApplicationByStatus(@RequestParam String applicationStatus,
                                                       @RequestParam String applicationDate,
                                                       @RequestParam String userId,
                                                       @AuthenticationPrincipal Principal principal,
                                                       @RequestHeader(name = "Authorization") String token) {
        /** S lint
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        return applicationDtos = applicationService.getApplicationDtosByStatus(applicationStatus);
            correction **/
        System.out.println("Staring sout up");
        return applicationService.getApplicationDtosByStatus(applicationStatus, applicationDate, userId);
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/searchWithOutSattlement")
    public List<ApplicationDto> getApplicationByStatusWithOutSattlement(@RequestParam String applicationStatus,
                                                       @RequestParam String applicationDate,
                                                       @RequestParam String userId,
                                                       @AuthenticationPrincipal Principal principal,
                                                       @RequestHeader(name = "Authorization") String token) {
        /** S lint
         List<ApplicationDto> applicationDtos = new ArrayList<>();
         return applicationDtos = applicationService.getApplicationDtosByStatus(applicationStatus);
         correction **/
        return applicationService.getApplicationDtosByStatusWithOutSattlement(applicationStatus, applicationDate, userId);
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/searchBystatusAndOfficeName")
    public List<ApplicationDto> getApplicationBystatusAndOfficeName(@RequestParam String applicationStatus,
                                                                    @RequestParam String officeName,
                                                       @RequestParam String applicationDate,
                                                       @AuthenticationPrincipal Principal principal,
                                                       @RequestHeader(name = "Authorization") String token) {

        return applicationService.getApplicationDtosByStatusAndOfficeName(officeName,applicationStatus,applicationDate);
    }



    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @GetMapping("/getApplicationOfSattlement")
    public List<ApplicationDto> getApplicationOfSattlement(@RequestParam String applicationStatus,
                                                                    @RequestParam String officeName,
                                                                    @RequestParam String applicationDate,
                                                                    @AuthenticationPrincipal Principal principal,
                                                                    @RequestHeader(name = "Authorization") String token) {


        return applicationService.getApplicationDtoOfSattlement(officeName,applicationStatus,applicationDate);
    }




    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @GetMapping("/ForNbrAdmin")
    public List<ApplicationDto> getApplicationsForNbrAdmin(@RequestHeader(name = "Authorization") String token,
                                                           @AuthenticationPrincipal Principal principal) {

       /**  S lint
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        return applicationDtos = applicationService.getApplicationDtosForNbrAdmin();
        Correction **/
        return applicationService.getApplicationDtosForNbrAdmin();
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/customs/step-one/{id}")
    public ApplicationCustomsStepOneDto getApplicationCustomsStepOne(@PathVariable String id,
                                                                     @RequestHeader(name = "Authorization") String token,
                                                                     @AuthenticationPrincipal Principal principal) {
     /**   S lint
        ApplicationCustomsStepOneDto applicationCustomsStepOneDto = new ApplicationCustomsStepOneDto();
        return applicationCustomsStepOneDto = applicationService.getApplicationCustomsStepOneDto(id);
         Correction **/
        return applicationService.getApplicationCustomsStepOneDto(id);
    }


    @PostMapping("/customs/step-one")
    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    public ApplicationCustomsStepOneDto saveApplicationCustomsStepOne(@RequestHeader(name = "Authorization") String token,
                                                                      @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                                      @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                                      @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                                      @AuthenticationPrincipal Principal principal,
                                                                      @RequestBody ApplicationCustomsStepOneDto applicationCustomsStepOneDto) {

        return applicationCustomsStepOneDto = applicationService.saveApplicationCustomsStepOneDto(applicationCustomsStepOneDto, principal.getName());
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @PutMapping("/customs/step-one/{id}")
    public ApplicationCustomsStepOneDto updateApplicationCustomsStepOne(@PathVariable(value = "id") String id,
                                                                        @RequestHeader(name = "Authorization") String token,
                                                                        @AuthenticationPrincipal Principal principal,
                                                                        @RequestBody ApplicationCustomsStepOneDto applicationCustomsStepOneDto) {
        applicationCustomsStepOneDto = applicationService.updateApplicationCustomsStepOne(id,
                applicationCustomsStepOneDto, principal.getName());
        return applicationCustomsStepOneDto;
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping("/customs/step-two/{id}")
    public ApplicationCustomsStepTwoDto getApplicationCustomsStepTwo(@PathVariable String id,
                                                                     @RequestHeader(name = "Authorization") String token,
                                                                     @AuthenticationPrincipal Principal principal) {
       ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = new ApplicationCustomsStepTwoDto();
         applicationCustomsStepTwoDto = applicationService.getApplicationCustomsStepTwoDto(id);
        return applicationCustomsStepTwoDto;
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @PutMapping("/customs/step-two/{id}")
    public ApplicationCustomsStepTwoDto updateApplicationCustomsStepTwo(@PathVariable(value = "id") String id,
                                                                        @RequestHeader(name = "Authorization") String token,
                                                                        @AuthenticationPrincipal Principal principal,
                                                                        @Valid @RequestBody ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto) {
        applicationCustomsStepTwoDto = applicationService.updateApplicationCustomsStepTwo(id,
                applicationCustomsStepTwoDto, principal.getName());
        return applicationCustomsStepTwoDto;
    }

    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @PutMapping("/nbr/step-one/{id}")
    public ApplicationNbrAdminStepOneDto updateApplicationAdminStepOne(@PathVariable(value = "id") String id,
                                                                       @RequestHeader(name = "Authorization") String token,
                                                                       @AuthenticationPrincipal Principal principal,
                                                                       @Valid @RequestBody ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto) {

        return applicationNbrAdminStepOneDto = applicationService.getApplicationNbrAdminStepOneDto(id,
                applicationNbrAdminStepOneDto);
    }

    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @PutMapping("/nbr/step-two/{id}")
    public ApplicationNbrAdminStepTwoDto updateApplicationAdminStepTwo(@PathVariable(value = "id") String id,
                                                                       @RequestHeader(name = "Authorization") String token,
                                                                       @AuthenticationPrincipal Principal principal,
                                                                       @Valid @RequestBody ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto) {


        ApplicationEntity applicationEntity = applicationService.updateApplicationAdminStepTwo(id, applicationNbrAdminStepTwoDto,
                principal.getName());
        applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto(
                (applicationEntity));
        return applicationNbrAdminStepTwoDto;
    }

    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @PutMapping("/nbr/step-three/{id}")
    public ApplicationNbrAdminStepThreeDto updateApplicationAdminStepThree(@PathVariable(value = "id") String id,
                                                                           @RequestHeader(name = "Authorization") String token,
                                                                           @AuthenticationPrincipal Principal principal,
                                                                           @Valid @RequestBody ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto) {
        applicationNbrAdminStepThreeDto = applicationService.updateApplicationAdminStepThree(id,
                applicationNbrAdminStepThreeDto, principal.getName());
        return applicationNbrAdminStepThreeDto;
    }





    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @PutMapping("/customs/step-five/{id}")
    public ApplicationCustomsStepFiveDto updateApplicationAdminStepFive(@PathVariable(value = "id") String id,
                                                                        @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                                                        @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                                                        @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                                                        @RequestHeader(name = "Authorization") String token, @AuthenticationPrincipal Principal principal,
                                                                        @Valid @RequestBody ApplicationCustomsStepFiveDto applicationCustomsStepFiveDto) {

        ApplicationCustomsStepFiveDto  applicationCustomsStepFiveDataObject = applicationService.SaveCustomApplicationStepFive(principal.getName(),  applicationCustomsStepFiveDto, id);
        return applicationCustomsStepFiveDataObject;
    }







    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping(value = "/five/file/{refId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @RequestHeader(name = "Authorization") String token,
                             @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                             @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                             @AuthenticationPrincipal Principal principal,
//                             @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                             @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}\\\\.\\\\d{6}\\\\d{2}:\\\\d{2}", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                             @PathVariable("refId") @NotBlank String refId) throws Exception {
        try {
            log.info("Request received for file retrieve by: {}", refId);
            DownloadFileResponse fileRes = applicationService.downloadFile(refId);
            File file = fileRes.getFile();
            if (file == null || !file.exists()) {
                log.error("File not found {}", refId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            // Here we have mentioned it to show inline
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s%s", file.getName() , "\""));
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            log.info("Response send for image retrieve: {}", response);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping(value = "/two/file/{refId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public void downloadTwoFile(HttpServletRequest request, HttpServletResponse response,
                                @RequestHeader(name = "Authorization") String token,
                                @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                @AuthenticationPrincipal Principal principal,
//                                @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "\\\\d{4}-\\\\d{2}-\\\\d{2}T\\\\d{2}:\\\\d{2}:\\\\d{2}\\\\.\\\\d{6}\\\\d{2}:\\\\d{2}", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                @PathVariable("refId") @NotBlank String refId) throws Exception {
        try {
            log.info("Request received for file retrieve by: {}", refId);
            DownloadFileResponse fileRes = applicationService.downloadTwoFile(refId);
            File file = fileRes.getFile();
            if (file == null || !file.exists()) {
                log.error("File not found {}", refId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            // Here we have mentioned it to show inline
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s%s" , file.getName() , "\""));
            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            log.info("Response send for image retrieve: {}", response);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

    /** This is controller is for Download PURBO BOTI BILL OF ENTRY
     Reason: To Download PURBO BOTI BILL OF ENTRY FROM ITS OWN DIRECTORY;
     Modified By Arif. **/

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-admin','role-nbr-investigation')")
    @GetMapping(value = "/one/file/{refId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header")})
    public void downloadOneFile(HttpServletRequest request, HttpServletResponse response,
                                @RequestHeader(name = "Authorization") String token,
                                @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                @AuthenticationPrincipal Principal principal,
                                // S lint
//                                @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)[0-9][0-9]-(0[0-9]|1[0-2])-(0[1-9]|([12][0-9]|3[01]))T([01][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9].([0-9]{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                // Correction
                                @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = "(19|20)\\d-(0\\d|1[0-2])-(0[1-9]|([12]\\d|3[01]))T([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d.(\\d{3,6})Z", message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                @PathVariable("refId") @NotBlank String refId) throws Exception {
        try {
            log.info("Request received for file retrieve by: {}", refId);
            DownloadFileResponse fileRes = applicationService.downloadOneFile(refId);
            File file = fileRes.getFile();
            if (file == null || !file.exists()) {
                log.error("File not found {}", refId);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
            }

            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            // Here we have mentioned it to show inline
            // S lint
            response.setHeader("Content-Disposition", String.format("inline; filename=\"%s%s" , file.getName() , "\""));
            // Correction

            response.setContentLength((int) file.length());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            log.info("Response send for image retrieve: {}", response);
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }
    /** This is the end of Downloading Controller of PURBO BOTI BILL OF ENTRY FROM ITS OWN DIRECTORY; **/



    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @GetMapping("/verify/{billOfEntryNo}")
    public String verifyBillOfEntryNumber(@PathVariable(value = "billOfEntryNo") String billOfEntryNo,
                                          @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                          @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                          @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                          @RequestHeader(name = "Authorization") String token,
                                          @AuthenticationPrincipal Principal principal) {
        if (applicationRepository.findByBillOfEntryNo(billOfEntryNo) == null) {
            return "false";
        } else {
            return "true";
        }
    }

    @PreAuthorize("hasAnyAuthority('role-operator','role-nbr-investigation')")
    @GetMapping("/verify/{billOfEntryNo}/{applyingOfficeOid}/{billOfEntryDate}")
    public Boolean verifyBillOfEntryNumberAndDateOfEntry(@PathVariable(value = "billOfEntryNo") String billOfEntryNo,
                                                        @PathVariable(value = "applyingOfficeOid") String applyingOfficeOid,
                                                        @PathVariable(value = "billOfEntryDate") Timestamp billOfEntryDate,
                                          @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                          @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                          @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                          @RequestHeader(name = "Authorization") String token,
                                          @AuthenticationPrincipal Principal principal
    ) {

      return applicationRepository.findBillofEntryNameDateIsExits(billOfEntryNo,applyingOfficeOid,billOfEntryDate);
    }


    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @PutMapping("/nbr/step-three-submit/{id}")
    public ResponseEntity<ApplicationNbrAdminStepThreeDto> updateApplicationStatusAdmin(@PathVariable(value = "id") String id,
                                                       @RequestHeader(name = "Authorization") String token,
                                                       @AuthenticationPrincipal Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.updateApplicationStatusAdmin(id, principal.getName()));
    }

    @PreAuthorize("hasAuthority('role-nbr-admin')")
    @PutMapping("/nbr/step-three-submit-for-sattlement/{id}")
    public ResponseEntity<ApplicationNbrAdminStepThreeDto> updateApplicationStatusAdminforSattlement(@PathVariable(value = "id") String id,
                                                                                        @RequestHeader(name = "Authorization") String token,
                                                                                        @AuthenticationPrincipal Principal principal) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(applicationService.updateApplicationStatusAdminforSattlement(id, principal.getName()));
    }
}
