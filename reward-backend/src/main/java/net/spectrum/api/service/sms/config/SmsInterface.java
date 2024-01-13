package net.spectrum.api.service.sms.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.GsonBuilder;
import lombok.*;

import java.sql.Timestamp;
import java.util.Map;

public interface SmsInterface {

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    class Request {
        private RequestHeader header;
        private Map<String, Object> body;
    }

    @Setter
    @Getter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class RequestHeader {
         private String client_id;
         private String client_secret;

        public RequestHeader() {
            this.client_id = "asycuda-ums";
            this.client_secret = "45rt-h39679";
        }

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class ResponseHeader {
           private Timestamp request_receive_time;
           private Timestamp  response_time;
           private Long process_duration_ms;
           private int code;
           private Boolean status;
           private String message;
    }


    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SendSmsRequest {
        private  String sms;

        private  String  mobile_no;

        private  String sms_type;

        private  String request_id;

        private Timestamp request_time;

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class SendSmsRequestBody {

        private  String sms;

        private  String  mobile_no;

        private  String sms_type;

        private  String request_id;

        private Timestamp request_time;

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }

    }
}
