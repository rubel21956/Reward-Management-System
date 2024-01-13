package net.spectrum.api.office.service;

import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.office.dto.OfficeRequestDto;
import net.spectrum.api.office.dto.OfficeResponseDto;
import net.spectrum.api.office.entity.OfficeEntity;
import net.spectrum.api.office.repository.OfficeRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.Messages;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OfficeService {

    @Autowired
    private OfficeRepository officeRepository;

    public List<OfficeEntity> getOffices() throws ExceptionHandlerUtil {
        List<OfficeEntity> officeList = officeRepository.findAll();

        if (officeList == null || officeList.isEmpty())
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "data not found");
        return officeList;
    }

    public OfficeEntity getOfficeByOid(String oid) throws ExceptionHandlerUtil {
        OfficeEntity officeEntity = officeRepository.findByOid(oid);

        if (officeEntity == null)
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "data not found");
        return officeEntity;
    }

    public OfficeResponseDto saveOffice(OfficeRequestDto officeRequestDto, String createdBy) throws ExceptionHandlerUtil {
        OfficeEntity officeEntity = new OfficeEntity();
        BeanUtils.copyProperties(officeRequestDto, officeEntity);
        officeEntity.setOid(officeEntity.getCode());
        officeEntity.setCreatedBy(createdBy);
        officeEntity = officeRepository.save(officeEntity);

        if (officeEntity.getOid() == null)
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "data not saved");

        return OfficeResponseDto.builder()
                .oid(officeEntity.getOid())
                .message("Data saved successfully")
                .build();
    }

    public OfficeResponseDto updateOffice(OfficeRequestDto officeRequestDto, String oid, String updatedBy) throws ExceptionHandlerUtil {
        OfficeEntity officeEntity = getOfficeByOid(oid);
        BeanUtils.copyProperties(officeRequestDto, officeEntity, "oid");
        officeEntity.setUpdatedBy(updatedBy);
        officeEntity = this.officeRepository.save(officeEntity);

        return OfficeResponseDto.builder()
                .oid(officeEntity.getOid())
                .message("Data updated successfully")
                .build();
    }

    public ResponseEntity<Map<String, String>> deleteOffice(String oid, String updatedBy) throws ExceptionHandlerUtil {
        OfficeEntity entity = getOfficeByOid(oid);

        officeRepository.deleteById(entity.getOid());
        log.info("Removed Office information for: {}", oid);
        Map<String, String> response = new HashMap<String, String>();
        response.put("userMessage", Messages.OFFICE_REMOVED);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);
    }

}
