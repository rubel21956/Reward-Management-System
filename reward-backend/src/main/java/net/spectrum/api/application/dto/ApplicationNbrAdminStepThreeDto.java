package net.spectrum.api.application.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.spectrum.api.rewardamount.entity.RewardAmountEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationNbrAdminStepThreeDto {
    private String oid;
    private List<RewardAmountEntity> rewardAmounts;
    private BigDecimal totalRewardedAmount;
}
