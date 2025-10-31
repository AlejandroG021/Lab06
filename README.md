# Lab 6: REST Endpoints

## Project Overview
This project implements REST endpoints for password quality checking and email address validation using Spring Boot.

## Project Structure
```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── lab6/
│   │           ├── Lab6Application.java
│   │           ├── config/
│   │           │   ├── LoggingInterceptor.java
│   │           │   └── WebConfig.java
│   │           ├── controller/
│   │           │   ├── PasswordQualityController.java
│   │           │   └── EmailValidationController.java
│   │           └── model/
│   │               ├── PasswordQualityResponse.java
│   │               └── EmailValidationResponse.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/
        └── com/
            └── lab6/
                └── controller/
                    └── ControllerTests.java
```

## Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

## REST Endpoints Documentation

| Endpoint | Method | Consumes | Returns |
|----------|--------|----------|---------|
| `/api/password-quality` | POST | Plain text (password string) | JSON object with password quality metrics |
| `/api/email-address-valid` | POST | Plain text (email string) | JSON object with validation result |

### Endpoint Details

#### 1. Password Quality Endpoint
- **URL**: `http://localhost:8080/api/password-quality`
- **Method**: POST
- **Content-Type**: text/plain
- **Request Body**: Password string (e.g., "MyPassword123!")
- **Response**: JSON object containing:
  - `password`: The password that was checked
  - `score`: Numerical score (0-100)
  - `quality`: Quality rating ("Weak", "Moderate", "Strong", "Very Strong")
  - `hasUppercase`: Boolean indicating if password contains uppercase letters
  - `hasLowercase`: Boolean indicating if password contains lowercase letters
  - `hasDigit`: Boolean indicating if password contains digits
  - `hasSpecialChar`: Boolean indicating if password contains special characters
  - `length`: Length of the password

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/password-quality \
  -H "Content-Type: text/plain" \
  -d "Test123!"
```

**Example Response:**
```json
{
  "password": "Test123!",
  "score": 80,
  "quality": "Strong",
  "message": "Password analysis complete. Your password is strong! It has good character variety and length.",
  "hasUppercase": true,
  "hasLowercase": true,
  "hasDigit": true,
  "hasSpecialChar": true,
  "length": 8
}
```

#### 2. Email Validation Endpoint
- **URL**: `http://localhost:8080/api/email-address-valid`
- **Method**: POST
- **Content-Type**: text/plain
- **Request Body**: Email address string (e.g., "user@example.com")
- **Response**: JSON object containing:
  - `email`: The email address that was validated
  - `valid`: Boolean indicating if the email is valid
  - `reason`: Explanation of validation result

**Example Request:**
```bash
curl -X POST http://localhost:8080/api/email-address-valid \
  -H "Content-Type: text/plain" \
  -d "user@example.com"
```

**Example Response:**
```json
{
  "email": "user@example.com",
  "valid": true,
  "reason": "Valid email format",
  "message": "Success! The email address 'user@example.com' has a valid format and can be used."
}
```


## How to Build and Run

### Building the Project
```bash
# Build the project
mvn clean install
```

### Running the Application
```bash
# Run using Maven
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Running Unit Tests
```bash
# Run all tests
mvn test

# Run tests with detailed output
mvn test -X
```

## Testing the Endpoints

### Using cURL

**Test Password Quality:**
```bash
curl -X POST http://localhost:8080/api/password-quality \
  -H "Content-Type: text/plain" \
  -d "Test123!"
```

**Test Email Validation:**
```bash
curl -X POST http://localhost:8080/api/email-address-valid \
  -H "Content-Type: text/plain" \
  -d "test@example.com"
```

## Password Quality Scoring System
- **Length scoring:**
  - 8+ characters: +20 points
  - 12+ characters: +10 points
  - 16+ characters: +10 points
- **Character variety scoring:**
  - Uppercase letters: +15 points
  - Lowercase letters: +15 points
  - Digits: +15 points
  - Special characters: +15 points
- **Quality ratings:**
  - 0-39: Weak
  - 40-69: Moderate
  - 70-89: Strong
  - 90-100: Very Strong

## Email Validation Rules
- Must contain exactly one @ symbol
- Must contain at least one period (.)
- Must have characters before and after @
- Must have a valid domain extension
- Cannot start or end with @ or .

## Unit Tests Included
- Password quality tests (weak, moderate, strong passwords)
- Password feature tests (uppercase, lowercase, digits, special characters)
- Email validation tests (valid and invalid formats)
- Edge case tests (null, empty strings)
