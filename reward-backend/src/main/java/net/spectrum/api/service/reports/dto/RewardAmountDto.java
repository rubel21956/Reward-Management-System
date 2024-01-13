package net.spectrum.api.service.reports.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardAmountDto {

    private String oid;
    private String employeeName;
    private String rejectedName;
    private String designation;
    private BigDecimal advancePaidAmount;
    private BigDecimal appliedRewardAmount;
    private BigDecimal rewardedAmount;
    private String isRewarded;
    private String sl;

}
