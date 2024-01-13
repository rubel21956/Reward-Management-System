package net.spectrum.api.users.userprofile.dto;

import com.google.gson.GsonBuilder;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequestDTO {

//    @NotNull(message = "name must not be null")
//    @NotEmpty(message = "name must not be empty")

    private String userId;

    private String userCategory;
    private String awRole;

    private String password;

    private String confirmpassword;

    private String roleOid;

    private String name;

    private String email;

    private String mobileNo;

    private Date dob;

    private String nidNumber;

    private String nidPath;

    private String presentAddress;

    private String permanentAddress;

    private String photoPath;

    private String signaturePath;


    private String designation;

    private String userHistoryOid;

    private String mobileResponse;

    private String nidResponse;

    private String currentOffice;
    private String currentSection;

    private String licenseNumber;

    private String applicationStatus;

    private String userSpecificProperties;

    private String stakeHolderCategory;

    private String licenseCategory;


    //UserHistory

    private String approverOid;
    private String itUserOid;
    private String orderNo;

    private Timestamp orderDate;

    private String orderSL;

    private String orderOffice;

    private String orderSection;

    private String orderDocumentPath;


    //UserLog
    private String remarks;

    //Otplog
    private String otp;
    private String otpLogOid;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}
