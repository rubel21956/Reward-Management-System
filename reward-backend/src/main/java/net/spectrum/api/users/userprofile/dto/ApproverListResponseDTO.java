package net.spectrum.api.users.userprofile.dto;

import com.google.gson.GsonBuilder;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproverListResponseDTO {

    private String userProfileOid;
    private String userProfileName;
    private String loginOid;
    private String roleOid;
    private String roleName;
    private String umsRole;
    private String message;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
