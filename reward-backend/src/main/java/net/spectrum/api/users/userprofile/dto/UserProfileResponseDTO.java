package net.spectrum.api.users.userprofile.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {

    private String oid;
    private String userHistoryOid;
    private String userLogOid;
    private String loginOid;

    private String message;

    private String resCode;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}