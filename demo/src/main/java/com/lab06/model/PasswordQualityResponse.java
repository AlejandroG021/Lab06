package com.lab6.model;

public class PasswordQualityResponse {
    private String password;
    private int score;
    private String quality;
    private String message;
    private boolean hasUppercase;
    private boolean hasLowercase;
    private boolean hasDigit;
    private boolean hasSpecialChar;
    private int length;

    // Getters and Setters
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasUppercase() {
        return hasUppercase;
    }

    public void setHasUppercase(boolean hasUppercase) {
        this.hasUppercase = hasUppercase;
    }

    public boolean isHasLowercase() {
        return hasLowercase;
    }

    public void setHasLowercase(boolean hasLowercase) {
        this.hasLowercase = hasLowercase;
    }

    public boolean isHasDigit() {
        return hasDigit;
    }

    public void setHasDigit(boolean hasDigit) {
        this.hasDigit = hasDigit;
    }

    public boolean isHasSpecialChar() {
        return hasSpecialChar;
    }

    public void setHasSpecialChar(boolean hasSpecialChar) {
        this.hasSpecialChar = hasSpecialChar;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}