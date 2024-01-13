package net.spectrum.api.config.swagger;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@EnableSwagger2
public class SwaggerConfig { }

// Swagger config resource link: https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

