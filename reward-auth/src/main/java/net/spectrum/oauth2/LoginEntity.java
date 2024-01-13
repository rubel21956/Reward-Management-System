package net.spectrum.oauth2;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Nullable;
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
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "oid", updatable = false, nullable = false)
    private String loginOid;

    @Column(name="userid")
    private String userId;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    @Column(name="roleoid")
    private String roleOid;

    @Column(name="status")
    private String status;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name="editedon")
    private Timestamp editedOn;

    @Column(name="currentversion")
    private String currentVersion;

    @Column(name="resetrequired")
    private String resetRequired;

    @Nullable
    @Column(name="reset")
    private boolean reset;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "userrole",
            joinColumns = @JoinColumn(name = "loginoid", referencedColumnName = "oid"),
            inverseJoinColumns = @JoinColumn(name = "roleoid", referencedColumnName = "roleoid")
    )
    private Collection<RoleEntity> roles;
}
