package net.spectrum.api.users.operator.entity;

import java.io.Serializable;
import java.sql.Date;
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
import javax.validation.constraints.Size;

import brave.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperatorEntity  implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String oid;

    @Size(max = 128)
    private String name;


    private String officeCode;

    private Date dob;

    @Size(max = 128)
    private String nidNumber;

    private Timestamp joiningDate;

    @Size(max = 128)
    private String contactNumber;

    @Size(max = 128)
    private String status;

    private String userId;

    private String roleOid;

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
    private String designation;

    @Size(max = 128)
    private String email;


//    @PrePersist
//    public void onInsert() {
//        createdOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
//    }
//
//    @PreUpdate
//    public void onUpdate() {
//        updatedOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
//    }
}
