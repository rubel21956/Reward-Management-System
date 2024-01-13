package net.spectrum.api.users.operator.dto;

import java.sql.Date;
import java.sql.Timestamp;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OperatorDto {
    private String oid;
    @Size(max = 128)
    private String name;
    private String officeCode;
    private Date dob;
    @Size(max = 128)
    private  String nidNumber;
    private Timestamp joiningDate;
    @Size(max = 128)
    private String contactNumber;
    @Size(max = 128)
    private String status;
    private String userId;
    private String message;
    private String designation;
    private String email;
    private String password;
}
