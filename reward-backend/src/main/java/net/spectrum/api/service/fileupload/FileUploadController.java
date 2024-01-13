package net.spectrum.api.service.fileupload;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.application.dto.ApplicationDto;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import net.spectrum.api.applicationattachment.repository.ApplicationAttachmentRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@Validated
@RequestMapping("/v1/file")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    ApplicationAttachmentRepository applicationAttachmentRepository;

    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = "application/json")
    public ResponseEntity<UploadResponse> uploadFile(
        @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
        @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
        @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
        @RequestParam("files") List<MultipartFile> files,
        @RequestParam(name = "fileDir") String fileDir
    )
        throws Exception {
        try {
            String url = "/v1/file/rms";
//            log.info("Resource : {}, Request received time : {}", url,
//                DateTimeFormat.forPattern(ValidationPattern.DATE_FORMAT).print(new DateTime(requestTime)));
            UploadDto uploadDto = UploadDto.builder()
                .files(files)
                .build();
            ResponseEntity<UploadResponse> fileUploadResponse = fileUploadService.uploadFiles(uploadDto, fileDir);
            log.info("Resource : {}", url);
            log.info("Response sent : {}", fileUploadResponse);
            return fileUploadResponse;
        } catch (ExceptionHandlerUtil ex) {
            log.error(ex.getMessage(), ex);
            throw new ResponseStatusException(ex.getCode(), ex.getMessage(), ex);
        }
    }

//    this api is for delete apendix uploaded file at the time of registration.
    @GetMapping(value = "/delete/{fileName}")
    public String deleteFile(@PathVariable(value = "fileName") String fileName,
                             @RequestHeader(name = "Authorization") String token,
                             @RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                             @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                             @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                             @AuthenticationPrincipal Principal principal,
                            @RequestParam(name = "fileDir") String fileDir) {


        try {
            System.out.println(fileDir);
            System.out.println(fileName);
            fileUploadService.deleteFile(fileDir+fileName);
            ApplicationAttachmentEntity deletableEntity =  applicationAttachmentRepository.findByfileName(fileName);
            applicationAttachmentRepository.delete(deletableEntity);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return "";
    }


}
