package net.spectrum.api.service.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgetPasswordRequestDto {
    private String userId;
    private String nid;
    private String recoveryType;

}
