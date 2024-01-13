package net.spectrum.api.application.dto;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import net.spectrum.api.rewardamount.entity.RewardAmountEntity;

//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCustomsStepOneDto {

    private String oid;

    @NotNull
    @Size(max = 128)
    private String applyingOfficeOid;

    @NotNull
    @Size(max = 128)
    private String billOfEntryNo;
    @NotNull
    private Timestamp billOfEntryDate;

    @NotNull
    @Size(max = 128)
    private String officeOid;

    private String officeName;

    private String officeAddress;

    @NotNull
    private Timestamp anomalyCaptureDate;

    @NotNull
    @Size(max = 256)
    private String referenceNo;

    @Size(max = 256)
    private String anomalyReportNo;

    private String anomalyReason;

    @NotNull
    @Size(max = 256)
    private String appealOrderNo;

    @NotNull
    private Timestamp appealOrderDate;

    private BigDecimal exporterImporterDutyAmount;

    private BigDecimal collectedDutyAndPenaltyAmount;

    private BigDecimal extraReceivedAmount;

    @Size(max = 128)
    private String category;

    private BigDecimal rewardableAmount;

    @Size(max = 128)
    private String officeHeadsName;

    private String remarksFromCustomsOffice;

    @Size(max = 128)
    private String accusedCompanyName;

    @Size(max = 128)
    private String accusedCompanyAddress;

    private List<RewardAmountEntity> rewardAmounts;

    private List<ApplicationAttachmentEntity> applicationAttachments;

    private String nbrRewardFileNumber;

    private String customsRewardFileNumber;
    @NotNull
    private String applicationStatus;
    private String applicationStatusBn;
    private Timestamp updatedOn;
    private Timestamp applicationDate;
}
