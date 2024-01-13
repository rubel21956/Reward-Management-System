package net.spectrum.api.rewardamount.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RewardAmountDomain {
    private String id;
    private String oid;
    private String employeeName;
    private String designation;
    private String advancePaidAmount;
    private String appliedRewardAmount;
    private String rewardedAmount;
    private String isRewarded;
    private String createdBy;
    private Timestamp createdOn;
    private String updatedBy;
    private Timestamp updatedOn;
    private String sl;
}
