package net.spectrum.api.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationNbrAdminStepTwoDto {
    @NonNull
    private String oid;
    private String suggestionFromNbr;
    private String suggestionStatus;
    private String applicationStatus;
    private String applicationStatusBn;
}
