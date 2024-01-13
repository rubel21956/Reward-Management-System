package net.spectrum.api.users.userprofile.entity;

import java.sql.Timestamp;
import java.util.Date;
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
@Table(name = "userprofile")
public class UserProfileEntity {

        @Id
        @GeneratedValue(generator = "UUID")
        @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
        @Column(name = "oid", updatable = false, nullable = false)
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

        @Column(name = "ismobileverified")
        private String isMobileVerified;

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

        @Column(name = "createdby")
        private String createdBy;

        @Column(name = "createdon")
        private Timestamp createdOn;

        @Column(name = "updatedby")
        private String updatedBy;

        @Column(name = "updatedon")
        private Timestamp updatedOn;
    }

