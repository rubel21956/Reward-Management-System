package net.spectrum.api.auth.role.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

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

//    @Column(name = "officeoid")
//    private String officeOid;

    @Column(name = "rolename")
    private String roleName;

    @Column(name = "status")
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "updatedon")
    private Timestamp updatedOn;

}
