package net.spectrum.api.auth.role.service;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.spectrum.api.auth.role.entity.RoleEntity;
import net.spectrum.api.auth.role.repository.RoleRepository;
import net.spectrum.api.util.ExceptionHandlerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Builder
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public ResponseEntity<Map<String, Object>> getRoleList(String userId) throws ExceptionHandlerUtil {
        List<RoleEntity> roleList = roleRepository.findAll();
        if(roleList.size() == 0) {
            log.error("Error occurred during retrieving all list: all list not found");
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "Data not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", roleList);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }
}
