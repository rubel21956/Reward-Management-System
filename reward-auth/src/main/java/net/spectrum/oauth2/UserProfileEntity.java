package net.spectrum.oauth2;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name="userprofile")
public class UserProfileEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name="oid", updatable = false, nullable = false)
    private String oid;

    @Column(name = "loginoid")
    private String loginOid;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileno")
    private String mobileNo;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "nidnumber")
    private String nidNumber;

    @Column(name = "nidpath")
    private String nidPath;

    @Column(name = "presentaddress")
    private String presentAddress;

    @Column(name = "permanentaddress")
    private String permanentAddress;

    @Column(name = "photopath")
    private String photoPath;

    @Column(name = "signaturepath")
    private String signaturePath;

    @Column(name = "designation")
    private String designation;

    @Column(name = "mobileresponse")
    private String mobileResponse;

    @Column(name = "nidresponse")
    private String nidResponse;

    @Column(name = "currentoffice")
    private String currentOffice;

    @Column(name = "currentsection")
    private String currentSection;

    @Column(name = "awuserstatus")
    private String awUserStatus;

    @Column(name = "licensenumber")
    private String licenseNumber;

    @Column(name = "userspecificproperties")
    private String userSpecificProperties;

    @Column(name = "joindate")
    private Timestamp joinDate;

    @Column(name = "releasedate")
    private Timestamp releaseDate;

    @Column(name = "awrole")
    private String awRole;

    @Column(name = "stakeholdercategory")
    private String stakeHolderCategory;

    @Column(name = "approvertype")
    private String approverType;

    @Column(name = "licensecategory")
    private String licenseCategory;

    @Column(name = "awpassword")
    private String awPassword;

    @Column(name = "ismobileverified")
    private String isMobileVerified;


    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "createdon")
    private Timestamp createdOn;

    @Column(name = "updatedby")
    private String updatedBy;

    @Column(name = "updatedon")
    private Timestamp updatedOn;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "userrole",
            joinColumns = @JoinColumn(name = "loginoid", referencedColumnName = "oid"),
            inverseJoinColumns = @JoinColumn(name = "roleoid", referencedColumnName = "roleoid")
    )
    private Collection<RoleEntity> roles;
}
