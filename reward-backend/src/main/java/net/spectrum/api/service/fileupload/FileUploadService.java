package net.spectrum.api.service.fileupload;

import com.google.common.collect.Lists;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileUploadService {

//    public static final String RMS_FILE_DIR = "file_dir";

    @Value("${file.upload.path}")
    private String fileUploadPath;

    public ResponseEntity<UploadResponse> uploadFiles(UploadDto uploadDto, String fileDir) throws ExceptionHandlerUtil {
//        String fileDir = "appendix";
        List<String> fileList = uploadFile(uploadDto.getFiles(), fileUploadPath, fileDir);

        // Build response
        UploadResponse response = UploadResponse.builder()
            .files(fileList)
            .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<String> uploadFile(List<MultipartFile> files, String baseDirectory, String fileDir)
        throws ExceptionHandlerUtil {
        List<String> fileList = Lists.newArrayList();
        try {
            File directory = FileUtils.getFile(baseDirectory, fileDir);
            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
            }

            System.out.println("this is directory");
            System.out.println(baseDirectory);
            System.out.println(fileDir);
            System.out.println(directory);

            for (int j = 0; j < files.size(); j++) {
                MultipartFile this_formDataBodyPartFile = files.get(j);
                String ext = FilenameUtils.getExtension(this_formDataBodyPartFile.getOriginalFilename());
                String fileName = UUID.randomUUID() + "." + ext;
//                String fileName = this_formDataBodyPartFile.getOriginalFilename() + "." + ext;
                byte[] bytes = this_formDataBodyPartFile.getBytes();
                Path path = Paths.get(directory.getPath() + File.separator + fileName);
                Files.write(path, bytes);
                fileList.add(fileName);
//                Files.deleteIfExists(path);
                System.out.println(path);
            }



        } catch (Exception e) {
            log.error("An Exception occured while document uploading : ", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return fileList;
    }

    public ResponseEntity<String> deleteFile(String filePath){
        Path path = Paths.get(filePath);
        try{Files.deleteIfExists(path);}catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("delete successful");
        return new ResponseEntity<String>("File delete successful", HttpStatus.OK);
    }
}
