// config/SecurityConfig.java
package com.example.bankSphere.config;

import com.example.bankSphere.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll()  // Allow all routes under /api/**
                        .anyRequest().permitAll()  // Allow any other request to be accessed without authentication
                )
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for APIs (ensure this is done properly)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter (remove if not needed)
        return http.build();
    }
}