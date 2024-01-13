package net.spectrum.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public WebSecurityConfig() {
        super();
    }

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.parentAuthenticationManager(authenticationManagerBean()).userDetailsService(userDetailsService).passwordEncoder(encoder);
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    protected void configure(HttpSecurity http) throws Exception {

        http.cors().disable();

        http.csrf().disable()
                .requestMatchers()
                .antMatchers("/", "/login", "/forget-password",
                    "/api/v1//forget-password","/oauth/authorize**" , "/oauth2/check_token","/api/swagger-ui",
                    "/swagger-ui.html",
                    "/api/**")
                .and()
                .authorizeRequests()
                .antMatchers(
                    "/api/swagger-ui",
                        "/swagger-ui.html",
                    "/forget-password",
                    "/api/v1//forget-password",
                        "/api/**",
                        "/oauth/token",
                        "/v1/auth/app/forgot/password/redirect",
                        "/v1/auth/app/register/redirect"
                ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll();

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .sessionRegistry(sessionRegistry())
                ;

    }

    @Bean
    @SuppressWarnings("unchecked")
    public SpringSessionBackedSessionRegistry sessionRegistry() {
        return new SpringSessionBackedSessionRegistry(this.sessionRepository);
    }

    /*@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }*/

   /* @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }*/
}
