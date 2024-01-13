package net.spectrum.oauth2;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl {

    @Autowired
    ClientRegistrationService clientRegistrationService;

    @Autowired
    SpringSessionBackedSessionRegistry sessionRegistry;

    @Autowired
    FindByIndexNameSessionRepository<? extends Session> sessions;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    private LoginTrailRepository loginTrailRepository;

    @Autowired
    private LoginRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private RoleCustomRepository roleCustomRepository;

    public ResponseEntity<Map<String, Object>> customLogout(HttpServletRequest request, HttpServletResponse response,
                                                            Authentication authentication) {
        Map<String, Object> userResponse = new HashMap<>();

        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();

        removeToken(userId);

        getLoggedInUserData(userId);

        removeSession(userId);

        new SecurityContextLogoutHandler().logout(request, response, authentication);

        userResponse.put("userMessage", "User logged out successfully");
        return new ResponseEntity<Map<String, Object>>(userResponse, HttpStatus.OK);
    }

    public void getLoggedInUserData(String usrId){
        List<LoginTrailEntity> loggedInUsers = loginTrailRepository.findByUserIdAndStatusOrderBySignInTimeDesc(usrId, "Active");

        for (LoginTrailEntity loginTrailEntity : loggedInUsers){
            updateLogoutTime(loginTrailEntity);
        }
        log.info("update login trail for user : {}", usrId);
    }

    public void removeToken(String userId){

        for (ClientDetails client : clientRegistrationService.listClientDetails()) {
            for (OAuth2AccessToken token : tokenStore.findTokensByClientIdAndUserName(client.getClientId(), userId)) {
                tokenStore.removeRefreshToken(token.getRefreshToken());
                tokenStore.removeAccessToken(token);
            }
        }
    }

    public void updateLogoutTime(LoginTrailEntity loginTrailEntity){
        loginTrailEntity.setStatus("Terminate");
        loginTrailEntity.setSignOutTime(Timestamp.valueOf(LocalDateTime.now()));
        loginTrailRepository.save(loginTrailEntity);
    }

    public void removeSession(String userId){
        Collection<? extends Session> usersSessions = sessions
                .findByPrincipalName(userId)
                .values();

        for (Session session : usersSessions){
            sessionRegistry.getSessionInformation(session.getId()).expireNow();
            sessions.deleteById(session.getId());
        }
        log.info("removed session for user : {}", userId);

    }

    public ResponseEntity<Map<String, Object>> getUserSessionList(Authentication authentication, Pageable pageable, String searchText)  {
        Map<String, Object> response = new HashMap<>();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<LoginTrailEntity> userSessionList = loginTrailRepository.findByStatusAndUserIdContainingIgnoreCaseAndUserIdNotInOrderBySignInTimeDesc("Active",
                searchText, Arrays.asList(userDetails.getUsername()) , pageable);
        response.put("userMessage", "User session list retrieved successfully");
        response.put("data", userSessionList);
        response.put("count", loginTrailRepository.countByStatusAndUserIdContainingIgnoreCaseAndUserIdNotInOrderBySignInTimeDesc("Active",
                searchText, Arrays.asList(userDetails.getUsername())));

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> updateUserSessionList(SessionRequestDto requestDto){
        Map<String, Object> response = new HashMap<>();
        List<LoginTrailEntity> userSessionList = loginTrailRepository.findByLoginTrailOidInOrderBySignInTimeDesc(requestDto.getOids());
        for (LoginTrailEntity loginTrailEntity : userSessionList){
            updateLogoutTime(loginTrailEntity);
            log.info("update login trail for oid : {}", loginTrailEntity.getLoginTrailOid());
            removeToken(loginTrailEntity.getUserId());
            removeSession(loginTrailEntity.getUserId());
        }
        response.put("userMessage", "User logged out successfully");
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
    }

    public ResponseEntity<UserInfo> getUserInfo(Principal principal) throws ExceptionHandlerUtil {

        LoginEntity user = userRepository.findByUserId(principal.getName());
        UserProfileEntity userProfileEntity = userProfileRepository.findByLoginOid(user.getLoginOid());
        OfficeEntity officeEntity = officeRepository.findByOid(userProfileEntity.getCurrentOffice());
//        String applicationStatus = roleCustomRepository.getUserId(principal.getName());
        if(user == null){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "User not found");
        }
        if(user.getStatus().equals("নিষ্ক্রিয়")){
            throw new ExceptionHandlerUtil(HttpStatus.NOT_FOUND, "User is Inactive!");
        }

        log.info("Start fetching last login time from login trail repository by userId : {}", user.getUserId());
        List<LoginTrailEntity> loginTrails = loginTrailRepository.findByUserId(principal.getName(), PageRequest.of(0,1, Sort.Direction.DESC, "SignInTime"));
        log.info("Successfully fetched login trail from repository by userId : {}", loginTrails);
        LoginTrailEntity loginTrail = loginTrails.size() == 0 ? null : loginTrails.get(0);

        List<MenuJson> menuJsonList = new ArrayList<>();

        List<PermissionEntity> permissionEntityList =  user.getRoles().stream()
                        .flatMap(role -> role.getPermissions().stream()).collect(Collectors.toList());
        permissionEntityList.sort((e1, e2) -> e1.getCreatedOn().compareTo(e2.getCreatedOn()));
//        log.info("Sending permission : {}",permissionEntityList);
        List<PermissionEntity> distinctTopMenuIds = permissionEntityList.stream()
                .filter(permissionEntity -> Strings.isNotBlank(permissionEntity.getTopMenuId()) )
                .filter(distinctByKey(p -> p.getTopMenuId()))
                .collect(Collectors.toList());
//        log.info("Sending Topmenu : {}",distinctTopMenuIds);
        for(PermissionEntity permissionEntity : distinctTopMenuIds){
            List<String> leftMenuIds = new ArrayList<>();
            for(PermissionEntity permission : permissionEntityList){
                if(Strings.isNotBlank(permission.getTopMenuId()) && permission.getTopMenuId().equals(permissionEntity.getTopMenuId())){
                    leftMenuIds.add(permission.getLeftMenuId());
                }
//                log.info("Sending leftmenu : {}",leftMenuIds);
            }
            MenuJson menuJson = MenuJson.builder().topMenuId(permissionEntity.getTopMenuId())
                    .leftMenuId(leftMenuIds).build();
            log.info("Sending menuJson : {}",menuJson);
            menuJsonList.add(menuJson);
        }
//        log.info("Sending menujsonlist : {}",menuJsonList);


        List<String> roles =
                user.getRoles().stream()
                        .map(roleEntity -> roleEntity.getRoleName()).collect(Collectors.toList());

        UserInfo userInfo = UserInfo.builder()
                .roleOid(user.getRoleOid())
                .currentOfficeName(officeEntity.getBNname())
                .userId(user.getUserId())
                .menuJson(menuJsonList)
                .lastLoginTime(new SimpleDateFormat("EEEEE MMMMM dd, yyyy h:mm:ss aaa").format(loginTrail.getSignInTime()))
                .roles(roles)
                .resetRequired(user.getResetRequired())
                .status(user.getStatus())
                .userMessage("Successfully retrieved user info")
                .designation(userProfileEntity.getDesignation())
                .build();

        
        return new ResponseEntity<UserInfo>(userInfo, HttpStatus.OK);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor)
    {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
