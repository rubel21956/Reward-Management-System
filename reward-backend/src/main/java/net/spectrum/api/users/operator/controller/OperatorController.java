package net.spectrum.api.users.operator.controller;

import static net.spectrum.api.util.constants.Messages.USER_REGISTERED;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import net.spectrum.api.auth.login.entity.LoginEntity;
import net.spectrum.api.auth.login.repository.LoginRepository;
import net.spectrum.api.users.operator.dto.OperatorDto;
import net.spectrum.api.users.operator.entity.OperatorEntity;
import net.spectrum.api.users.operator.repository.OperatorRepository;
import net.spectrum.api.users.systemadmin.service.SystemAdminService;
import net.spectrum.api.util.ExceptionHandlerUtil;
import net.spectrum.api.util.constants.ValidationPattern;
import net.spectrum.api.util.converter.EnglishToBangla;
import net.spectrum.api.util.mapper.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/operator")
public class OperatorController {

    @Autowired
    OperatorRepository operatorRepository;

    @Autowired
    SystemAdminService systemAdminService;
    @Autowired
    Converter converter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    LoginRepository loginRepository;

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @PostMapping()
    public OperatorDto addOperator(@RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                   @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                   @RequestHeader(name = "Authorization") String token,
                                   @AuthenticationPrincipal Principal principal,
                                   @Valid @RequestBody OperatorDto operatorDto) throws ExceptionHandlerUtil
    {
        OperatorEntity findByUserId = operatorRepository.findByUserId(operatorDto.getUserId());
        if (findByUserId == null){
            operatorDto.setUserId(operatorDto.getUserId());
            OperatorEntity operatorEntity =new OperatorEntity();
            BeanUtils.copyProperties(operatorDto,operatorEntity);
            operatorEntity.setCreatedBy(principal.getName());
            operatorEntity.setCreatedOn(new Timestamp(System.currentTimeMillis()));
            operatorEntity.setRoleOid("role-operator");
            operatorEntity = operatorRepository.save(operatorEntity);
            systemAdminService.saveOperatorInOthers(operatorEntity, operatorDto, principal.getName(), "");
            BeanUtils.copyProperties(operatorEntity,operatorDto);
            return  operatorDto;
        }else{
            throw new ExceptionHandlerUtil(HttpStatus.BAD_REQUEST, USER_REGISTERED);
        }
    }

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @PutMapping()
    public OperatorDto updateOperator(@RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                      @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                      @RequestHeader(name = "Authorization") String token,
                                      @AuthenticationPrincipal Principal principal,
                                      @Valid @RequestBody OperatorDto operatorDto) throws ExceptionHandlerUtil
    {
            OperatorEntity operatorEntity = operatorRepository.findByOid(operatorDto.getOid());
            String oldOperatorUserId = operatorEntity.getUserId();
            operatorDto.setUserId(operatorDto.getUserId());
            BeanUtils.copyProperties(operatorDto,operatorEntity);
            operatorEntity.setUpdatedBy(principal.getName());
            operatorEntity.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
            if(operatorEntity.getOfficeCode().equals("100")){
                operatorEntity.setRoleOid("role-sys-admin");
            }else {
                operatorEntity.setRoleOid("role-operator");
            }

            operatorEntity = operatorRepository.save(operatorEntity);
            systemAdminService.saveOperatorInOthers(operatorEntity, operatorDto, principal.getName(), oldOperatorUserId);
            BeanUtils.copyProperties(operatorEntity,operatorDto);
            return  operatorDto;
    }
    @PreAuthorize("hasAuthority('role-sys-admin')")
    @GetMapping("/{id}")
    public OperatorDto getOperator(@RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                   @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                   @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                   @RequestHeader(name = "Authorization") String token,
                                   @AuthenticationPrincipal Principal principal,
                                   @PathVariable(value = "id") String id)
    {
        OperatorEntity operatorEntity = new OperatorEntity();
        operatorEntity = operatorRepository.findByOid(id);
        OperatorDto operatorDto = new OperatorDto();
        return  (OperatorDto) converter.convert(operatorEntity,operatorDto);
    }


    @PreAuthorize("hasAuthority('role-sys-admin')")
    @GetMapping
    public List<OperatorDto> getOperatorDtos(@RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                                             @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                                             @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                                             @RequestHeader(name = "Authorization") String token,
                                             @AuthenticationPrincipal Principal principal)
    {
        List<OperatorDto>  operatorDtos = new ArrayList<>();
        List<OperatorEntity> operatorEntities = operatorRepository.findAll();
        OperatorDto operatorDto = new OperatorDto();
        for(OperatorEntity operatorEntity: operatorEntities){
            operatorDto = (OperatorDto) converter.convert(operatorEntity,operatorDto);
            operatorDtos.add(operatorDto);
        }
        return operatorDtos;
    }

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @GetMapping("/verify/{id}")
    public String findByUserId(@RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                               @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                               @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                               @RequestHeader(name = "Authorization") String token,
                               @AuthenticationPrincipal Principal principal,
                               @PathVariable("id") String id)
    {
        if (operatorRepository.findByUserId(id) == null){
            return "false";
        }else{
            return "true";
        }
    }

    @PreAuthorize("hasAuthority('role-sys-admin')")
    @GetMapping("/password/{id}")
    public String findByUserPassword(@RequestHeader(name = "Request-Id", required = true) @NotEmpty String requestId,
                               @RequestHeader(name = "Request-Timeout-In-Seconds", required = true) @NotEmpty String requestTimeoutInSeconds,
                               @RequestHeader(name = "Request-Time", required = true) @NotEmpty @Pattern(regexp = ValidationPattern.requestTimePattern, message = "must match yyyy-MM-ddTHH:mm:ss.SSSSSSZ") String requestTime,
                               @RequestHeader(name = "Authorization") String token,
                               @AuthenticationPrincipal Principal principal,
                               @PathVariable("id") String id)
    {
      String password = "";
        System.out.println(id);
        LoginEntity lg = loginRepository.findByUserId(id);
        if(lg == null){
            System.out.println("loginEntity is null");
        }else if(lg != null) {
            System.out.println(lg.getPassword());
        }
      return password;
    }
}
