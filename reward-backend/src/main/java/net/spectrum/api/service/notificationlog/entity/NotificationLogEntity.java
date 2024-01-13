package net.spectrum.api.service.notificationlog.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "notificationlog")
public class NotificationLogEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String oid;

    @Column(name = "loginoid")
    private String loginOid;

    @Column(name = "mobileno")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "smsbody")
    private String smsBody;

    @Column(name = "emailbody")
    private String emailBody;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "updatedon")
    private Timestamp updatedOn;

    @Column(name = "updatedby")
    private String updatedBy;

}

