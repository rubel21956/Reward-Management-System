package net.spectrum.api.officesection.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeSectionRequestDto {

    @NotNull(message = "officeOid must not be null")
    @NotEmpty(message = "officeOid must not be empty")
    private String officeOid;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String name;
    private String status;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
