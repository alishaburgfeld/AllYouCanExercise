package com.allyoucanexercise.back_end.helpers;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class HttpLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(HttpLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        // 🔥 Force flush response so body is available in full
        responseWrapper.flushBuffer();

        logRequest(requestWrapper);
        logResponse(responseWrapper);

        // ✅ Write the response back to the output stream
        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        String payload = new String(request.getContentAsByteArray());
        log.info("Request: method={}, uri={}, payload={}", request.getMethod(), request.getRequestURI(), payload);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        String payload = new String(response.getContentAsByteArray());
        log.info("Response: status={}, payload={}", response.getStatus(), payload);
    }
}

//
// https://petrepopescu.tech/2023/07/how-to-log-http-request-and-response-in-spring-boot/