package com.lab6.controller;

import com.lab6.model.PasswordQualityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordQualityController {

    private static final Logger logger = LoggerFactory.getLogger(PasswordQualityController.class);

    @PostMapping("/password-quality")
    public PasswordQualityResponse checkPasswordQuality(@RequestBody String password) {
        logger.info("Processing password quality check request");
        logger.debug("Password length: {}", password.length());
        
        int score = calculatePasswordScore(password);
        String quality = determineQuality(score);
        String message = generatePasswordMessage(quality, score);
        
        logger.info("Password quality assessment - Score: {}, Quality: {}", score, quality);
        
        PasswordQualityResponse response = new PasswordQualityResponse();
        response.setPassword(password);
        response.setScore(score);
        response.setQuality(quality);
        response.setMessage(message);
        response.setHasUppercase(password.matches(".*[A-Z].*"));
        response.setHasLowercase(password.matches(".*[a-z].*"));
        response.setHasDigit(password.matches(".*\\d.*"));
        response.setHasSpecialChar(password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"));
        response.setLength(password.length());
        
        return response;
    }

    private int calculatePasswordScore(String password) {
        int score = 0;
        
        // Length scoring
        if (password.length() >= 8) score += 20;
        if (password.length() >= 12) score += 10;
        if (password.length() >= 16) score += 10;
        
        // Character variety scoring
        if (password.matches(".*[A-Z].*")) score += 15;
        if (password.matches(".*[a-z].*")) score += 15;
        if (password.matches(".*\\d.*")) score += 15;
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) score += 15;
        
        return Math.min(score, 100);
    }

    private String determineQuality(int score) {
        if (score < 40) return "Weak";
        if (score < 70) return "Moderate";
        if (score < 90) return "Strong";
        return "Very Strong";
    }

    private String generatePasswordMessage(String quality, int score) {
        StringBuilder message = new StringBuilder();
        message.append("Password analysis complete. ");
        
        switch (quality) {
            case "Weak":
                message.append("Your password is weak and needs improvement. ");
                message.append("Consider adding uppercase letters, numbers, special characters, and increasing length.");
                break;
            case "Moderate":
                message.append("Your password is moderate but could be stronger. ");
                message.append("Try adding more character variety or increasing the length.");
                break;
            case "Strong":
                message.append("Your password is strong! ");
                message.append("It has good character variety and length.");
                break;
            case "Very Strong":
                message.append("Excellent! Your password is very strong. ");
                message.append("It meets all security criteria for a robust password.");
                break;
        }
        
        return message.toString();
    }
}