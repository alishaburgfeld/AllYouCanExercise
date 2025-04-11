package com.allyoucanexercise.back_end.helpers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.util.StringUtils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.function.Supplier;

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
                                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // Stores
                                                                                                                    // CSRF
                                                                                                                    // token
                                                                                                                    // in
                                                                                                                    // cookie
                                                .csrfTokenRequestHandler(new SpaCsrfTokenRequestHandler()) // Handles
                                                                                                           // CSRF for
                                                                                                           // SPAs
                                )
                                .sessionManagement(session -> session
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/**", "/auth/**").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(form -> form.disable())
                                .httpBasic(basic -> basic.disable());

                return http.build();
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }
}

/**
 * Custom CSRF handler for SPAs
 */
final class SpaCsrfTokenRequestHandler implements CsrfTokenRequestHandler {
        private final CsrfTokenRequestHandler plain = new CsrfTokenRequestAttributeHandler();
        private final CsrfTokenRequestHandler xor = new XorCsrfTokenRequestAttributeHandler();

        @Override
        public void handle(HttpServletRequest request, HttpServletResponse response, Supplier<CsrfToken> csrfToken) {
                this.xor.handle(request, response, csrfToken);
                csrfToken.get(); // Ensures CSRF token is set in a cookie
        }

        @Override
        public String resolveCsrfTokenValue(HttpServletRequest request, CsrfToken csrfToken) {
                String headerValue = request.getHeader(csrfToken.getHeaderName());
                return (StringUtils.hasText(headerValue) ? this.plain : this.xor).resolveCsrfTokenValue(request,
                                csrfToken);
        }
}
