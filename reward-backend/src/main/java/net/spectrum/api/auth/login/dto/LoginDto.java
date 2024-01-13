package net.spectrum.api.auth.login.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.GsonBuilder;
import lombok.*;
import net.spectrum.api.auth.login.util.MenuJson;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonDeserialize
public class LoginDto {
    private String userId;
    private List<MenuJson> menuJson;
    private List<String> roles;
    private String roleOid;
    private String lastLoginTime;
    private String resetRequired;
    private String status;
    private String currentOfficeName;
    private String designation;
    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

