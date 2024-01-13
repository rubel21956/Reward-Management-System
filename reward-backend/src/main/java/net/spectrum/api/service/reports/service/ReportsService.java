package net.spectrum.api.service.reports.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.spectrum.api.application.entity.ApplicationEntity;
import net.spectrum.api.application.repository.ApplicationRepository;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import net.spectrum.api.applicationattachment.repository.ApplicationAttachmentRepository;
import net.spectrum.api.auth.login.repository.LoginRepository;
import net.spectrum.api.auth.role.repository.RoleRepository;
import net.spectrum.api.office.repository.OfficeRepository;
import net.spectrum.api.rewardamount.domain.RewardAmountDomain;
import net.spectrum.api.rewardamount.entity.RewardAmountEntity;
import net.spectrum.api.rewardamount.repository.RewardAmountRepository;
import net.spectrum.api.service.reports.dto.RewardAmountDto;
import net.spectrum.api.service.reports.dto.rewardAmountDtoDomain;
import net.spectrum.api.service.reports.repository.ApplicationReportRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static net.spectrum.api.util.converter.EnglishToBangla.getDigitBanglaFromEnglish;

//@Transactional
@Service
@Slf4j
public class ReportsService {

    private static final String PDF_REPORT_PATH = "pdf-templates/";
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private OfficeRepository officeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private ApplicationReportRepository applicationReportRepository;
    @Autowired
    private RewardAmountRepository rewardAmountRepository;
    @Autowired
    private ApplicationAttachmentRepository applicationAttachmentRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    public void generatePdfFileForApplicationStepOne(String oid, OutputStream out)
        throws ExceptionHandlerUtil {
        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(oid);
        List<RewardAmountDomain> domainList = convertRewardAmountEntityToDomainApplicationStepOne(rewardAmountEntities);

        try {
            Resource resource = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "nbr_step_one.jrxml");
            log.info("Applicant list pdf file loaded");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, getApplicationStepOne(applicationEntity, jasperReport),
                new JRBeanCollectionDataSource(domainList));
            ;
            JasperExportManager.exportReportToPdfStream(print, out);
            log.info("Successfully user list pdf file generated");
        } catch (FileNotFoundException e) {
            log.error("Applicant List jrxml file not found", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (IOException | JRException e) {
            log.error("Exception occurred during applicant list report", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    Map<String, Object> getApplicationStepOne(ApplicationEntity applicationEntity, JasperReport jasperSubReport) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));

        parameters.put("customsRewardFileNumber", applicationEntity.getCustomsRewardFileNumber());
        parameters.put("nbrRewardFileNumber", applicationEntity.getNbrRewardFileNumber());
        parameters.put("applyingOfficeName",applicationEntity.getOfficeName());
        parameters.put("anomalyReason", applicationEntity.getAnomalyReason());
        parameters.put("billOfEntryNo", getDigitBanglaFromEnglish(applicationEntity.getBillOfEntryNo().toString()));
        parameters.put("rewardableAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getRewardableAmount()).replace("₹","")));
        parameters.put("accusedCompany", applicationEntity.getAccusedCompanyName()+", "+applicationEntity.getAccusedCompanyAddress());
        //parameters.put("totalRewardedAmount", applicationEntity.getRewardableAmount());
        parameters.put("subreportParameter", jasperSubReport);

        return parameters;
    }

    public void generatePdfFileForApplicationStepTwo(String oid, OutputStream out)
        throws ExceptionHandlerUtil {
        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(oid);
        List<RewardAmountDomain> domainList = convertRewardAmountEntityToDomainApplicationStepOne(rewardAmountEntities);
        log.info("Request to build Response For Statement");
        try {
//            Resource imageResources = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "images/nbr_logo.png");
            Resource resource = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "nbr_step_two.jrxml");
            log.info("Applicant list pdf file loaded");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, getApplicationStepTwo(applicationEntity),
                new JRBeanCollectionDataSource(domainList));
            ;
            JasperExportManager.exportReportToPdfStream(print, out);
            log.info("Successfully user list pdf file generated");
        } catch (FileNotFoundException e) {
            log.error("Applicant List jrxml file not found", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (IOException | JRException e) {
            log.error("Exception occurred during applicant list report", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    Map<String, Object> getApplicationStepTwo(ApplicationEntity applicationEntity) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        parameters.put("customsRewardFileNumber", applicationEntity.getCustomsRewardFileNumber());
        parameters.put("nbrRewardFileNumber", applicationEntity.getNbrRewardFileNumber());
        parameters.put("applyingOfficeName", applicationEntity.getOfficeName());
        parameters.put("anomalyReason", applicationEntity.getAnomalyReason());
        parameters.put("billOfEntryNo", getDigitBanglaFromEnglish(applicationEntity.getBillOfEntryNo()));
        parameters.put("rewardableAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getRewardableAmount()).replace("₹","")));
        parameters.put("suggestionStatus", applicationEntity.getSuggestionStatus());
        parameters.put("accusedCompany", applicationEntity.getAccusedCompanyName()+", "+applicationEntity.getAccusedCompanyAddress());
        //parameters.put("totalRewardedAmount", applicationEntity.getRewardableAmount());

        return parameters;
    }

    public void generatePdfFileForApplicationStepThree(String oid, OutputStream out)
        throws ExceptionHandlerUtil {
        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        List<RewardAmountDto> rewardAmountEntities = generatePdfFileForApplicationAdminStepThree(oid);
        List<rewardAmountDtoDomain> domainList = convertRewardAmountDtoDomainApplicationStepthree(rewardAmountEntities);

        log.info("Request to build Response For Statement");
        try {
//            Resource imageResources = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "images/nbr_logo.png");
            Resource resource = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "nbr_step_three.jrxml");
            log.info("Applicant list pdf file loaded");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, getApplicationStepThree(applicationEntity),
                new JRBeanCollectionDataSource(domainList));
            ;
            JasperExportManager.exportReportToPdfStream(print, out);
            log.info("Successfully user list pdf file generated");
        } catch (FileNotFoundException e) {
            log.error("Applicant List jrxml file not found", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (IOException | JRException e) {
            log.error("Exception occurred during applicant list report", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    Map<String, Object> getApplicationStepThree(ApplicationEntity applicationEntity) {

        Map<String, Object> parameters = new HashMap<String, Object>();
        Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));

        parameters.put("customsRewardFileNumber", applicationEntity.getCustomsRewardFileNumber());
        parameters.put("nbrRewardFileNumber", applicationEntity.getNbrRewardFileNumber());
        parameters.put("applyingOfficeName", applicationEntity.getOfficeName());
        parameters.put("anomalyReason", applicationEntity.getAnomalyReason());
        parameters.put("billOfEntryNo", getDigitBanglaFromEnglish(applicationEntity.getBillOfEntryNo()));
        parameters.put("rewardableAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getRewardableAmount()).replace("₹","")));
        parameters.put("suggestionStatus", applicationEntity.getSuggestionStatus());
        parameters.put("totalRewardedAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getTotalRewardedAmount()).replace("₹","")));
        parameters.put("accusedCompany", applicationEntity.getAccusedCompanyName()+", "+applicationEntity.getAccusedCompanyAddress());
        //parameters.put("totalRewardedAmount", applicationEntity.getRewardableAmount());

        return parameters;
    }

    public List<RewardAmountDto> generatePdfFileForApplicationAdminStepThree(String oid) {

        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
//        RewardAmountDto rewardAmountDto = new RewardAmountDto();
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(oid);
        List<RewardAmountDto> rewardAmountDtos = new ArrayList<>();

        for (int i = 0; i < rewardAmountEntities.size(); i++) {
            RewardAmountDto rewardAmountDto = new RewardAmountDto();
            rewardAmountDto.setOid(rewardAmountEntities.get(i).getOid());
            rewardAmountDto.setEmployeeName(rewardAmountEntities.get(i).getEmployeeName());
            rewardAmountDto.setDesignation(rewardAmountEntities.get(i).getDesignation());
            rewardAmountDto.setAdvancePaidAmount(rewardAmountEntities.get(i).getAdvancePaidAmount());
            rewardAmountDto.setAppliedRewardAmount(rewardAmountEntities.get(i).getAppliedRewardAmount());
            rewardAmountDto.setIsRewarded(rewardAmountEntities.get(i).getIsRewarded());
            if (rewardAmountEntities.get(i).getIsRewarded().equals("false")
                || rewardAmountEntities.get(i).getIsRewarded().equals("False")) {
                rewardAmountDto.setRewardedAmount(null);
                rewardAmountDto.setRejectedName(rewardAmountEntities.get(i).getEmployeeName());
            } else {
                rewardAmountDto.setRewardedAmount(rewardAmountEntities.get(i).getRewardedAmount());
                rewardAmountDto.setRejectedName(rewardAmountEntities.get(i).getEmployeeName());
            }
            rewardAmountDtos.add(rewardAmountDto);
        }
        return rewardAmountDtos;
    }

    public List<RewardAmountDto> generatePdfFileForApplicationAdminStepFinal(String oid) {

        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
//        RewardAmountDto rewardAmountDto = new RewardAmountDto();
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(oid);
        List<RewardAmountDto> rewardAmountDtos = new ArrayList<>();

        for (int i = 0; i < rewardAmountEntities.size(); i++) {
            RewardAmountDto rewardAmountDto = new RewardAmountDto();

            if (rewardAmountEntities.get(i).getIsRewarded().equals("false")
                    || rewardAmountEntities.get(i).getIsRewarded().equals("False")) {
//                rewardAmountDto.setRewardedAmount(null);
                 rewardAmountDto.setRejectedName(rewardAmountEntities.get(i).getEmployeeName());
            } else {
                rewardAmountDto.setOid(rewardAmountEntities.get(i).getOid());
                rewardAmountDto.setEmployeeName(rewardAmountEntities.get(i).getEmployeeName());
                rewardAmountDto.setDesignation(rewardAmountEntities.get(i).getDesignation());
                rewardAmountDto.setAdvancePaidAmount(rewardAmountEntities.get(i).getAdvancePaidAmount());
                rewardAmountDto.setAppliedRewardAmount(rewardAmountEntities.get(i).getAppliedRewardAmount());
                rewardAmountDto.setRewardedAmount(rewardAmountEntities.get(i).getRewardedAmount());
                rewardAmountDto.setIsRewarded(rewardAmountEntities.get(i).getIsRewarded());
                rewardAmountDto.setSl(rewardAmountEntities.get(i).getSl());
                rewardAmountDtos.add(rewardAmountDto);
            }

        }
        List<RewardAmountDto> temp = new ArrayList<>();
        //For adding fast
        for(int l=0; l<rewardAmountDtos.size(); l++){
            if(rewardAmountDtos.get(l).getEmployeeName().equalsIgnoreCase("তথ্য সরবরাহকারী") && (!rewardAmountEntities.get(l).getIsRewarded().equals("false")
                    || !rewardAmountEntities.get(l).getIsRewarded().equals("False"))){
                temp.add(rewardAmountDtos.get(l));
            }
        }
        //For adding middile serial
        for(int k=0; k<rewardAmountDtos.size(); k++){
            for(int j=0; j<rewardAmountDtos.size(); j++){
                if(rewardAmountDtos.get(j).getSl().equalsIgnoreCase(String.valueOf(k))){
                    BigDecimal tempAmount = rewardAmountDtos.get(j).getRewardedAmount();
                    Locale bangladeshiLocale = new Locale("bn", "BD");
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols(bangladeshiLocale);
                    symbols.setCurrencySymbol("৳");
                    DecimalFormat currencyFormat = new DecimalFormat("¤ #,##,###.00", symbols);
                    currencyFormat.setCurrency(Currency.getInstance("BDT"));
                    String format = NumberFormat.getCurrencyInstance().format(tempAmount);
                    temp.add(rewardAmountDtos.get(j));
                }
            }
//            System.out.println(temp.get(k).getEmployeeName()+temp.get(k).getRewardedAmount());
        }

        //For adding last
        for(int l=0; l<rewardAmountDtos.size(); l++){
            if(rewardAmountDtos.get(l).getEmployeeName().equalsIgnoreCase("কাস্টমস হাউস এর কল্যাণ তহবিল")){
                String addPercentWord = rewardAmountDtos.get(l).getEmployeeName()+" ১২%";
                rewardAmountDtos.get(l).setEmployeeName(addPercentWord);
                temp.add(rewardAmountDtos.get(l));
            }
        }

        //For adding Second last
        for(int l=0; l<rewardAmountDtos.size(); l++){
            if(rewardAmountDtos.get(l).getEmployeeName().equalsIgnoreCase("জারাবো এর কল্যাণ তহবিল")){
                String addPercentWord = rewardAmountDtos.get(l).getEmployeeName()+" ৩%";
                rewardAmountDtos.get(l).setEmployeeName(addPercentWord);
                temp.add(rewardAmountDtos.get(l));
            }
        }

        return temp;
    }

    public void generatePdfFileForCustomsApplicationStepFour(String oid, OutputStream out)
            throws ExceptionHandlerUtil {
        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        List<ApplicationAttachmentEntity> attachmentType = applicationAttachmentRepository.findByOid(oid);
        if(attachmentType.size() == 1 ){
            ApplicationAttachmentEntity ap1 = new ApplicationAttachmentEntity();
            ApplicationAttachmentEntity ap2 = new ApplicationAttachmentEntity();
            ApplicationAttachmentEntity ap3 = new ApplicationAttachmentEntity();
            ap1.setAttachmentType("কোন সংযুক্তি যুক্ত করা হয়নি");
            ap2.setAttachmentType("   ");
            ap3.setAttachmentType("   ");
//            attachmentType.add(ap1);
            attachmentType.add(ap2);
            attachmentType.add(ap3);
        }
        if(attachmentType.size() == 2 ){
            ApplicationAttachmentEntity ap1 = new ApplicationAttachmentEntity();
            ApplicationAttachmentEntity ap2 = new ApplicationAttachmentEntity();
            ApplicationAttachmentEntity ap3 = new ApplicationAttachmentEntity();
            ap1.setAttachmentType("কোন সংযুক্তি যুক্ত করা হয়নি");
            ap2.setAttachmentType("   ");
            ap3.setAttachmentType("   ");
//            attachmentType.add(ap1);
            attachmentType.add(ap2);
//            attachmentType.add(ap3);
        }
        log.info("Request to build Response For Statement");
        try {
//            Resource imageResources = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "images/nbr_logo.png");
            Resource resource = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "customs_step_four.jrxml");
            log.info("Applicant list pdf file loaded");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, getCustomsApplicationStepFour(applicationEntity, attachmentType),
                    new JRBeanCollectionDataSource(attachmentType));
            ;
            JasperExportManager.exportReportToPdfStream(print, out);
            log.info("Successfully user list pdf file generated");
        } catch (FileNotFoundException e) {
            log.error("Applicant List jrxml file not found", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (IOException | JRException e) {
            log.error("Exception occurred during applicant list report", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }


}

    Map<String, Object> getCustomsApplicationStepFour(ApplicationEntity applicationEntity, List<ApplicationAttachmentEntity> attachmentType) {

        Map<String, Object> parameters = new HashMap<String, Object>();

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String applicationDate = formatter.format(applicationEntity.getApplicationDate());
        String billOfEntryDate = formatter.format(applicationEntity.getBillOfEntryDate());
        String anomalyCaptureDate = formatter.format(applicationEntity.getAnomalyCaptureDate());
        Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));

        parameters.put("officeName", applicationEntity.getOfficeName());
        parameters.put("officeAddress", applicationEntity.getOfficeAddress());
        parameters.put("referenceNo", getDigitBanglaFromEnglish(applicationEntity.getReferenceNo()));
        parameters.put("customsRewardFileNumber", applicationEntity.getCustomsRewardFileNumber());
        parameters.put("applicationDate", getDigitBanglaFromEnglish(applicationDate));
        parameters.put("billOfEntryDate", getDigitBanglaFromEnglish(billOfEntryDate));
        parameters.put("billOfEntryNo", getDigitBanglaFromEnglish(applicationEntity.getBillOfEntryNo()));
        parameters.put("anomalyCaptureDate", getDigitBanglaFromEnglish(anomalyCaptureDate));
        parameters.put("extraReceivedAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getExtraReceivedAmount()).replace("₹","")));
        parameters.put("rewardableAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getRewardableAmount()).replace("₹","")));
        parameters.put("applicationNarration", getDigitBanglaFromEnglish(applicationEntity.getApplicationNarration()));
        parameters.put("officeHeadsName", applicationEntity.getOfficeHeadsName());
        parameters.put("attachmentTypeSize", attachmentType.size());

        return parameters;
    }









    public void generatePdfFileForApplicationStepThreeAccepted(String oid, OutputStream out)
            throws ExceptionHandlerUtil {
        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        List<RewardAmountDto> rewardAmountEntities = generatePdfFileForApplicationAdminStepFinal(oid);
        log.info("Request to build Response For Statement");
        try {
//            Resource imageResources = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "images/nbr_logo.png");
            Resource resource = resourceLoader.getResource("classpath:" + PDF_REPORT_PATH + "nbr_step_five_rewarded.jrxml");
            log.info("Applicant list pdf file loaded");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, getApplicationStepThreeAccepted(applicationEntity, rewardAmountEntities),
                    new JRBeanCollectionDataSource(rewardAmountEntities));
            ;
            JasperExportManager.exportReportToPdfStream(print, out);
            log.info("Successfully user list pdf file generated");
        } catch (FileNotFoundException e) {
            log.error("Applicant List jrxml file not found", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (IOException | JRException e) {
            log.error("Exception occurred during applicant list report", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    Map<String, Object> getApplicationStepThreeAccepted(ApplicationEntity applicationEntity, List<RewardAmountDto> rewardAmountEntities) {

        // This Saction is modified by Arif

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        String applicationDate = new SimpleDateFormat("dd.MM.yyyy").format(applicationEntity.getApplicationDate());
        String anomalyCaptureDate = formatter.format(applicationEntity.getAnomalyCaptureDate());
        String currentDate = formatter.format(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        List<rewardAmountDtoDomain> convertedRewardAmountDto = convertRewardAmountDtoDomainApplicationStepthree(rewardAmountEntities);
        String customAwardSanctionDate = "ভুল ফরমেট ";
        try{
            String inputDate = applicationEntity.getCustomsAwardSanctioningCommittee();
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_INSTANT;
            Instant instant = Instant.from(inputFormatter.parse(inputDate));
            LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            Locale bangladeshLocale = new Locale("bn", "BD"); // Creating Bangladesh locale

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", bangladeshLocale);
            customAwardSanctionDate = dateTime.format(outputFormatter);


        }catch (Exception e){
            System.out.println("CustomAwardSanctionDate format is error");
        }

        Map<String, Object> parameters = new HashMap<String, Object>();
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(convertedRewardAmountDto);
        Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));

        parameters.put("rewardAmountEntities", jrBeanCollectionDataSource);
        parameters.put("fiscalYear", getDigitBanglaFromEnglish(getCurrentFiscalYear()));
        parameters.put("officeName", applicationEntity.getOfficeName());
        parameters.put("officeAddress", applicationEntity.getOfficeAddress());
        parameters.put("nbrRewardFileNumber", applicationEntity.getNbrRewardFileNumber());
        parameters.put("applicationDate", getDigitBanglaFromEnglish(applicationDate));
        parameters.put("referenceNo", getDigitBanglaFromEnglish(applicationEntity.getReferenceNo()));
        parameters.put("customsAwardSanctioningCommittee", getDigitBanglaFromEnglish(customAwardSanctionDate));
        parameters.put("billOfEntryNo", getDigitBanglaFromEnglish(applicationEntity.getBillOfEntryNo()));
        parameters.put("anomalyCaptureDate", getDigitBanglaFromEnglish(anomalyCaptureDate));
        parameters.put("appealOrderNo", getDigitBanglaFromEnglish(applicationEntity.getAppealOrderNo()));
        parameters.put("totalRewardedAmount", getDigitBanglaFromEnglish(format.format(applicationEntity.getTotalRewardedAmount()).replace("₹","")));
//        parameters.put("currentDate", Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        parameters.put("currentDate", getDigitBanglaFromEnglish(currentDate));
        return parameters;
    }


    private List<RewardAmountDomain> convertRewardAmountEntityToDomainApplicationStepOne(List<RewardAmountEntity> entities){
        if(entities!=null && entities.size()>0){
            List<RewardAmountDomain> domainList= new ArrayList<>();
            entities.forEach(entity->{

     String id;
     String oid;
     String employeeName;
     String designation;
     String advancePaidAmount;
     String appliedRewardAmount;
     String rewardedAmount;
     String isRewarded;
     String createdBy;
     Timestamp createdOn;
     String updatedBy;
     Timestamp updatedOn;
     String sl;


     if(entity.getEmployeeName() != null){
         employeeName = entity.getEmployeeName();
     }else{
         employeeName = "";
     }
                if(entity.getDesignation() != null){
                    designation = entity.getDesignation();
                }else{
                    designation = "";
                }
                if(entity.getRewardedAmount() != null){
                    rewardedAmount = entity.getRewardedAmount().toString();
                }else{
                    rewardedAmount = "";
                }
                if(entity.getAppliedRewardAmount() != null){
                    appliedRewardAmount = entity.getAppliedRewardAmount().toString();
                }else{
                    appliedRewardAmount = "0";
                }
                if(entity.getAdvancePaidAmount() != null){
                    advancePaidAmount = entity.getAdvancePaidAmount().toString();
                }else{
                    advancePaidAmount = "0";
                }
                 Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));
                        domainList.add(RewardAmountDomain.builder()
                                .id(entity.getId())
                                .oid(entity.getOid())
                                .employeeName(employeeName)
                                .designation(designation)
                                .advancePaidAmount(getDigitBanglaFromEnglish(advancePaidAmount))
                                .appliedRewardAmount(getDigitBanglaFromEnglish(format.format(entity.getAppliedRewardAmount() == null ? 0 : entity.getAppliedRewardAmount()).replace("₹","")))
                                .rewardedAmount(getDigitBanglaFromEnglish(format.format(entity.getRewardedAmount() == null ? 0 : entity.getRewardedAmount()).replace("₹","")))
                                .isRewarded(entity.getIsRewarded())
                                .createdBy(entity.getCreatedBy())
                                .createdOn(entity.getCreatedOn())
                                .updatedBy(entity.getUpdatedBy())
                                .updatedOn(entity.getUpdatedOn())
                                .sl(entity.getSl())
                                .build());

            });

            List<RewardAmountDomain> temp = new ArrayList<>();


            domainList.stream().forEach(entity->{
                //For adding fast
                    if(entity.getEmployeeName().equalsIgnoreCase("তথ্য সরবরাহকারী")){
                        System.out.println(entity.getAppliedRewardAmount());
                        temp.add(entity);
                    }
            });

            //For adding middile serial
            domainList.stream().forEach(e->{
              domainList.stream().forEach(f->{
                  if(e.getSl().equalsIgnoreCase(f.getSl()) && !e.getSl().isEmpty() && e.getSl() != null){
                      temp.add(f);
                  }
              });
            });


            domainList.stream().forEach(entity->{
                //For adding fast
                if(entity.getEmployeeName().equalsIgnoreCase("কাস্টমস হাউস এর কল্যাণ তহবিল")){
                    temp.add(entity);
                }
            });

            domainList.stream().forEach(entity->{
                //For adding fast
                if(entity.getEmployeeName().equalsIgnoreCase("জারাবো এর কল্যাণ তহবিল")){
                    temp.add(entity);
                }
            });

    return temp;
        }

        return new ArrayList<>();
    }

    private List<rewardAmountDtoDomain> convertRewardAmountDtoDomainApplicationStepthree(List<RewardAmountDto> entities){
        if(entities!=null && entities.size()>0){
            List<rewardAmountDtoDomain> domainList= new ArrayList<>();
            entities.forEach(entity->{

                String oid;
                String employeeName;
                String rejectedName;
                String designation;
                String advancePaidAmount;
                String appliedRewardAmount;
                BigDecimal rewardedAmount;
                String isRewarded;

                System.out.println(entity.getRejectedName());

                if(entity.getEmployeeName() != null){
                    employeeName = entity.getEmployeeName();
                }else{
                    employeeName = "";
                }
                if(entity.getRejectedName() != null){
                    rejectedName = entity.getRejectedName();
                }else{
                    rejectedName = "";
                }
                if(entity.getDesignation() != null){
                    if(entity.getDesignation().matches("তথ্য সরবরাহকারী") || entity.getDesignation().matches("জারাবো এর কল্যাণ তহবিল") || entity.getDesignation().matches("কাস্টমস হাউস এর কল্যাণ তহবিল")){
                        designation = "";
                    }else{
                        designation = entity.getDesignation();
                    }
                }else{
                    designation = "";
                }
//                if(entity.getRewardedAmount() != null){
//                    rewardedAmount = entity.getRewardedAmount().toString();
//                }else{
//                    rewardedAmount = "";
//                }
                if(entity.getAppliedRewardAmount() != null){
                    appliedRewardAmount = entity.getAppliedRewardAmount().toString();
                }else{
                    appliedRewardAmount = "0";
                }
                if(entity.getAdvancePaidAmount() != null){
                    advancePaidAmount = entity.getAdvancePaidAmount().toString();
                }else{
                    advancePaidAmount = "0";
                }

                Format format = com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"));

                domainList.add(rewardAmountDtoDomain.builder()
                        .oid(entity.getOid())
                        .employeeName(employeeName)
                        .rejectedName(rejectedName)
                        .designation(designation)
                        .advancePaidAmount(getDigitBanglaFromEnglish(advancePaidAmount))
                        .appliedRewardAmount(getDigitBanglaFromEnglish(format.format((entity.getAppliedRewardAmount() == null ? 0 : entity.getAppliedRewardAmount())).replace("₹","")))
                        .rewardedAmount(getDigitBanglaFromEnglish(format.format((entity.getRewardedAmount() == null ? 0 : entity.getRewardedAmount())).replace("₹","")))
                        .isRewarded(entity.getIsRewarded())
                        .build());
            });

//            List<rewardAmountDtoDomain> temp = new ArrayList<>();
//            domainList.stream().forEach(entity->{
//                //For adding fast
//                if(entity.getEmployeeName().equalsIgnoreCase("তথ্য সরবরাহকারী")){
//                    System.out.println(entity.getAppliedRewardAmount());
//                    temp.add(entity);
//                }
//            });
//
//            //For adding middile serial
//            domainList.stream().forEach(e->{
//                domainList.stream().forEach(f->{
//                    if(e.getSl().equalsIgnoreCase(f.getSl()) && !e.getSl().isEmpty() && e.getSl() != null){
//                        temp.add(f);
//                    }
//                });
//            });
//
//            domainList.stream().forEach(entity->{
//                //For adding fast
//                if(entity.getEmployeeName().equalsIgnoreCase("জারাবো এর কল্যাণ তহবিল")){
//                    temp.add(entity);
//                }
//            });
//
//            domainList.stream().forEach(entity->{
//                //For adding fast
//                if(entity.getEmployeeName().equalsIgnoreCase("কাস্টমস হাউস এর কল্যাণ তহবিল")){
//                    temp.add(entity);
//                }
//            });

            return domainList;
        }

        return new ArrayList<>();
    }


        public String getCurrentFiscalYear() {
            // Get the current date
            LocalDate currentDate = LocalDate.now();

            // Define the start month of your fiscal year (e.g., April)
            int fiscalYearStartMonth = 4;

            // Calculate the fiscal year based on the current date
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonthValue();
            int fiscalYearStartYear;

            if (currentMonth < fiscalYearStartMonth) {
                // If the current month is before the start month, subtract 1 from the year
                fiscalYearStartYear = currentYear - 1;
            } else {
                fiscalYearStartYear = currentYear;
            }

            int fiscalYearEndYear = fiscalYearStartYear + 1;

            // Format the fiscal year as a string
            String fiscalYearString = fiscalYearStartYear + "-" + fiscalYearEndYear;

            return fiscalYearString;
        }




}
