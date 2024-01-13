package net.spectrum.api.officesection.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.auth.login.entity.LoginEntity;
import net.spectrum.api.auth.login.repository.LoginRepository;
import net.spectrum.api.officesection.dto.OfficeSectionRequestDto;
import net.spectrum.api.officesection.dto.OfficeSectionResponseDto;
import net.spectrum.api.officesection.entity.OfficeSectionEntity;
import net.spectrum.api.officesection.repository.OfficeSectionCustomRepository;
import net.spectrum.api.officesection.repository.OfficeSectionRepository;
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
public class OfficeSectionService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OfficeSectionRepository officeSectionRepository;
    @Autowired
    private OfficeSectionCustomRepository officeSectionCustomRepository;
    @Autowired
    private LoginRepository loginRepository;

    public ResponseEntity<Map<String, Object>> getOfficeSections( int limit, int offset, String searchText) throws ExceptionHandlerUtil {
        List<OfficeSectionResponseDto> officeSectionList = officeSectionCustomRepository.getOfficeSections(limit, offset, searchText);

        if (officeSectionList == null || officeSectionList.isEmpty())
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "office section not found");
        Map<String, Object> response = new HashMap<>();
        response.put("data", officeSectionList);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public OfficeSectionEntity getOfficeSectionByOid(String oid) throws ExceptionHandlerUtil {
        OfficeSectionEntity officeSectionEntity = officeSectionRepository.findByOid(oid);

        if (officeSectionEntity == null)
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Office section not found");
        return officeSectionEntity;
    }

    public OfficeSectionResponseDto saveOfficeSection(OfficeSectionRequestDto officeSectionRequestDto, String userId) throws ExceptionHandlerUtil {
        LoginEntity loginEntity = loginRepository.findByUserId(userId);
        OfficeSectionEntity officeSectionEntity = new OfficeSectionEntity();
        BeanUtils.copyProperties(officeSectionRequestDto, officeSectionEntity);
        officeSectionEntity.setCreatedBy(loginEntity.getLoginOid());

        officeSectionEntity = officeSectionRepository.save(officeSectionEntity);

        if (officeSectionEntity.getOid() == null)
            throw new ExceptionHandlerUtil(HttpStatus.INTERNAL_SERVER_ERROR, "Office section not saved");

        return OfficeSectionResponseDto.builder()
                .oid(officeSectionEntity.getOid())
                .message("Office section saved successfully")
                .build();
    }

    public OfficeSectionResponseDto updateOfficeSection(OfficeSectionRequestDto officeSectionRequestDto, String oid, String userId) throws ExceptionHandlerUtil {
        LoginEntity loginEntity = loginRepository.findByUserId(userId);
        OfficeSectionEntity officeSectionEntity = officeSectionRepository.findByOid(oid);

        if (officeSectionEntity == null)
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Office section not found");

        BeanUtils.copyProperties(officeSectionRequestDto, officeSectionEntity, "oid");
        officeSectionEntity.setUpdatedBy(loginEntity.getLoginOid());

        officeSectionEntity = this.officeSectionRepository.save(officeSectionEntity);

        return OfficeSectionResponseDto.builder()
                .oid(officeSectionEntity.getOid())
                .message("Office section updated successfully")
                .build();
    }

    public ResponseEntity<Map<String, String>> deleteOfficeSection(String oid, String updatedBy) throws ExceptionHandlerUtil {
        OfficeSectionEntity officeSectionEntity = officeSectionRepository.findByOid(oid);

        if (officeSectionEntity == null)
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Office Section not found");

        officeSectionRepository.deleteById(officeSectionEntity.getOid());
        log.info("Removed Office Section information for: {}", oid);
        Map<String, String> response = new HashMap<String, String>();
        response.put("userMessage", Messages.OFFICE_SECTION_REMOVED);
        return new ResponseEntity<Map<String, String>>(response, HttpStatus.OK);

    }
}
