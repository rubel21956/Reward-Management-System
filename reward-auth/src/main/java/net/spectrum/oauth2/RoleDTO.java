package net.spectrum.oauth2;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {

    private String userId;
    private String loginOid;
    private String userProfileOid;
    private String applicationStatus;
    private String roleOid;
}
