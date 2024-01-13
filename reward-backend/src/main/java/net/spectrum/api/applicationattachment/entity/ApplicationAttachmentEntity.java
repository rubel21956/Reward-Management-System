package net.spectrum.api.applicationattachment.entity;

import java.io.Serializable;
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
 * A ApplicationAttachment.
 */

@Entity
@Table(name = "application_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationAttachmentEntity implements Serializable {

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
    @Column(name = "file_name", length = 128, nullable = false)
    private String fileName;


    @Size(max = 128)
    @Column(name = "original_file_name", length = 128, nullable = true)
    private String originalFileName;

    @NotNull
    @Size(max = 128)
    @Column(name = "attachment_type", length = 128, nullable = false)
    private String attachmentType;

    @NotNull
    @Size(max = 128)
    @Column(name = "file_url", length = 128, nullable = false)
    private String fileUrl;

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

    @PrePersist
    public void onInsert() {
        createdOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
        /** updatedOn = createdOn; **/
    }

    @PreUpdate
    public void onUpdate() {
        updatedOn = Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant());
    }

}

