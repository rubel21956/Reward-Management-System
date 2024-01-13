package net.spectrum.api.service.reports.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponseDTO {

    private String updatedOn;
    private String userStatus;
    private String createdBy;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
