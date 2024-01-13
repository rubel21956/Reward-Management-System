package net.spectrum.api.application.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationDto {
    private ApplicationCustomsStepOneDto applicationCustomsStepOneDto;
    private ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto;
    private  ApplicationCustomsStepFiveDto applicationCustomsStepFiveDto;
    private ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto;
    private ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto;
    private ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto;
}
