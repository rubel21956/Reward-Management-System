package net.spectrum.api.application.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.application.converter.ApplicationConverter;
import net.spectrum.api.application.dto.*;
import net.spectrum.api.application.entity.ApplicationEntity;
import net.spectrum.api.application.repository.ApplicationRepository;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import net.spectrum.api.applicationattachment.repository.ApplicationAttachmentRepository;
import net.spectrum.api.rewardamount.entity.RewardAmountEntity;
import net.spectrum.api.rewardamount.repository.RewardAmountRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import net.spectrum.api.util.converter.EnglishToBangla;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Transactional
@Service
@Slf4j
public class ApplicationService {

    public static final String RMS_FILE_DIR = "submitted-application";
    public static final String RMS_TWO_FILE_DIR = "appendix";
    private static final  String BASE_DIRECTORY = "/opt/rms";
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    RewardAmountRepository rewardAmountRepository;
    @Autowired
    ApplicationAttachmentRepository applicationAttachmentRepository;
    @Autowired
    ApplicationConverter applicationConverter;

    private final EntityManager entityManager;
    public ApplicationService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ApplicationEntity getApplicationEntity(String id) {
        return applicationRepository.findByOid(id);
    }

    public ApplicationEntity saveApplicationCustomsStepOne(ApplicationCustomsStepOneDto applicationCustomsStepOneDto, String createdBy) {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        BeanUtils.copyProperties(applicationCustomsStepOneDto, applicationEntity);
        applicationEntity.setCreatedBy(createdBy);
        applicationEntity.setUpdatedOn(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        applicationEntity = applicationRepository.save(applicationEntity);
        if (applicationCustomsStepOneDto.getRewardAmounts() != null) {
            List<RewardAmountEntity> rewardAmountList = applicationCustomsStepOneDto.getRewardAmounts();
            for (RewardAmountEntity rewardAmountEntity : rewardAmountList) {
                rewardAmountEntity.setOid(applicationEntity.getOid());
                rewardAmountEntity.setCreatedBy(createdBy);
                rewardAmountRepository.save(rewardAmountEntity);
            }
        }

        List<ApplicationAttachmentEntity> applicationAttachmentEntities = applicationCustomsStepOneDto.getApplicationAttachments();
        if (applicationAttachmentEntities != null) {
            for (ApplicationAttachmentEntity applicationAttachmentEntity : applicationAttachmentEntities) {
                if (applicationAttachmentEntity.getId() != null) {
                    applicationAttachmentEntity.setUpdatedBy(createdBy);
                    applicationAttachmentRepository.save(applicationAttachmentEntity);
                } else {
                    applicationAttachmentEntity.setOid(applicationEntity.getOid());
                    applicationAttachmentEntity.setCreatedBy(createdBy);
                    applicationAttachmentRepository.save(applicationAttachmentEntity);
                }
            }
        }
        return applicationEntity;
    }

    public ApplicationCustomsStepOneDto updateApplicationCustomsStepOne(String id, ApplicationCustomsStepOneDto applicationCustomsStepOneDto, String updatedBy) {

        // Find application by Oid. Then, update updatedBy and save.
        ApplicationEntity applicationEntityFromDb = applicationRepository.findByOid(id);
        BeanUtils.copyProperties(applicationCustomsStepOneDto, applicationEntityFromDb);
        applicationEntityFromDb.setUpdatedBy(updatedBy);
        applicationEntityFromDb.setUpdatedOn(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        applicationEntityFromDb = applicationRepository.save(applicationEntityFromDb);



        // Find if list of reward amounts exit in request. If exist then retrieve existing list. After that, delete previous officers so that, updated officers can get inserted.
        if (applicationCustomsStepOneDto.getRewardAmounts() != null) {
            List<RewardAmountEntity> getRewardAmountsFromDb = rewardAmountRepository.findByOid(id);
            for (int i = 0; i < getRewardAmountsFromDb.size(); i++) {
//                if (!getRewardAmountsFromDb.get(i).getDesignation().matches("জারাবো এর কল্যাণ তহবিল|কাস্টম হাউস এর কল্যাণ তহবিল")) {
//                    rewardAmountRepository.delete(getRewardAmountsFromDb.get(i));
//                }
                rewardAmountRepository.delete(getRewardAmountsFromDb.get(i));
            }
            // Get requested officers and insert them by setting their Oid and updatedBy.
            List<RewardAmountEntity> requestedRewardAmounts = applicationCustomsStepOneDto.getRewardAmounts();
            for (RewardAmountEntity rewardAmountEntity : requestedRewardAmounts) {
                rewardAmountEntity.setOid(applicationEntityFromDb.getOid());
                rewardAmountEntity.setUpdatedBy(updatedBy);
                rewardAmountRepository.save(rewardAmountEntity);
            }
        }
        // Retrieving all data needed for response.
        List<RewardAmountEntity>  getRewardAmountsFromDb = rewardAmountRepository.findByOid(id);
        ApplicationAttachmentEntity newAttachment = null;
        if(applicationCustomsStepOneDto.getApplicationAttachments() != null && !applicationCustomsStepOneDto.getApplicationAttachments().isEmpty()){
             newAttachment = applicationCustomsStepOneDto.getApplicationAttachments().get(0);
        }

        applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto(applicationEntityFromDb);
        applicationCustomsStepOneDto.setRewardAmounts(getRewardAmountsFromDb);

        ////////////// The below methods is added by Arif \\\\\\\\\\\\\\\\\

        if(newAttachment != null){
            ApplicationAttachmentEntity modifyAttachment = newAttachment;   
            modifyAttachment.setOid(id);
            applicationAttachmentRepository.save(modifyAttachment);
        }

        return applicationCustomsStepOneDto;
    }

    public ApplicationCustomsStepTwoDto updateApplicationCustomsStepTwo(String id, ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto, String createdBy) {
        ApplicationEntity response = applicationRepository.findByOid(id);

        BeanUtils.copyProperties(applicationCustomsStepTwoDto, response);

        applicationRepository.save(response);
        List<ApplicationAttachmentEntity> applicationAttachmentEntities = applicationCustomsStepTwoDto.getApplicationAttachments();
        if (applicationAttachmentEntities != null) {
            for (ApplicationAttachmentEntity applicationAttachmentEntity : applicationAttachmentEntities) {
                if (applicationAttachmentEntity.getId() != null) {
                    applicationAttachmentEntity.setUpdatedBy(createdBy);
                    applicationAttachmentRepository.save(applicationAttachmentEntity);
                } else {
                    applicationAttachmentEntity.setOid(applicationCustomsStepTwoDto.getOid());
                    applicationAttachmentEntity.setCreatedBy(createdBy);
                    applicationAttachmentRepository.save(applicationAttachmentEntity);
                }
            }
        }

        return applicationCustomsStepTwoDto;
    }

    public ApplicationEntity updateApplicationAdminStepOne(String id, ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto) {
        ApplicationEntity response = applicationRepository.findByOid(id);
        BeanUtils.copyProperties(applicationNbrAdminStepOneDto, response);
        applicationRepository.save(response);

        return response;
    }

    public ApplicationEntity updateApplicationAdminStepTwo(String id, ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto, String updatedBy) {
        ApplicationEntity response = applicationRepository.findByOid(id);

        BeanUtils.copyProperties(applicationNbrAdminStepTwoDto, response, "oid");

        response.setUpdatedBy(updatedBy);
        response.setUpdatedOn(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        response = applicationRepository.save(response);
        return response;
    }

    public ApplicationNbrAdminStepThreeDto updateApplicationAdminStepThree(String id, ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto, String updatedBy) {

        ApplicationEntity applicationEntity = applicationRepository.findByOid(id);
        if (applicationNbrAdminStepThreeDto.getTotalRewardedAmount() != null) {
            applicationEntity.setTotalRewardedAmount(applicationNbrAdminStepThreeDto.getTotalRewardedAmount());
            applicationEntity.setUpdatedBy(updatedBy);
            applicationRepository.save(applicationEntity);
        }

        if (applicationNbrAdminStepThreeDto.getRewardAmounts() != null) {

            List<RewardAmountEntity> rewardAmountList = applicationNbrAdminStepThreeDto.getRewardAmounts();

            for (RewardAmountEntity rewardAmountEntity : rewardAmountList) {

                rewardAmountEntity.setUpdatedBy(updatedBy);
                rewardAmountRepository.save(rewardAmountEntity);
            }
        }
        return applicationNbrAdminStepThreeDto;
    }

    public ApplicationEntity getApplicationCustomsStepOne(String id) {
        return applicationRepository.findByOid(id);
    }

    public ApplicationCustomsStepTwoDto getApplicationCustomsStepTwoDto(String id) {

        ApplicationEntity applicationEntity = applicationRepository.findByOid(id);

        List<ApplicationAttachmentEntity> applicationAttachmentEntities = applicationAttachmentRepository.findByOid(id);

        ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto;

        applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto(applicationEntity);

        applicationCustomsStepTwoDto.setApplicationAttachments(applicationAttachmentEntities);

        return applicationCustomsStepTwoDto;
    }

    public List<ApplicationEntity> getApplicationByStatus(String applicationStatus, String applicationDate, String userId) {
        if(applicationStatus.equalsIgnoreCase("All")){
            applicationStatus = "";
        }
        String sql = "select * from application ";
        if(!"".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where created_by = '"+userId+"' and application_status = '"+applicationStatus+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00';";
        }else if(!"".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where created_by = '"+userId+"' and application_status = '"+applicationStatus+"';";
        }else if("".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where created_by = '"+userId+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00';";
        }else if(!"".equalsIgnoreCase(applicationStatus)){
            sql += "where created_by = '"+userId+"' and application_status = '"+applicationStatus+"';";
        }else if(!"".equalsIgnoreCase(applicationDate)){
            sql += "where created_by = '"+userId+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00';";
        }
        if(sql.equalsIgnoreCase("select * from application ")){
            sql += "where created_by = '"+userId+"';";
        }
        Query query = entityManager.createNativeQuery(sql, ApplicationEntity.class);

        return query.getResultList();
    }


    public List<ApplicationEntity> getApplicationByStatusAndOffice(String officeName, String applicationStatus, String applicationDate) {
        if(applicationStatus.equalsIgnoreCase("All")){
            applicationStatus = "";
        }
        String sql = "select * from application ";
        if(!"".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and application_status = '"+applicationStatus+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if(!"".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and applying_office_oid = '"+officeName+"' and not (application_status = 'Draft' or application_status = 'Sattlement')";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if("".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if("".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if("".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus)){
            sql += "where applying_office_oid = '"+officeName+"' and not (application_status = 'Draft' or application_status = 'Sattlement');";
        }

        if(sql.equalsIgnoreCase("select * from application ")){
            sql += "where not (application_status = 'Draft' or application_status = 'Sattlement');";
        }
        Query query = entityManager.createNativeQuery(sql, ApplicationEntity.class);

        return query.getResultList();
    }

    public List<ApplicationEntity> getApplicationOfSattlement(String officeName, String applicationStatus, String applicationDate) {
        if(applicationStatus.equalsIgnoreCase("All")){
            applicationStatus = "";
        }
        String sql = "select * from application ";
        if(!"".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and application_status = '"+applicationStatus+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and application_status = 'Sattlement';";
        }else if(!"".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and applying_office_oid = '"+officeName+"' and application_status = 'Sattlement';";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and application_status = 'Sattlement';";
        }else if("".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and application_status = 'Sattlement';";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where applying_office_oid = '"+officeName+"' and application_status = 'Sattlement'";
        }else if("".equalsIgnoreCase(officeName) && !"".equalsIgnoreCase(applicationStatus) && "".equalsIgnoreCase(applicationDate)){
            sql += "where application_status = '"+applicationStatus+"' and application_status = 'Sattlement';";
        }else if("".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus) && !"".equalsIgnoreCase(applicationDate)){
            sql += "where anomaly_capture_date between '"+applicationDate+"-01-01 00:00:00' AND '"+applicationDate+"-12-31 00:00:00' and application_status = 'Sattlement';";
        }else if(!"".equalsIgnoreCase(officeName) && "".equalsIgnoreCase(applicationStatus)){
            sql += "where applying_office_oid = '"+officeName+"' and application_status = 'Sattlement';";
        }

        if(sql.equalsIgnoreCase("select * from application ")){
            sql += "where application_status = 'Sattlement';";
        }
        System.out.println("This is the sql "+sql);
        Query query = entityManager.createNativeQuery(sql, ApplicationEntity.class);

        return query.getResultList();
    }


    public List<ApplicationEntity> getApplicationByOperator(String createdBy) {
        return applicationRepository.findByCreatedByOrderByCreatedOnDesc(createdBy);
    }

    public List<ApplicationEntity> getApplicationEntities() {
        return applicationRepository.findAll();
    }

    public ApplicationCustomsStepFiveDto SaveCustomApplicationStepFive(String updatedBy, ApplicationCustomsStepFiveDto applicationCustomsStepFiveDto, String oid) {

        String customFileNumber = getCustomsFileNumber();

        ApplicationEntity applicationEntity = applicationRepository.findByOid(oid);
        applicationEntity.setCustomsRewardFileNumber(customFileNumber);
        applicationEntity.setApplicationStatus("Submitted");
        applicationEntity.setApplicationStatusBn("কার্যক্রম চলমান");
        applicationEntity.setUpdatedBy(updatedBy);
        applicationRepository.save(applicationEntity);
        applicationCustomsStepFiveDto.getApplicationAttachmentEntity().setUpdatedBy(updatedBy);
        applicationAttachmentRepository.save(applicationCustomsStepFiveDto.getApplicationAttachmentEntity());

        return applicationCustomsStepFiveDto;
    }

    private String getCustomsFileNumber() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yy");

        String formattedYear = dateFormat.format(new Date());

        List<ApplicationEntity> applicationEntities = applicationRepository.findForNbrAdminByOrderByCreatedOnDesc();

        Integer noOfApplications = applicationEntities.size() + 1;

        String formattedNoOfApplications = String.format("%04d", noOfApplications);

        formattedNoOfApplications = EnglishToBangla.getDigitBanglaFromEnglish(formattedNoOfApplications);
        formattedYear = EnglishToBangla.getDigitBanglaFromEnglish(formattedYear);

        return formattedYear + "-" + formattedNoOfApplications;
    }

    public List<ApplicationEntity> getApplicationsForNbrAdmin() {
        return applicationRepository.findForNbrAdminByOrderByCreatedOnDesc();
    }

    public DownloadFileResponse downloadFile(String refId) throws ExceptionHandlerUtil {



        // Get file
        File file = getDownloadFile(BASE_DIRECTORY, refId);

        // Create response
        DownloadFileResponse response = DownloadFileResponse.builder().file(file).build();

        log.info("Download agent document companyOid - {}, fileName - {}", refId);

        return response;
    }

    private File getDownloadFile(String baseDirectory, String fileName) throws ExceptionHandlerUtil {
        File newFile = null;
        try {
            File file = FileUtils.getFile(baseDirectory, RMS_FILE_DIR);
            newFile = FileUtils.getFile(file, fileName);
        } catch (Exception e) {
            log.error("An Exception occured while get document : ", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return newFile;
    }

    public DownloadFileResponse downloadTwoFile(String refId) throws ExceptionHandlerUtil {
        String baseDirectory = BASE_DIRECTORY;

        // Get file
        File file = getTwoDownloadFile(baseDirectory, refId);

        // Create response
        DownloadFileResponse response = DownloadFileResponse.builder().file(file).build();

        log.info("Download agent document companyOid - {}, fileName - {}", refId);

        return response;
    }

    private File getTwoDownloadFile(String baseDirectory, String fileName) throws ExceptionHandlerUtil {
        File newFile = null;
        try {
            File file = FileUtils.getFile(baseDirectory, RMS_TWO_FILE_DIR);
            newFile = FileUtils.getFile(file, fileName);
        } catch (Exception e) {
            log.error("An Exception occured while get document : ", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return newFile;
    }

    // The below methods are for for download PURBO BOTI BIL OF ENTRY
    // REASON : The file location is another directory.

    public DownloadFileResponse downloadOneFile(String refId) throws ExceptionHandlerUtil {

        // Get file
        File file = getOneDownloadFile(BASE_DIRECTORY, refId);

        // Create response
        DownloadFileResponse response = DownloadFileResponse.builder().file(file).build();

        log.info("Download agent document companyOid - {}, fileName - {}", refId);

        return response;
    }

    private File getOneDownloadFile(String baseDirectory, String fileName) throws ExceptionHandlerUtil {
        File newFile = null;
        try {
            File file = FileUtils.getFile(baseDirectory, "boe");
            newFile = FileUtils.getFile(file, fileName);
        } catch (Exception e) {
            log.error("An Exception occured while get document : ", e);
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return newFile;
    }

    // This is the End Of the Bill of entry download method

    public List<ApplicationDto> getApplicationDtos() {
        List<ApplicationEntity> applicationEntities = getApplicationEntities();
        List<ApplicationDto> applicationDtos = new ArrayList<>();

        for (int i = 0; i < applicationEntities.size(); i++) {

            ApplicationDto applicationDto = new ApplicationDto();
            List<ApplicationAttachmentEntity> applicationAttachmentForStepOne = new ArrayList<>();
            List<ApplicationAttachmentEntity> applicationAttachmentForStepTwo = new ArrayList<>();
            ApplicationCustomsStepFiveDto applicationCustomsStepFiveDto = new ApplicationCustomsStepFiveDto();

            ApplicationEntity applicationEntity = applicationEntities.get(i);
            List<ApplicationAttachmentEntity> applicationAttachmentEntities = applicationAttachmentRepository.findByOid(applicationEntity.getOid());
            List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(applicationEntity.getOid());
            ApplicationCustomsStepOneDto applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntity));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntity));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntity));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntity));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntity));
            applicationCustomsStepFiveDto.setOid(applicationEntity.getOid());

            for (int j = 0; j < applicationAttachmentEntities.size(); j++) {
                if (applicationAttachmentEntities.get(j).getAttachmentType().equals(ValidationPattern.attachmentTypeForStepOne)) {
                    applicationAttachmentForStepOne.add(applicationAttachmentEntities.get(j));

                    applicationCustomsStepOneDto.setApplicationAttachments(applicationAttachmentForStepOne);
                } else if (applicationAttachmentEntities.get(j).getAttachmentType().equals(ValidationPattern.attachmentTypeForStepFive)) {
                    applicationCustomsStepFiveDto.setApplicationAttachmentEntity(applicationAttachmentEntities.get(i));
                } else {
                    applicationAttachmentForStepTwo.add(applicationAttachmentEntities.get(i));
                }
            }

            applicationCustomsStepOneDto.setApplicationAttachments(applicationAttachmentForStepOne);
            applicationCustomsStepTwoDto.setApplicationAttachments(applicationAttachmentForStepTwo);
            applicationCustomsStepOneDto.setRewardAmounts(rewardAmountEntities);
            applicationNbrAdminStepThreeDto.setRewardAmounts(rewardAmountEntities);
            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);
            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);
            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);
            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);
            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);
            applicationDto.setApplicationCustomsStepFiveDto(applicationCustomsStepFiveDto);
            applicationDtos.add(applicationDto);
        }
        return applicationDtos;
    }

    public ApplicationDto getApplicationDtoByOid(String id) {
        List<ApplicationAttachmentEntity> applicationAttachmentForStepOne = new ArrayList<>();
        List<ApplicationAttachmentEntity> applicationAttachmentForStepTwo = new ArrayList<>();

        ApplicationCustomsStepFiveDto applicationCustomsStepFiveDto = new ApplicationCustomsStepFiveDto();

        ApplicationEntity applicationEntity = getApplicationEntity(id);
        List<ApplicationAttachmentEntity> applicationAttachmentEntities = applicationAttachmentRepository.findByOid(applicationEntity.getOid());
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(applicationEntity.getOid());

        ApplicationCustomsStepOneDto applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntity));
        ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntity));
        ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntity));
        ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntity));
        ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntity));
        applicationCustomsStepFiveDto.setOid(applicationEntity.getOid());

        for (int i = 0; i < applicationAttachmentEntities.size(); i++) {
            if (applicationAttachmentEntities.get(i).getAttachmentType().equals(ValidationPattern.attachmentTypeForStepOne)) {
                applicationAttachmentForStepOne.add(applicationAttachmentEntities.get(i));
                applicationCustomsStepOneDto.setApplicationAttachments(applicationAttachmentForStepOne);
            } else if (applicationAttachmentEntities.get(i).getAttachmentType().equals(ValidationPattern.attachmentTypeForStepFive)) {
                applicationCustomsStepFiveDto.setApplicationAttachmentEntity(applicationAttachmentEntities.get(i));
            } else {
                applicationAttachmentForStepTwo.add(applicationAttachmentEntities.get(i));
            }
        }

        applicationCustomsStepOneDto.setApplicationAttachments(applicationAttachmentForStepOne);
        applicationCustomsStepTwoDto.setApplicationAttachments(applicationAttachmentForStepTwo);
        applicationNbrAdminStepThreeDto.setRewardAmounts(rewardAmountEntities);
        applicationCustomsStepOneDto.setRewardAmounts(rewardAmountEntities);

        return new ApplicationDto(applicationCustomsStepOneDto, applicationCustomsStepTwoDto, applicationCustomsStepFiveDto, applicationNbrAdminStepOneDto, applicationNbrAdminStepTwoDto, applicationNbrAdminStepThreeDto);
    }

    public List<ApplicationDto> getApplicationDtosByOperator(String operatorName) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationByOperator(operatorName).stream().filter(
                s->!s.getApplicationStatus().equalsIgnoreCase("Sattlement")
        ).collect(Collectors.toList());

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);

            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);

            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);

            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);

            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);

            applicationDtos.add(applicationDto);

        }
        return applicationDtos;
    }

    public List<ApplicationDto> getApplicationDtosByOperatorOfSattlement(String operatorName) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationByOperator(operatorName).stream().filter(s->s.getApplicationStatus().equalsIgnoreCase("Sattlement")).collect(Collectors.toList());

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);

            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);

            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);

            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);

            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);

            applicationDtos.add(applicationDto);

        }
        return applicationDtos;
    }

    public List<ApplicationDto> getApplicationDtosByStatus(String applicationStatus, String date, String userId) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationByStatus(applicationStatus, date, userId).stream().filter(s->!s.getApplicationStatus().equalsIgnoreCase("Sattlement") && !s.getApplicationStatus().equalsIgnoreCase("Rejected")).collect(Collectors.toList());

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto  applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);

            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);

            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);

            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);

            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);

            applicationDtos.add(applicationDto);

        }
        return applicationDtos;
    }

    public List<ApplicationDto> getApplicationDtosByStatusWithOutSattlement(String applicationStatus, String date, String userId) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationByStatus(applicationStatus, date, userId).stream().filter(
                s-> s.getApplicationStatus().equalsIgnoreCase("Sattlement") ||  s.getApplicationStatus().equalsIgnoreCase("Rejected")).collect(Collectors.toList());

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto  applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);

            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);

            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);

            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);

            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);

            applicationDtos.add(applicationDto);

        }
        return applicationDtos;
    }


    public List<ApplicationDto> getApplicationDtosByStatusAndOfficeName(String officeName, String applicationStatus, String applicationDate) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationByStatusAndOffice(officeName, applicationStatus, applicationDate);

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto  applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);
            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);
            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);
            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);
            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);
            applicationDtos.add(applicationDto);
        }
        return applicationDtos;
    }



    public List<ApplicationDto> getApplicationDtoOfSattlement(String officeName, String applicationStatus, String applicationDate) {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationOfSattlement(officeName, applicationStatus, applicationDate);

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();

            ApplicationCustomsStepOneDto  applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            ApplicationCustomsStepTwoDto applicationCustomsStepTwoDto = applicationConverter.applicationEntityToApplicationCustomsStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto = applicationConverter.applicationEntityToApplicationNbrAdminStepOneDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepTwoDto applicationNbrAdminStepTwoDto = applicationConverter.applicationEntityToApplicationNbrAdminStepTwoDto((applicationEntities.get(i)));
            ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = applicationConverter.applicationEntityToApplicationNbrAdminStepThreeDto((applicationEntities.get(i)));

            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);
            applicationDto.setApplicationCustomsStepTwoDto(applicationCustomsStepTwoDto);
            applicationDto.setApplicationNbrAdminStepOneDto(applicationNbrAdminStepOneDto);
            applicationDto.setApplicationNbrAdminStepTwoDto(applicationNbrAdminStepTwoDto);
            applicationDto.setApplicationNbrAdminStepThreeDto(applicationNbrAdminStepThreeDto);
            applicationDtos.add(applicationDto);
        }
        return applicationDtos;
    }

    public List<ApplicationDto> getApplicationDtosForNbrAdmin() {
        List<ApplicationDto> applicationDtos = new ArrayList<>();
        List<ApplicationEntity> applicationEntities = getApplicationsForNbrAdmin();

        for (int i = 0; i < applicationEntities.size(); i++) {
            ApplicationDto applicationDto = new ApplicationDto();
            ApplicationCustomsStepOneDto applicationCustomsStepOneDto = applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntities.get(i)));
            applicationDto.setApplicationCustomsStepOneDto(applicationCustomsStepOneDto);
            applicationDtos.add(applicationDto);
        }
        return applicationDtos;
    }

    public ApplicationCustomsStepOneDto getApplicationCustomsStepOneDto(String id) {
        List<ApplicationAttachmentEntity> applicationAttachmentForStepOne = new ArrayList<>();
        ApplicationCustomsStepOneDto applicationCustomsStepOneDto = new ApplicationCustomsStepOneDto();

        ApplicationEntity applicationEntity = getApplicationCustomsStepOne(id);

        List<ApplicationAttachmentEntity>  applicationAttachmentEntities = applicationAttachmentRepository.findByOid(applicationEntity.getOid());
        List<RewardAmountEntity> rewardAmountEntities = rewardAmountRepository.findByOid(applicationEntity.getOid());
        BeanUtils.copyProperties(applicationEntity, applicationCustomsStepOneDto);

        for (int i = 0; i < applicationAttachmentEntities.size(); i++) {
            if (applicationAttachmentEntities.get(i).getAttachmentType().equals(ValidationPattern.attachmentTypeForStepOne)) {
                applicationAttachmentForStepOne.add(applicationAttachmentEntities.get(i));
                applicationCustomsStepOneDto.setApplicationAttachments(applicationAttachmentForStepOne);
            }
        }
        applicationCustomsStepOneDto.setRewardAmounts(rewardAmountEntities);
        return applicationCustomsStepOneDto;
    }

    public ApplicationCustomsStepOneDto saveApplicationCustomsStepOneDto(ApplicationCustomsStepOneDto applicationCustomsStepOneDto, String username) {
        ApplicationEntity applicationEntity = saveApplicationCustomsStepOne(applicationCustomsStepOneDto, username);
        return applicationConverter.applicationEntityToApplicationCustomsStepOneDto((applicationEntity));
    }

    public ApplicationNbrAdminStepOneDto getApplicationNbrAdminStepOneDto(String id, ApplicationNbrAdminStepOneDto applicationNbrAdminStepOneDto) {
        applicationNbrAdminStepOneDto.setNbrRewardFileNumber(EnglishToBangla.getDigitBanglaFromEnglish(applicationNbrAdminStepOneDto.getNbrRewardFileNumber()));
        applicationNbrAdminStepOneDto.setCustomsAwardSanctioningCommittee(applicationNbrAdminStepOneDto.getCustomsAwardSanctioningCommittee());
        ApplicationEntity applicationEntity = updateApplicationAdminStepOne(id, applicationNbrAdminStepOneDto);
        BeanUtils.copyProperties(applicationEntity, applicationNbrAdminStepOneDto);
        return applicationNbrAdminStepOneDto;
    }

    public ApplicationNbrAdminStepThreeDto updateApplicationStatusAdmin(String id, String updatedBy) {
        ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = new ApplicationNbrAdminStepThreeDto();
        ApplicationEntity applicationEntity = applicationRepository.findByOid(id);
        applicationEntity.setApplicationStatus("Accepted");
        applicationEntity.setApplicationStatusBn("মঞ্জুরীর জন্য সুপারিশকৃত");
        applicationEntity.setUpdatedBy(updatedBy);
        applicationEntity.setUpdatedOn(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        applicationRepository.save(applicationEntity);
        BeanUtils.copyProperties(applicationEntity, applicationNbrAdminStepThreeDto);
        return applicationNbrAdminStepThreeDto;

    }

    public ApplicationNbrAdminStepThreeDto updateApplicationStatusAdminforSattlement(String id, String updatedBy) {
        ApplicationNbrAdminStepThreeDto applicationNbrAdminStepThreeDto = new ApplicationNbrAdminStepThreeDto();
        ApplicationEntity applicationEntity = applicationRepository.findByOid(id);
        applicationEntity.setApplicationStatus("Sattlement");
        applicationEntity.setApplicationStatusBn("মীমাংসিত");
        applicationEntity.setUpdatedBy(updatedBy);
        applicationEntity.setUpdatedOn(Timestamp.from(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).toInstant()));
        applicationRepository.save(applicationEntity);
        BeanUtils.copyProperties(applicationEntity, applicationNbrAdminStepThreeDto);
        return applicationNbrAdminStepThreeDto;

    }
}
