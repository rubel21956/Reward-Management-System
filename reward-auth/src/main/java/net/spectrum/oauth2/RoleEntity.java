package net.spectrum.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="roleoid", updatable = false, nullable = false)
    private  String roleOid;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "status")
    private String status;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "rolepermission",
            joinColumns = @JoinColumn(name = "roleoid", referencedColumnName = "roleoid"),
            inverseJoinColumns = @JoinColumn(name = "permissionoid", referencedColumnName = "permissionoid")
    )
    private Collection<PermissionEntity> permissions;



}
