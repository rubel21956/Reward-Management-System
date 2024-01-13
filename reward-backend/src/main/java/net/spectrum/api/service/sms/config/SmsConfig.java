package net.spectrum.api.service.sms.config;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static net.spectrum.api.util.constants.Messages.TIME_OUT;

import java.sql.Timestamp;
import java.util.Date;

@Service
@Slf4j
public class SmsConfig implements SmsInterface{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.baseUrl}")
    private String smsBaseUrl;

    @Value("${sms.access-token}")
    private String accessToken;

//    @HystrixCommand(commandKey = "ftTimeOut", fallbackMethod = "sendSmsFallBack")
    public ResponseEntity<JsonNode> sendSms(String requestId, String mobileNo, String sms) {
//        RequestHeader requestHeader = new RequestHeader();
        SendSmsRequest request = SendSmsRequest.builder()
                .sms(sms)
                .mobile_no(mobileNo)
                .sms_type("aw-ums-credential")
                .request_id(requestId)
                .request_time(new Timestamp(new Date().getTime()))
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("client_id", "asycuda-ums");
        headers.add("client_secret", "45rt-h39679");
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                smsBaseUrl ,
                HttpMethod.POST,
                new HttpEntity<>(request, headers),
                JsonNode.class
        );
       log.info("Sending request : {}",request);
        return response;
    }


    ResponseEntity<JsonNode> sendSmsFallBack(String requestId, String mobileNo, String sms) throws ExceptionHandlerUtil {
        throw new ExceptionHandlerUtil(HttpStatus.REQUEST_TIMEOUT, TIME_OUT);
    }

}
