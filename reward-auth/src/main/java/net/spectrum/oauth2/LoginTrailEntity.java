package net.spectrum.oauth2;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="logintrail")
public class LoginTrailEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String loginTrailOid;

    @Column(name="loginid")
    private String userId;

    @Column(name="clientid")
    private String clientId;

    @Column(name="roleid")
    private String roleId;

    @Column(name="signintime")
    private Timestamp signInTime;

    @Column(name="signouttime")
    private Timestamp signOutTime;

    @Column(name="loginstatus")
    private String status;

    @Column(name = "machineip")
    private String machineIp;

}
