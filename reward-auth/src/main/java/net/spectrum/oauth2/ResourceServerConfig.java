package net.spectrum.oauth2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

import java.util.List;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {

		//httpSecurity.cors().disable();
		httpSecurity.csrf().disable()
				.authorizeRequests().antMatchers(
				"/v2/api-docs",
				"/configuration/ui",
				"/configuration/**",
				"/swagger-resources/**",
				"/management/**",
				"/swagger-ui.html",
				"/h2-console/**",
				"/webjars/**",
				"/oauth/token",
				"/v1/auth/app/forgot/password/redirect",
				"/v1/auth/app/register/redirect",
				"/v1/auth/app/account/redirect",
				"/v1/auth/app/login").permitAll();

		httpSecurity.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
				.anyRequest().authenticated()
				.and()
				.headers()
				.frameOptions().disable().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
