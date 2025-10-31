package com.lab6.controller;

import com.lab6.model.EmailValidationResponse;
import com.lab6.model.PasswordQualityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ControllerTests {

    @Autowired
    private PasswordQualityController passwordController;

    @Autowired
    private EmailValidationController emailController;

    // Password Quality Tests
    @Test
    public void testWeakPassword() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("abc");
        assertEquals("Weak", response.getQuality());
        assertTrue(response.getScore() < 40);
    }

    @Test
    public void testModeratePassword() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("password123");
        assertEquals("Moderate", response.getQuality());
        assertTrue(response.getScore() >= 40 && response.getScore() < 70);
    }

    @Test
    public void testStrongPassword() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("Password123!");
        assertTrue(response.getQuality().equals("Strong") || response.getQuality().equals("Very Strong"));
        assertTrue(response.getScore() >= 70);
    }

    @Test
    public void testPasswordWithUppercase() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("Password");
        assertTrue(response.isHasUppercase());
        assertTrue(response.isHasLowercase());
    }

    @Test
    public void testPasswordWithDigits() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("pass123");
        assertTrue(response.isHasDigit());
    }

    @Test
    public void testPasswordWithSpecialChars() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("pass@word!");
        assertTrue(response.isHasSpecialChar());
    }

    @Test
    public void testPasswordLength() {
        PasswordQualityResponse response = passwordController.checkPasswordQuality("Testing123!");
        assertEquals(11, response.getLength());
    }

    // Email Validation Tests
    @Test
    public void testValidEmail() {
        EmailValidationResponse response = emailController.validateEmail("test@example.com");
        assertTrue(response.isValid());
        assertEquals("Valid email format", response.getReason());
    }

    @Test
    public void testInvalidEmailNoAt() {
        EmailValidationResponse response = emailController.validateEmail("testexample.com");
        assertFalse(response.isValid());
        assertEquals("Missing @ symbol", response.getReason());
    }

    @Test
    public void testInvalidEmailMultipleAt() {
        EmailValidationResponse response = emailController.validateEmail("test@@example.com");
        assertFalse(response.isValid());
    }

    @Test
    public void testInvalidEmailNoDomain() {
        EmailValidationResponse response = emailController.validateEmail("test@");
        assertFalse(response.isValid());
    }

    @Test
    public void testInvalidEmailNoExtension() {
        EmailValidationResponse response = emailController.validateEmail("test@example");
        assertFalse(response.isValid());
    }

    @Test
    public void testEmptyEmail() {
        EmailValidationResponse response = emailController.validateEmail("");
        assertFalse(response.isValid());
        assertEquals("Email is empty or null", response.getReason());
    }

    @Test
    public void testNullEmail() {
        EmailValidationResponse response = emailController.validateEmail(null);
        assertFalse(response.isValid());
        assertEquals("Email is empty or null", response.getReason());
    }

    @Test
    public void testEmailWithNumbers() {
        EmailValidationResponse response = emailController.validateEmail("user123@test456.com");
        assertTrue(response.isValid());
    }

    @Test
    public void testEmailWithDash() {
        EmailValidationResponse response = emailController.validateEmail("user-name@test-domain.com");
        assertTrue(response.isValid());
    }
}