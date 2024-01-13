package net.spectrum.api.service.sms.service;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.gson.GsonBuilder;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Service
public interface AbsClientService {

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    class Request {
        private RequestHeader header;
        //        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private Map<String, Object> body;
    }

    @Setter
    @Getter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class RequestHeader {
        //        private String requestClient;
//        private String requestId;
//        private int requestRetryCount;
//        private String requestSource;
//        private String requestSourceService;
//        private String requestTime;
//        private int requestTimeoutInSeconds;
//        private String requestType;
//        private String requestVersion;
//        private String traceId;
        private String client_id;
        private String client_secret;

        public RequestHeader() {
            this.client_id = "asycuda-ums";
            this.client_secret = "45rt-h39679";

//            this.requestRetryCount = 0;
//            this.requestSource = "account-service";
//            this.requestSourceService = requestSourceService;
//            this.requestTime = DateTime.now().toString("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
//            this.requestTimeoutInSeconds = -1;
//            this.requestType = requestType;
//            this.requestVersion = "1.0";
//            this.traceId = "";
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
    @AllArgsConstructor
    class CoreCorrectResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private ResponseBody body;

        public CoreCorrectResponse() {
            header = new ResponseHeader();
            body = new ResponseBody();
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class CoreCorrectMobileNoUpdateResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private CoreCorrectMobileNoUpdateResponseBody body;

        public CoreCorrectMobileNoUpdateResponse() {
            header = new ResponseHeader();
            body = new CoreCorrectMobileNoUpdateResponseBody();
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    class CoreCorrectMobileNoUpdateResponseBody {
        public String ofsReference;
        public String ofsDateTime;
        public String currNo;
        public String inputter;
        public String authoriser;
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class ResponseHeader {
        //        private String requestId;
//        private String requestReceivedTime;
//        private String responseCode;
//        private String responseMessage;
//        private int responseProcessingTimeInMs;
//        private String responseTime;
//        private String responseVersion;
//        private String traceId;
//        private int hopCount;
        private Timestamp request_receive_time;
        private Timestamp response_time;
        private Long process_duration_ms;
        private int code;
        private Boolean status;
        private String message;
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
//    @ToString
    class ResponseBody {
        private String cusShortName;
        private String cusName1;
        private String cusName2;
        private String cusStreet;
        private String cusTownCountry;
        private String cusPostCode;
        private String cusCountry;
        private String cusSector;
        private String cusMnemonic;
        private String cusIndustry;
        private String cusTarget;
        private String cusNationality;
        private String cusCustomerStatus;
        private String cusResidence;
        private String cusIntroducer;
        private String cusCompanyBook;
        private String cusIssueCheques;
        private String cusTitle;
        private String cusGivenNames;
        private String cusFamilyName;
        private String cusGender;
        private String cusDateOfBirth;
        private String cusMaritalStatus;
        private String cusPhone1;
        private String cusSms1;
        private String cusEmail1;
        private String cusOccupation;
        private String cusTaxI;
        private String cusInternetBankingService;
        private String cusMobileBankingService;
        private String cusRecordStatus;
        private Integer cusAddressLineNo;
        private Map<String, String> cusAddress;
        private Integer cusLegalRecordNo;
        private Map<String, String> cusLocalRef;
        private String bankAccountNumber;
        private List<CdLegalDoc> cusLegalDocs;

        private String bankCustomerId;
        private String acCategory;
        private String acAccountTitle1;
        private String acMnemonic;
        private String acCurrency;
        private String acPostingRestrict;
        private String acInactivMarker;
        private String altAcctType;
        private String altAcctId;
        private String acRecordStatus;
        private String branchOid;
        private String acOpeningDate;
        private String acClosureDate;
        private String acOverride;
        private Double acLockedAmount;
        private Double acWorkingBalance;
        private Map<String, String> acLocalRef;

        private String cusEcoPurCode;
        private String cusCbSecCode;
        private String cusMotherName;
        private String cusFatherName;
        private String cusSpouseName;
        private String introducerBankCustomerid;
        private String cusAblCusType;
        private String csbEnrollmentType;
        private String csbCustomerId;

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
    @ToString
    class CbsAccountInfoDto {

        private String bankAccountNumber;
        private String bankCustomerId;
        private String acCategory;
        private String acAccountTitle1;
        private String acMnemonic;
        private String acCurrency;
        private String acPostingRestrict;
        private String acInactivMarker;
        private String acRecordStatus;
        private String branchOid;
        private String acLockedAmount;
        private BigDecimal acWorkingBalance;

    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    class CustomerInfoResponseBody {

        private String csbCustomerId;
        private String cusSms1;
        private String cusRecordStatus;
        private String cusMnemonic;
        private String cusName1;
        private String cusFatherName;
        private String cusMotherName;
        private String cusSpouseName;
        private List<CdLegalDoc> cusLegalDocs;
        private String cusGender;
        private String address;
        private String cusDateOfBirth;
        private String cusAblCusType;
        private String cusEmail1;
        private int cusAddressLineNo;
        private String cusAddress;
        private List<String> cusAddress1;
        private List<CbsAccountInfoDto> accounts;

    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class CdLegalDoc {

        private String legalId;
        private String legalDocName;
        private String legalIssDate;
        private String legalExpDate;
        private String legalHolderName;
        private String legalIssAuth;
        private Integer slNo;


    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class CustomerInfoResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private CustomerInfoResponseBody body;

        public CustomerInfoResponse() {
            header = new ResponseHeader();
            body = new CustomerInfoResponseBody();
        }
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class FtRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private FtRequestBody body;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    class FtRequestBody {
        @NotNull
        private String traceId;
        @NotNull
        private String action;
        @NotNull
        private String fromBranchCode;
        @NotNull
        private String toBranchCode;
        @NotNull
        private String isInterBranch;
        @NotNull
        private String transactionType;
        @NotNull
        private String chargeType;
        @NotNull
        private String chargeCode;
        @NotNull
        private String creditTheirRef;
        @NotNull
        private String debitTheirRef;
        @NotNull
        private BigDecimal transAmount;
        @NotNull
        private Double chargeAmount;
        @NotNull
        private Double vatAmount;
        @NotNull
        private String fromBankAccountNo;
        @NotNull
        private String toBankAccountNo;
        @NotNull
        private String paymentDetails;
        @NotNull
        private Double profitCentreDept;
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    class EsRequestBody {
        @NotNull
        private String bankAccountNo;
        @NotNull
        private String fromDate;
        @NotNull
        private String toDate;

        @Override
        public String toString() {
            return "EsRequestBody {" +
                "bankAccountNo='" + bankAccountNo + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                '}';
        }
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class EsRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private EsRequestBody body;

        @Override
        public String toString() {
            return "EsRequest{" +
                "header=" + header +
                ", meta=" + meta +
                ", body=" + body +
                '}';
        }
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class EsResponseBody {
        private String bankAccountNo;
        private String accountTitle;
        private String bankCustomerName;
        private String address;
        private String bankCustomerId;
        private String branchOid;
        private String productOid;
        private String accountOpeningDate;
        private String accountStatus;
        private BigDecimal totalCredit;
        private BigDecimal totalDebit;
        private BigDecimal startBalance;
        private BigDecimal endBalance;
        private BigDecimal workingBalance;
        private int count;


    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @ToString
    class EsRequestResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private EsResponseBody body;

        public EsRequestResponse() {
            header = new ResponseHeader();
            body = new EsResponseBody();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    class MsRequestBody {
        private String bankAccountNo;


        @Override
        public String toString() {
            return "MsRequestBody{" +
                " bankAccountNo='" + bankAccountNo + '\'' +
                '}';
        }
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class MsRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private MsRequestBody body;

        @Override
        public String toString() {
            return "MsRequest{" +
                "header=" + header +
                ", meta=" + meta +
                ", body=" + body +
                '}';
        }
    }


    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    class TransactionProfileValidateResponseBody {
        private boolean data;
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class TransactionProfileValidateResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private TransactionProfileValidateResponseBody body;

        public TransactionProfileValidateResponse() {
            header = new ResponseHeader();
            body = new TransactionProfileValidateResponseBody();
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    class TransactionProfileUpdateResponseBody {
        private boolean data;
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class TransactionProfileUpdateResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta = new HashMap<String, Object>();
        private TransactionProfileUpdateResponseBody body;

        public TransactionProfileUpdateResponse() {
            header = new ResponseHeader();
            body = new TransactionProfileUpdateResponseBody();
        }
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class ChargeModelResponse {
        private ResponseHeader header;
        private HashMap<String, Object> meta;
        private ChargeModelResponseBody body;

        public ChargeModelResponse() {
            meta = new HashMap<String, Object>();
            header = new ResponseHeader();
            body = new ChargeModelResponseBody();
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class ChargeModelResponseBody {
        private GetByKeysItem data;

        public ChargeModelResponseBody() {
            this.data = new GetByKeysItem();
        }
    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    class GetByKeysItem {

        private String chargemodeldefOid;
        private String chargeModelItemOid;
        private String chargeModelItemVersion;
        private BigDecimal transactionAmount;
        private BigDecimal vatAmount;
        private BigDecimal chargeAmount;
        private String debitorType;
        private String creditorAccount;
        private String chargeType;
    }


    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SaveFpRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private SaveFpRequestBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SavePersonRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private SavePersonRequestBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SaveCustomerRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private SaveCustomerRequestBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class MobileNoVerificationRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private MobileNoVerificationBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SaveImageRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private SaveImageRequestBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SaveAccountRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private SaveAccountRequestBody body;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class ServicePointDetails {

        //        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String bankCustomerId;
        private String bankAccountNo;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;

        public ServicePointDetails() {
//            this.branchOid = "BD0011207";
            this.agentOid = "AG100175";
            this.servicePointOid = "670602";
            this.loginId = "10804361";
            this.roleId = "AGENT.TELLER";
            this.agentStaffOid = "10000176";
            this.serviceTerminalOid = "ST1000164";
            this.clientDeviceIdentifierId = "359475077199314";
            this.bankCustomerId = "21234347";
            this.bankAccountNo = "0200003237318";
        }
    }


    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class MobileNoVerificationBody {
        private String mobileNo;
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class SavePersonRequestBody {
        private String title;
        private String customerName;
        private String gender;
        private String dateOfBirth;
        private String mobileNo;
        private String isMobileNoVerified;
        private String photoIdType;
        private String photoId;
        private String photoIdIssuanceDate;
        private String customerType;
        private String isIntroducerAgent;
        private String isIntroducerCustomer;
        private String introducerCustomerId;
        private String introducerBankAccountNo;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class SaveAccountRequestBody {

        private String photoIdType;
        private String photoId;
        private String accountTitle;
        private String productOid;
        private String productType;
        private String initialDeposit;
        private String productName;
        private String userName;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
        private String fingerprintIdentifierId;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    class SaveImageRequestBody {

        private String photoIdType;
        private String photoId;
        private String photoContent;
        private String photoIdPathFront;
        private String photoIdContentFront;
        private String photoIdPathBack;
        private String photoIdContentBack;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
        private String fingerprintIdentifierId;

        @Override
        public String toString() {
            return "SaveImageRequestBody{" +
                "photoIdType='" + photoIdType + '\'' +
                ", photoId='" + photoId + '\'' +
                ", photoIdPathFront='" + photoIdPathFront + '\'' +
                ", photoIdPathBack='" + photoIdPathBack + '\'' +
                ", branchOid='" + branchOid + '\'' +
                ", agentOid='" + agentOid + '\'' +
                ", servicePointOid='" + servicePointOid + '\'' +
                ", loginId='" + loginId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", agentStaffOid='" + agentStaffOid + '\'' +
                ", serviceTerminalOid='" + serviceTerminalOid + '\'' +
                ", clientDeviceIdentifierId='" + clientDeviceIdentifierId + '\'' +
                ", fingerprintIdentifierId='" + fingerprintIdentifierId + '\'' +
                '}';
        }
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class SaveCustomerRequestBody {
        private String photoIdType;
        private String photoId;
        private String customerType;
        private String isIntroducerAgent;
        private String isIntroducerCustomer;
        private String introducerCustomerId;
        private String introducerBankAccountNo;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
        private String customerQrCode;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class SaveFpRequestBody {

        private String photoIdType;
        private String photoId;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String agentStaffOid;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
        private String fingerprintIdentifierId;
        private SaveFpDataFingerPrint fingerprint;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class SaveFpDataFingerPrint {

        private String fpDeviceModelOid;
        private String fpDeviceOid;
        private String fpDeviceMnemonic;
        private String clientSideSdk;
        private String serverSideSdk;
        private String defaultFinger;
        private Map<String, Object> fpDetails;
        private Map<String, Object> ri;
        private Map<String, Object> rm;
        private Map<String, Object> rt;
        private Map<String, Object> rr;
        private Map<String, Object> rp;
        private Map<String, Object> li;
        private Map<String, Object> lm;
        private Map<String, Object> lt;
        private Map<String, Object> lr;
        private Map<String, Object> lp;
        private Map<String, Object> riMeta;
        private Map<String, Object> rmMeta;
        private Map<String, Object> rtMeta;
        private Map<String, Object> rrMeta;
        private Map<String, Object> rpMeta;
        private Map<String, Object> liMeta;
        private Map<String, Object> lmMeta;
        private Map<String, Object> ltMeta;
        private Map<String, Object> lrMeta;
        private Map<String, Object> lpMeta;

    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    class SaveCasaResponseBody {
        private String userId;
        private String photoIdNo;
        private String photoIdType;
        private String branchId;
        private String branchName;
        private String userMessage;
        private String title;
        private String customerName;
        private String fullName;
        private String firstName;
        private String middleName;
        private String lastName;
        private String familyName;
        private String givenName;
        private String mobileNo;
        private String introducerBankAccountNumber;
        private String productId;
        private String productName;
        private String csbCustomerId;
        private String csbAccountId;
        private String cbsReferenceNo;
        private String cbsCustomerId;
        private String customerOfsReference;
        private String customerOfsDateTime;
        private String bankAccountNo;
        private String accountOfsReference;
        private String accountOfsDateTime;
        private String accountCurrentVersionNo;
        private String accountInputter;
        private String accountAuthoriser;
        private BigDecimal initialDeposit;
        private EkycPersonalCasaAccountDetailCustomerReceipt customerReceipt;

    }

    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class EkycPersonalCasaAccountDetailCustomerReceipt {

        private String userId;
        private String userName;
        private String agentId;
        private Long transactionDate;
        private String transactionId;
        private String traceId;
        private String accountName;
        private String accountNumber;
        private String productName;
        private Double amount;
        private String amountInWords = "";
        private Double chargeAndVat;
        private String printDateTime = "";
        private String cbsCustomerId;
        private String mobileNo;

    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class NidVerificationRequest {
        private AbsClientService.RequestHeader header;
        private HashMap<String, Object> meta;
        private AbsClientService.NidVerificationBody body;
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    class NidVerificationBody {
        private String photoIdType;
        private String photoId;
        private String photoIdIssuanceDate;
        private String branchOid;
        private String agentOid;
        private String servicePointOid;
        private String loginId;
        private String roleId;
        private String serviceTerminalOid;
        private String clientDeviceIdentifierId;
        private String fingerprintIdentifierId;
        private String agentStaffOid;
    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class SendSmsRequest {
        //        private RequestHeader header;
//        private SendSmsRequestBody body;
        private String sms;

        private String mobile_no;

        private String sms_type;

        private String request_id;

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

        private String sms;

        private String mobile_no;

        private String sms_type;

        private String request_id;

        private Timestamp request_time;

        /*private  String messageProviderOid;

        private  String templateCode;

        private  String smsFilterType;

        private  String branchId;*/

        @Override
        public String toString() {
            return new GsonBuilder().setPrettyPrinting().create().toJson(this);
        }

    }


    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class IccRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private IccRequestBody body;
    }


    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class IccRequestBody {

        private String branchOid;
        private String mnemonic;
        private String shortName;
        private String name1;
        private String sector;
        private String industry;
        private String nationality;
        private String residence;
        private String language;
        private String legalId;
        private String legalDocName;
        private String legalIssDate;
        private String legalExpDate;
        private String title;
        private String givenNames;
        private String familyName;
        private String gender;

        private String dateOfBirth;
        private String sms1;
        private String introducerBankCustomerId;
        private String ablCusType;
        private String status;
        private String customerId;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class IccResponseBody {

        private String authoriser;
        private String cbsCustomerId;
        private String currNo;
        private String inputter;
        private String ofsReference;
//        private DateTime ofsDateTime;

    }

    @Builder
    @Getter
    @Setter
    @JsonSerialize
    @JsonDeserialize
    @AllArgsConstructor
    class CacRequest {
        private RequestHeader header;
        private HashMap<String, Object> meta;
        private CacRequestBody body;
    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class CacRequestBody {

        private String branchOid;
        private String mnemonic;
        private String shortTitle;
        private String accountTitle;
        private String bankCustomerId;
        private String category;
        private String currency;
        private String passbook;
        private String legacyGlCode;
        private String accountId;
        private String cbIndCode;
        private String servicePointOid;

    }

    @Getter
    @Setter
    @Builder
    @JsonSerialize
    @JsonDeserialize
    @NoArgsConstructor
    @AllArgsConstructor
    class CacResponseBody {

        private String bankAccountNo;
        private String ofsReference;
        //        private DateTime ofsDateTime;
        private String currNo;
        private String inputter;
        private String authoriser;

    }
}
