package net.spectrum.api.application.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String oid;

    //    @DecimalMax(value = "128")
    @Column(name = "customs_reward_file_number")
    private String customsRewardFileNumber;

    @Column(name = "nbr_reward_file_number")
    private String nbrRewardFileNumber;

//    @NotNull
    @Size(max = 128)
    @Column(name = "applying_office_oid", length = 128, nullable = false)
    private String applyingOfficeOid;

//    @NotNull
    @Size(max = 128)
    @Column(name = "bill_of_entry_no", length = 128, nullable = false)
    private String billOfEntryNo;

//    @NotNull
    @Column(name = "bill_of_entry_date", nullable = false)
    private Timestamp billOfEntryDate;

//    @NotNull
    @Column(name = "anomaly_capture_date", nullable = false)
    private Timestamp anomalyCaptureDate;

//    @NotNull
    @Size(max = 128)
    @Column(name = "office_oid", length = 128, nullable = false)
    private String officeOid;

//    @NotNull
    @Size(max = 256)
    @Column(name = "reference_no", length = 256, nullable = false)
    private String referenceNo;

    @Size(max = 256)
    @Column(name = "anomaly_report_no", length = 256)
    private String anomalyReportNo;

    @Column(name = "anomaly_reason")
    private String anomalyReason;

//    @NotNull
    @Size(max = 256)
    @Column(name = "appeal_order_no", length = 256, nullable = false)
    private String appealOrderNo;

//    @NotNull
    @Column(name = "appeal_order_date", nullable = false)
    private Timestamp appealOrderDate;

    @Column(name = "exporter_importer_duty_amount", precision = 21, scale = 2)
    private BigDecimal exporterImporterDutyAmount;

    @Column(name = "collected_duty_and_penalty_amount", precision = 21, scale = 2)
    private BigDecimal collectedDutyAndPenaltyAmount;

    @Column(name = "extra_received_amount", precision = 21, scale = 2)
    private BigDecimal extraReceivedAmount;

    @Size(max = 128)
    @Column(name = "category", length = 128)
    private String category;

    @Column(name = "rewardable_amount", precision = 21, scale = 2)
    private BigDecimal rewardableAmount;

    @Size(max = 128)
    @Column(name = "office_heads_name", length = 128)
    private String officeHeadsName;

    @Column(name = "remarks_from_customs_office")
    private String remarksFromCustomsOffice;

    @Column(name = "suggestion_from_nbr")
    private String suggestionFromNbr;

    @Column(name = "suggestion_status", length = 128)
    private String suggestionStatus;

    @Column(name = "application_date")
    private Timestamp applicationDate;

    @Column(name = "application_status")
    private String applicationStatus;

    @Column(name = "application_status_bn")
    private String applicationStatusBn;

    @Column(name = "application_narration")
    private String applicationNarration;

    @Column(name = "total_rewarded_amount", precision = 21, scale = 2)
    private BigDecimal totalRewardedAmount;

    @Size(max = 128)
    private String officeName;

    @Size(max = 128)
    private String officeAddress;

    @Size(max = 128)
    @Column(name = "customs_award_sanctioning_committee")
    private String customsAwardSanctioningCommittee;

    @Size(max = 128)
    @Column(name = "accused_company_name")
    private String accusedCompanyName;

    @Size(max = 128)
    @Column(name = "accused_company_address")
    private String accusedCompanyAddress;

    @Size(max = 128)
    @Column(name = "created_by", length = 128)
    private String createdBy;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Size(max = 128)
    @Column(name = "updated_by", length = 128)
    private String updatedBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @PrePersist
    public void onInsert() {
        createdOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }

//    @PreUpdate
//    public void onUpdate() {
//        updatedOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
//    }
}