package net.spectrum.api.service.fileupload;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonSerialize
@JsonDeserialize
public class UploadResponse {

    private List<String> files;
}
