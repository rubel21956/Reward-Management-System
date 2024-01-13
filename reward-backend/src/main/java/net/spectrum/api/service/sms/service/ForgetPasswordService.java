package net.spectrum.api.service.sms.service;

import static net.spectrum.api.util.constants.Messages.USER_NOT_FOUND;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.auth.login.entity.LoginEntity;
import net.spectrum.api.auth.login.repository.LoginRepository;
import net.spectrum.api.service.email.send.EmailSendingService;
import net.spectrum.api.service.notificationlog.entity.NotificationLogEntity;
import net.spectrum.api.service.notificationlog.repository.NotificationLogRepository;
import net.spectrum.api.service.sms.ForgetPasswordRequestDto;
import net.spectrum.api.users.userprofile.entity.UserProfileEntity;
import net.spectrum.api.users.userprofile.repository.UserProfileRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ForgetPasswordService {

    //    @Value("${smsFlag}")
    private boolean smsFlag = false;

//    @Value("${customs.log.path}")
//    private String customsLogoPath;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserProfileRepository userProfileRepository;
//
//    @Autowired
//    LoginTrailRepository loginTrailRepository;

    @Autowired
    EmailSendingService emailSendingService;

    @Autowired
    private SMSClientService smsClientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationLogRepository notificationLogRepository;

//    @Autowired
//    private ApplicationCustomRepository applicationCustomRepository;

    public ResponseEntity<Map<String, Object>> setPassword(ForgetPasswordRequestDto requestDTO) throws ExceptionHandlerUtil {


        //List<ApplicationProfileHistoryResponseDTO> userDetail = applicationCustomRepository.getUserProfileDetails(requestDTO.getUserId(), requestDTO.getNid(), requestDTO.getRecoveryType());
//        if (userDetail.size() == 0) {
//            if( requestDTO.getRecoveryType().equalsIgnoreCase("nid")){
//                throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "NID is not found");
//            } else{
//                throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, USER_NOT_FOUND);
//            }
//        }

        LoginEntity loginEntity = loginRepository.findByUserId(requestDTO.getUserId());
        UserProfileEntity userProfile = userProfileRepository.findByLoginOid(requestDTO.getUserId());
//        String otp = new DecimalFormat("000000").format(new Random().nextInt(999999));
        String newPassword = Utility.getOtp();
        String hashedPassword = passwordEncoder.encode(newPassword);
        loginEntity.setPassword(hashedPassword);
        loginEntity.setOtp(newPassword);
        loginEntity.setEditedOn(Timestamp.valueOf(LocalDateTime.now()));
        loginEntity.setResetRequired("Yes");
        loginEntity.setReset(true);
        loginRepository.save(loginEntity);
        //fetching userprofile
//        UserProfileEntity userProfileEntity = userProfileRepository.findByLoginOid(userDetail.getLoginOid());

        boolean emailSend = false;

//        try {
//            String userEmail = userProfile.getEmail();
//            String emailBody = "Dear " + userProfile.getName() + ", \nYour temporary password has been reset successfully as:  " + newPassword + " \n Please login to the system and change the password. \n \n Regards, \n Admin \n ASYCUDA World System";
//            String subject = "Temporary password for User Management System";
//            emailSendingService.sendEmail(userEmail, emailBody, subject);
//            emailSend = true;
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println("Email Sending is failed");
//        }

//        if (!emailSend) {
//            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "Email sending is failed!");
//        }

        boolean smsSend = false;
//        boolean emailSend = false;

        if (smsFlag == true) {
            String smsBody = "";
            String emailBody = "";
            try {
                String requestId = UUID.randomUUID().toString();
                String mobileNo = userProfile.getMobileNo();
                smsBody = "Dear "+userProfile.getName()+"! Your User Id: " + loginEntity.getUserId() + " and temporary Password: " + newPassword + ".\n" +
                        "Thanks, Admin, AW System";
                smsClientService.sendSmsByPostmaster(requestId, mobileNo, smsBody);
                smsSend = true;
            } catch (Exception e) {
                System.out.println("SMS Sending is failed");
            }

//            try {
//                String email = userProfile.getEmail();
//                emailBody = "Dear " + userProfile.getName() + ", \n \nYour User Id: " + loginEntity.getUserId()+ " \n and temporary Password: " + newPassword + ".\n \n Thank you \n ASYCUDA Team \n "+
//                    "National Board of Revenue \n Rajswa Bhaban, Segunbagicha, \n Dhaka-1000, Bangladesh";
//                String subject = "User credentials for ASYCUDA World System ";
//                emailSendingService.sendEmailWithAttachment(email, emailBody, subject);
//               // emailSendingService.sendEmailWithAttachment(email, emailBody, subject, customsLogoPath);
//                emailSend = true;
//            } catch (Exception e) {
//                System.out.println("Email Sending is failed");
//            }

            if (!smsSend && !emailSend) {
                throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "SMS and Email sending are failed!");
            }

            NotificationLogEntity notificationLogEntity = new NotificationLogEntity();
            notificationLogEntity.setLoginOid(loginEntity.getLoginOid());
            notificationLogEntity.setEmail(userProfile.getEmail());
            notificationLogEntity.setMobileNo(userProfile.getMobileNo());
            notificationLogEntity.setSmsBody(smsBody);
            notificationLogEntity.setEmailBody(emailBody);
            notificationLogEntity.setCreatedOn(new Timestamp(new Date().getTime()));
            notificationLogEntity.setCreatedBy(requestDTO.getUserId());

            notificationLogEntity = this.notificationLogRepository.save(notificationLogEntity);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userMessage", "userId:"+loginEntity.getUserId()+" and new password:"+newPassword+"");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public boolean sendEmailToUser(String userId){
        log.info("Email sending to user");
        LoginEntity loginEntity = loginRepository.findByUserId(userId);
        UserProfileEntity userProfile = userProfileRepository.findByLoginOid(userId);

        try {
            String userEmail = userProfile.getEmail();
            String emailBody = "Dear " + userProfile.getName() + ", \nYour password has been reset successfully as:  " + loginEntity.getOtp() + " \n Please login to the system and change the password. \n \n Regards, \n Admin \n ASYCUDA World System";
            String subject = "Temporary password for Reward Management System";
            emailSendingService.sendEmail(userEmail,subject,emailBody);
            loginEntity.setReset(false);
            loginRepository.save(loginEntity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Email Sending is failed");
            return false;
        }
    }


}
