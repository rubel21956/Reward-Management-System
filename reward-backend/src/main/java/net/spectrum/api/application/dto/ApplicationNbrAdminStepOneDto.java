package net.spectrum.api.application.dto;

import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNbrAdminStepOneDto {

    @NonNull
    private String oid;
    private String nbrRewardFileNumber;
    @Size(max = 128)
    private String customsAwardSanctioningCommittee;
}
