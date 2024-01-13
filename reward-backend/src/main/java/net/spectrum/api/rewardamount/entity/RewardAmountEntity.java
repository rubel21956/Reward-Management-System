package net.spectrum.api.rewardamount.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

/**
 * A RewardAmount.
 */
@Entity
@Table(name = "reward_amount")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardAmountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Size(max = 128)
    @Column(name = "oid", length = 128, nullable = false)
    private String oid;

    @NotNull
    @Size(max = 128)
    @Column(name = "employee_name", length = 128, nullable = false)
    private String employeeName;

    @Size(max = 128)
    @Column(name = "designation", length = 128, nullable = false)
    private String designation;

    @Column(name = "advance_paid_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal advancePaidAmount;

    @Column(name = "applied_reward_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal appliedRewardAmount;

    @Column(name = "rewarded_amount", precision = 21, scale = 2)
    private BigDecimal rewardedAmount;

    @Column(name = "is_rewarded")
    private String isRewarded;

    @Size(max = 128)
    @Column(name = "created_by", length = 128)
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_on")
    private Timestamp createdOn;

    @Size(max = 128)
    @Column(name = "updated_by", length = 128)
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Size(max = 128)
    @Column(name = "sl", length = 128)
    private String sl;

    @PrePersist
    public void onInsert() {
        createdOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
        //updatedOn = createdOn;
    }

    @PreUpdate
    public void onUpdate() {
        updatedOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }
}