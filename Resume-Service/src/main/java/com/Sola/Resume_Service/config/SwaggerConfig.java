package com.Sola.Resume_Service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi(){

        return GroupedOpenApi.builder()
                .group("Resume-service")
                .pathsToMatch("/api/resume/**")
                .build();
    }

    public OpenAPI customizedAPI (){
        return new OpenAPI()
                .info(new Info()
                        .title("Resume service API")
                        .description("API Documentation for Resumes Management")
                        .version("v1.0"));
    }



}
