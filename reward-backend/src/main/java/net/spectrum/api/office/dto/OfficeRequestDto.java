package net.spectrum.api.office.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeRequestDto {

    @NotNull(message = "code must not be null")
    @NotEmpty(message = "code must not be empty")
    private String code;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String bNname;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String bNaddress2;

    @NotNull(message = "name must not be null")
    @NotEmpty(message = "name must not be empty")
    private String status;
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
