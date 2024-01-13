package net.spectrum.api.service.email.service;


//import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    private static final String email = "milonsmtp@gmail.com";

/**
 * It takes three parameters, toEmail, body and subject. It creates a SimpleMailMessage object and sets
 * the from, to, text and subject fields. Finally, it sends the message using the mailSender object
 * 
 * @param toEmail The email address of the recipient.
 * @param body The body of the email.
 * @param subject The subject of the email.
 */
    public void sendEmail(String toEmail, String body, String subject){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        //message.setFrom("aw.ums@bdcustoms.gov.bd");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
    }

//    public void sendEmailWithAttachment(String toEmail, String body, String subject, String attachment)
//            throws MessagingException {
//          MimeMessage mimeMessage= mailSender.createMimeMessage();

//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true );

//            mimeMessageHelper.setFrom("milonsmtp@gmail.com");
//            mimeMessageHelper.setTo(toEmail);
//            mimeMessageHelper.setText(body);
//            mimeMessageHelper.setSubject(subject);

//            FileSystemResource fileSystemResource = new FileSystemResource(new File(attachment));
//    }
}
