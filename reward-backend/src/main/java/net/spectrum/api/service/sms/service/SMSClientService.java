package net.spectrum.api.service.sms.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SMSClientService {
    @Autowired
    private SMSClientImpl smsClient;

    public void sendSmsByPostmaster(String requestId, String mobileNo, String sms) throws ExceptionHandlerUtil {
        log.info("Sending sms request to postmaster for {}, {}", requestId, mobileNo);
        try {
            ResponseEntity<JsonNode> response = smsClient.sendSms(requestId, mobileNo, sms);
            log.info("Received postmaster response for sms sending {}", response);
        } catch (Exception e) {
            log.error("Exception occurred during sending sms : {}", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "SMS sending is failed!");
        }
    }
}
