package com.lab6.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Enumeration;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        logger.info("========== Incoming HTTP Request ==========");
        logger.info("Request URL: {} {}", request.getMethod(), request.getRequestURL().toString());
        logger.info("Remote Address: {}", request.getRemoteAddr());
        logger.info("Remote Host: {}", request.getRemoteHost());
        logger.info("Remote Port: {}", request.getRemotePort());
        
        // Log query string if present
        if (request.getQueryString() != null) {
            logger.info("Query String: {}", request.getQueryString());
        }

        // Log headers
        logger.info("Request Headers:");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            logger.info("  {}: {}", headerName, headerValue);
        }

        // Log request parameters
        if (!request.getParameterMap().isEmpty()) {
            logger.info("Request Parameters:");
            request.getParameterMap().forEach((key, value) -> 
                logger.info("  {}: {}", key, String.join(", ", value))
            );
        }

        logger.info("Content Type: {}", request.getContentType());
        logger.info("Content Length: {}", request.getContentLength());
        logger.info("==========================================");

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - startTime;

        logger.info("========== HTTP Response ==========");
        logger.info("Request URL: {} {}", request.getMethod(), request.getRequestURL().toString());
        logger.info("Response Status: {}", response.getStatus());
        logger.info("Execution Time: {} ms", executeTime);
        
        if (ex != null) {
            logger.error("Exception occurred: ", ex);
        }
        
        logger.info("===================================");
    }
}