package net.spectrum.api.office.entity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "office")
public class OfficeEntity {

    @Id
    @Column(name = "oid", updatable = false, nullable = false)
    private String oid;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "bn_name")
    private String bNname;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "bn_address2")
    private String bNaddress2;

    @Column(name = "status")
    private String status;

    @Column(name = "created_on")
    private Timestamp createdOn;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Timestamp updatedOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @PrePersist
    public void onInsert() {
        createdOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }

    @PreUpdate
    public void onUpdate() {
        updatedOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }

}
