package net.spectrum.api.service.fileupload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
@NoArgsConstructor
@AllArgsConstructor
public class UploadDto {

    private List<MultipartFile> files;
}
