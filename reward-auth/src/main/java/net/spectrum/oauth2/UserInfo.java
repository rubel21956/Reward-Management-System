package net.spectrum.oauth2;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import java.util.List;
import com.google.gson.GsonBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
public class UserInfo {

    private String userId;
    private String roleOid;
    private String userName;
    private String mobileNo;
    private List<MenuJson> menuJson;
    private List<String> roles;
    private String lastLoginTime;
    private String resetRequired;
    private String status;
    private String userMessage;
    private String currentOfficeName;
    private String designation;
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
class MenuJson {
    String topMenuId;
    List<String> leftMenuId;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
