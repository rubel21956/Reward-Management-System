package net.spectrum.api.auth.login.entity;

import lombok.*;
import net.spectrum.api.auth.role.entity.RoleEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="login")
public class LoginEntity {

    @Id
    @Column(name = "oid")
    private String loginOid;

    @Column(name="userid")
    private String userId;

    @Column(name="roleoid")
    private String roleOid;

    @Column(name="password")
    private String password;

    @Column(name="status")
    private String status;

    @Column(name = "createdon")
    @CreationTimestamp
    private Timestamp createdOn;

    @Column(name="editedon")
    @UpdateTimestamp
    private Timestamp editedOn;

    @Column(name="currentversion")
    private String currentVersion;

    @Column(name="resetrequired")
    private String resetRequired;

    @Column(name="otp")
    private String otp;

    @Column(name="reset")
    private boolean reset;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "userrole",
            joinColumns = @JoinColumn(name = "loginoid", referencedColumnName = "oid"),
            inverseJoinColumns = @JoinColumn(name = "roleoid", referencedColumnName = "roleoid")
    )
    private Collection<RoleEntity> roles;
}
