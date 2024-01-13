package net.spectrum.oauth2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    RedisClient redisClient;

    @Autowired
    Environment env;

    @Value("${forget.password.link.web}")
    private String forgetPasswordLinkWeb;

   // @Value("${forget.password.link.app:http://localhost:4200/v1/auth/app/forgot/password/redirect}")
   // private String forgetPasswordLinkApp;

    @Value("${ums.create.user}")
    private String umsCreateUser;

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model){
        System.out.println("The starting of loging mapping");
        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        System.out.println(request.getParameter("username")+ "this is the name");
        String view = "login-unauthorized";

        if(defaultSavedRequest != null){
            System.out.println("This is defaultSavedRequest which is not equal to null");
            String targetURL = defaultSavedRequest.getRedirectUrl();
            if(defaultSavedRequest.getParameterMap() != null
                    && defaultSavedRequest.getParameterMap().get("client_id") != null
                    && defaultSavedRequest.getParameterMap().get("client_id").length > 0){
                System.out.println("This is inner of defaultsavedrequest");
                view = "login-web"; // for web login
                String clientId = defaultSavedRequest.getParameterMap().get("client_id")[0];
                if(clientId.equalsIgnoreCase("app")){
                    System.out.println("This is last condition of defaultsavedrequest");
                    view = "login"; // for app login
                    //model.addAttribute("forgot", forgetPasswordLinkApp);
                } else {
                    model.addAttribute("forgot", forgetPasswordLinkWeb);
                    model.addAttribute("request", umsCreateUser);
                }
            }
        }
        return view;
    }

}
