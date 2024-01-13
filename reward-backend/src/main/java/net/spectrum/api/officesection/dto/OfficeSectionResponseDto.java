package net.spectrum.api.officesection.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeSectionResponseDto {

    private String oid;
    private String message;
    private String officeName;
    private String officeSectionName;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
