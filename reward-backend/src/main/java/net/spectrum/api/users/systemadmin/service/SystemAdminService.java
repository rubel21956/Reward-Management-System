package net.spectrum.api.users.systemadmin.service;

import java.sql.Timestamp;
import java.util.Date;
import net.spectrum.api.auth.login.entity.LoginEntity;
import net.spectrum.api.auth.login.repository.LoginRepository;
import net.spectrum.api.users.operator.dto.OperatorDto;
import net.spectrum.api.users.userrole.entity.UserRoleEntity;
import net.spectrum.api.users.operator.entity.OperatorEntity;
import net.spectrum.api.users.useractivity.repository.UserActivityRepository;
import net.spectrum.api.users.userprofile.entity.UserProfileEntity;
import net.spectrum.api.users.userprofile.repository.UserProfileRepository;
import net.spectrum.api.users.userrole.repository.UserRoleRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SystemAdminService {
    @Autowired
    LoginRepository loginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public void saveOperatorInOthers(OperatorEntity operatorEntity, OperatorDto operatorDto, String userId, String oldUserId) throws ExceptionHandlerUtil {

        String defaultPassword = operatorDto.getPassword();
        String hashPass = passwordEncoder.encode(defaultPassword);
        LoginEntity loginEntity = loginRepository.findByUserId(operatorEntity.getUserId());

        if(loginEntity != null){
            // Inserting into login Table
            loginEntity.setLoginOid(operatorEntity.getUserId());
            loginEntity.setUserId(operatorEntity.getUserId());
            loginEntity.setRoleOid(operatorEntity.getRoleOid());
            loginEntity.setStatus(operatorEntity.getStatus());
            loginEntity.setCurrentVersion("1");
            loginEntity.setResetRequired("No");
            loginEntity.setCreatedOn(new Timestamp(new Date().getTime()));
            loginEntity = this.loginRepository.save(loginEntity);
        }else if(loginEntity == null){
            // Inserting into login Table
            loginEntity = new LoginEntity();
            loginEntity.setLoginOid(operatorEntity.getUserId());
            loginEntity.setUserId(operatorEntity.getUserId());
            loginEntity.setRoleOid(operatorEntity.getRoleOid());
            loginEntity.setPassword(hashPass);
            loginEntity.setStatus(operatorEntity.getStatus());
            loginEntity.setCurrentVersion("1");
            loginEntity.setResetRequired("Yes");
            loginEntity.setCreatedOn(new Timestamp(new Date().getTime()));
            loginEntity = this.loginRepository.save(loginEntity);
        }

//        // Inserting into login Table
//        loginEntity.setLoginOid(operatorEntity.getUserId());
//        loginEntity.setUserId(operatorEntity.getUserId());
//        loginEntity.setRoleOid(operatorEntity.getRoleOid());
//        loginEntity.setPassword(hashPass);
//        loginEntity.setStatus(operatorEntity.getStatus());
//        loginEntity.setCurrentVersion("1");
//        loginEntity.setResetRequired("No");
//        loginEntity.setCreatedOn(new Timestamp(new Date().getTime()));
//        loginEntity = this.loginRepository.save(loginEntity);


        // Inserting into userProfile table
        UserProfileEntity userProfileEntity = userProfileRepository.findByLoginOid(operatorEntity.getUserId());
        if(userProfileEntity != null){
            userProfileEntity.setName(operatorEntity.getName());
            userProfileEntity.setCurrentOffice(operatorEntity.getOfficeCode());
            userProfileEntity.setDob(operatorEntity.getDob());
            userProfileEntity.setNidNumber(operatorEntity.getNidNumber());
            userProfileEntity.setJoinDate(operatorEntity.getJoiningDate());
            userProfileEntity.setMobileNo(operatorEntity.getContactNumber());
            userProfileEntity.setAwUserStatus(operatorEntity.getStatus());
            userProfileEntity.setAwUserStatus(operatorEntity.getStatus());
            userProfileEntity.setCreatedBy(userId);
            userProfileEntity.setCreatedOn(new Timestamp(new Date().getTime()));
            userProfileEntity.setDesignation(operatorEntity.getDesignation());
            userProfileEntity.setEmail(operatorEntity.getEmail());
            userProfileRepository.save(userProfileEntity);
        }else if(userProfileEntity == null){
            userProfileEntity = new UserProfileEntity();
            userProfileEntity.setLoginOid(operatorEntity.getUserId());
            userProfileEntity.setName(operatorEntity.getName());
            userProfileEntity.setCurrentOffice(operatorEntity.getOfficeCode());
            userProfileEntity.setDob(operatorEntity.getDob());
            userProfileEntity.setNidNumber(operatorEntity.getNidNumber());
            userProfileEntity.setJoinDate(operatorEntity.getJoiningDate());
            userProfileEntity.setMobileNo(operatorEntity.getContactNumber());
            userProfileEntity.setAwUserStatus(operatorEntity.getStatus());
            userProfileEntity.setCreatedBy(userId);
            userProfileEntity.setCreatedOn(new Timestamp(new Date().getTime()));
            userProfileEntity.setDesignation(operatorEntity.getDesignation());
            userProfileEntity.setEmail(operatorEntity.getEmail());
            userProfileEntity = this.userProfileRepository.save(userProfileEntity);
        }




//        UserActivityEntity userActivityEntity = new UserActivityEntity();
//        //Inserting into userLog table
//        userActivityEntity.setUserHistoryOid(userId);
//        userActivityEntity.setSender(userId);
//        userActivityEntity.setReceiver("");
//        userActivityEntity.setSenderRemarks("Operator Created");
//        userActivityEntity.setApplicationStatusOid("7");
//        userActivityEntity.setCreatedOn(new Timestamp(new Date().getTime()));
//        userActivityEntity = userActivityRepository.save(userActivityEntity);


        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setLoginOid(loginEntity.getLoginOid());
        userRoleEntity.setRoleOid(loginEntity.getRoleOid());
        userRoleEntity = this.userRoleRepository.save(userRoleEntity);

        //c

        if (loginEntity.getLoginOid() == null)
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "User Login not saved");
        if (userProfileEntity.getOid() == null)
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "User Profile not saved");
        //c
//        if (userActivityEntity.getOid() == null)
//            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "User Log not saved");
//        OperatorResponseDto operatorResponseDto = new OperatorResponseDto();
    }
}
