package net.spectrum.api.service.email.send;

import java.io.File;
import java.util.Properties;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Service
public class EmailSendingService {

//    @Value("${spring.mail.username}")
//    private String sendingEmail;

//    @Autowired
   // private JavaMailSender mailSender;

    //    @HystrixCommand(commandKey = "sendEmailCo", fallbackMethod = "sendSmsFallBack")
    public void sendEmail(String emailId, String subject, String body) {
        final String username = "reward.nbr@bdcustoms.gov.bd";
        final String password = "KR@&Ss780Ui";
        int resCode = 200;
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "mail.bdcustoms.gov.bd");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("reward.nbr@bdcustoms.gov.bd"));
//            message.setFrom(new InternetAddress("milonsmtp@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailId));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully.");

        } catch (MessagingException e) {
            resCode = 400;
            e.printStackTrace();
        }
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(sendingEmail);
//        message.setTo(toEmail);
//        message.setText(body);
//        message.setSubject(subject);
//        mailSender.send(message);
    }

//    public void sendEmailWithAttachment(String toEmail, String body, String subject, String attachment)
//    public void sendEmailWithAttachment(String toEmail, String body, String subject)
//        throws MessagingException {
//        MimeMessage mimeMessage= mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true );
//
//        mimeMessageHelper.setFrom(sendingEmail);
//        mimeMessageHelper.setTo(toEmail);
//        mimeMessageHelper.setText(body);
//        mimeMessageHelper.setSubject(subject);
//
////        FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
////        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
//        mailSender.send(mimeMessage);
//        System.out.println("EMail with file attachement sent successfully");
//    }
}
