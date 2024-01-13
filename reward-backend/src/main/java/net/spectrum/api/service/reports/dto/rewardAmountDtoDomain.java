package net.spectrum.api.service.reports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

//This Class is Made By Arif.
//Reason: To make jasper reports number data in bangla fonts. Cause Big decemal cannot be converted to bangla fonts.
//        It is a replica of RewardAmountDto Class with all property in String.
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class rewardAmountDtoDomain {
    private String oid;
    private String employeeName;
    private String rejectedName;
    private String designation;
    private String advancePaidAmount;
    private String appliedRewardAmount;
    private String rewardedAmount;
    private String isRewarded;
    private String sl;
}
