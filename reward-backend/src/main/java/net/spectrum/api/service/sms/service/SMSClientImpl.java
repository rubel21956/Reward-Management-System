package net.spectrum.api.service.sms.service;

import static net.spectrum.api.util.constants.Messages.TIME_OUT;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class SMSClientImpl implements AbsClientService {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${sms.baseUrl}")
    private String smsBaseUrl;

//    @Value("${send.sms}")
//    private String sendSms;

    @Value("${sms.access-token}")
    private String accessToken;

    @Autowired
    private ObjectMapper objectMapper;

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
//        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("client_id", "asycuda-ums");
        headers.add("client_secret", "45rt-h39679");
        ResponseEntity<JsonNode> response = restTemplate.exchange(
            smsBaseUrl,
            HttpMethod.POST,
            new HttpEntity<>(request, headers),
            JsonNode.class
        );
        log.info("Sending request : {}", request);
        return response;
    }


    ResponseEntity<JsonNode> sendSmsFallBack(String requestId, String mobileNo, String sms) throws ExceptionHandlerUtil {
        throw new ExceptionHandlerUtil(HttpStatus.REQUEST_TIMEOUT, TIME_OUT);
    }
}
