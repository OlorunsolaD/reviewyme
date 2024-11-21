package com.Sola.resume_creation_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalAuthentication
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(Request -> Request
                        .requestMatchers("/api/v1/resumes/create",
                                "/api/v1/resumes/{id}/update",
                                "/api/v1/resumes/all",
                                "/api/v1/resumes/find/{id}",
                                "/api/v1/resumes/delete/{id}"
                        ).permitAll()
                        .requestMatchers("/api/v1/resumes/{id}/under_review",
                                "/api/v1/resumes/{id}/completed")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .build();

    }
}