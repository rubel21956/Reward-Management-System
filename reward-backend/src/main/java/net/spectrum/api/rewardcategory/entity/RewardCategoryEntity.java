package net.spectrum.api.rewardcategory.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * A RewardCategory.
 */
@Entity
@Table(name = "reward_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RewardCategoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
//    @SequenceGenerator(name = "sequenceGenerator")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String id;

    @NotNull
    @Size(max = 128)
    @Column(name = "application_id", length = 128, nullable = false)
    private String applicationId;

    @NotNull
    @Size(max = 128)
    @Column(name = "category", length = 128, nullable = false)
    private String category;

    @NotNull
    @Max(value = 128)
    @Column(name = "percentage", nullable = false)
    private Integer percentage;

    @NotNull
    @Size(max = 128)
    @Column(name = "amount", length = 128, nullable = false)
    private String amount;

    @NotNull
    @Column(name = "is_rewarded", nullable = false)
    private Boolean isRewarded;

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
}

