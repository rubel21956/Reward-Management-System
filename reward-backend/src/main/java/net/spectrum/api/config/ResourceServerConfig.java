package net.spectrum.api.config;

import net.spectrum.api.config.filter.RequestFilter;
import net.spectrum.api.auth.permission.entity.PermissionEntity;
import net.spectrum.api.auth.permission.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import java.util.List;

@EnableResourceServer
@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private RequestFilter requestFilter;


	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception {
		List<PermissionEntity> urlPermissionList = permissionRepository.findAll();

		httpSecurity.cors();
		httpSecurity.csrf().disable()
				.addFilterBefore(requestFilter, AbstractPreAuthenticatedProcessingFilter.class)
				.authorizeRequests().antMatchers(
					"/api/**",
				"/forget-password/**",
				"/api/v1//forget-password/**",
				"/v2/api-docs",
				"/api/swagger-ui",
				"/v1/application/applicationDto/**",
				"/validateUser",
				"/configuration/ui",
				"/configuration/**",
				"/swagger-resources/**",
				"/management/**",
				"/swagger-ui.html",
				"/h2-console/**",
				"/public/**",
				"/webjars/**").permitAll();

		for(PermissionEntity urlPermission : urlPermissionList) {
			if(urlPermission.getPermissionName().equalsIgnoreCase("all")){
				httpSecurity.authorizeRequests().antMatchers(HttpMethod.valueOf(urlPermission.getMethod()), urlPermission.getUrl())
						.permitAll();
			}else{
				httpSecurity.authorizeRequests()
						.antMatchers( HttpMethod.valueOf(urlPermission.getMethod()), urlPermission.getUrl())
						.hasAuthority(urlPermission.getPermissionName());
			}
		}

		httpSecurity.authorizeRequests()
				.anyRequest().authenticated()
				.and()
				.headers()
				.frameOptions().disable().and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
