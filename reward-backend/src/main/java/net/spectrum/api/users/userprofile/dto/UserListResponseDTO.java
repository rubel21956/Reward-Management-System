package net.spectrum.api.users.userprofile.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListResponseDTO {

    private String loginOid;
    private String userId;
    private String roleOid;
    private String roleName;
    private String umsRole;
    private String userProfileName;
    private String email;
    private String mobileNo;
    private String nidNumber;
    private String designation;
    private String userProfileOid;
    private String officeOid;
    private String status;
    private String applicationStatus;
    private String requestType;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
