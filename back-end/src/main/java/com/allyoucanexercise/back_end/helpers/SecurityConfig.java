package com.allyoucanexercise.back_end.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.setAllowedOrigins(List.of("http://localhost:3000"));
                    config.addAllowedHeader("*");
                    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    return config;
                }))
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(auth -> auth
                        // .requestMatchers("/api/auth/**").permitAll() // Allow authentication requests
                        .requestMatchers("/api/**", "/auth/**").permitAll() // Allow authentication requests
                        .anyRequest().authenticated())
                .formLogin(form -> form.disable()) // I won't be providing a java form login
                .httpBasic(basic -> basic.disable()); // I don't need basic authentication since I'm using cookies

        return http.build();
    }
}
