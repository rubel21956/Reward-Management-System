package net.spectrum.api.office.dto;


import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfficeResponseDto {

    private String oid;
    private String message;

    @Override
    public String toString(){
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
