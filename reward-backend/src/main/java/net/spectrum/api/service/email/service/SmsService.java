package net.spectrum.api.service.email.service;


import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.service.sms.config.SmsConfig;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SmsService {
    @Autowired
    private SmsConfig smsClient;

    public void sendSms(String requestId, String mobileNo, String sms) throws ExceptionHandlerUtil {
        log.info("Sending sms request to postmaster for {}, {}", requestId, mobileNo);
        try {
            ResponseEntity<JsonNode> response = smsClient.sendSms(requestId, mobileNo, sms);
            log.info("Received drws response for sms sending {}", response);
        } catch (Exception e) {
            log.error("Exception occurred during sending sms : {}", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "SMS sending is failed!");
        }
    }
}
