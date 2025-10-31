package com.lab6.controller;

import com.lab6.model.EmailValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailValidationController {

    private static final Logger logger = LoggerFactory.getLogger(EmailValidationController.class);

    @PostMapping("/email-address-valid")
    public EmailValidationResponse validateEmail(@RequestBody String email) {
        logger.info("Processing email validation request");
        logger.debug("Email to validate: {}", email);
        
        boolean isValid = isValidEmail(email);
        String reason = isValid ? "Valid email format" : getInvalidReason(email);
        String message = generateEmailMessage(isValid, email);
        
        logger.info("Email validation result - Email: {}, Valid: {}, Reason: {}", email, isValid, reason);
        
        EmailValidationResponse response = new EmailValidationResponse();
        response.setEmail(email);
        response.setValid(isValid);
        response.setReason(reason);
        response.setMessage(message);
        
        return response;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        
        // Basic email regex pattern
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    private String getInvalidReason(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Email is empty or null";
        }
        if (!email.contains("@")) {
            return "Missing @ symbol";
        }
        if (email.indexOf("@") != email.lastIndexOf("@")) {
            return "Multiple @ symbols found";
        }
        if (!email.contains(".")) {
            return "Missing domain extension";
        }
        if (email.startsWith("@") || email.endsWith("@")) {
            return "@ symbol at invalid position";
        }
        if (email.startsWith(".") || email.endsWith(".")) {
            return "Period at invalid position";
        }
        
        return "Invalid email format";
    }

    private String generateEmailMessage(boolean isValid, String email) {
        if (isValid) {
            return "Success! The email address '" + email + "' has a valid format and can be used.";
        } else {
            return "Email validation failed. The address '" + email + "' does not meet the required format criteria. Please check and try again.";
        }
    }
}