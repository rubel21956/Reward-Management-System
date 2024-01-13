package net.spectrum.api.auth.login.util;


import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Password {

    @NotNull(message = "Old password should not be empty")
    private String oldPassword;

    @NotNull(message = "New password should not be empty")
    private String newPassword;

    @Override
    public String toString() {
        return "SetPasswordRequest{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
