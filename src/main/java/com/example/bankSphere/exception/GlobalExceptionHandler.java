// exception/GlobalExceptionHandler.java
package com.example.bankSphere.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Additional exception handlers...
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "User Not Found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserFieldsMissingException.class)
    public ResponseEntity<Map<String, Object>> handleUserFieldsMissingException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "One or More Fields are Missing");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserUserAlreadyExistsException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "User Already Exists");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.FOUND);
    }

    @ExceptionHandler(UserLoginCredentialsInvalidException.class)
    public ResponseEntity<Map<String, Object>> handleUserLoginCredentialsInvalidException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Login Credentials are Invalid");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_ACCEPTABLE.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserAccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFoundException(UserAccountNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Account Not Found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserTransactionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTransactionNotFoundException(UserTransactionNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Transaction Not Found");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientFundException(InsufficientFundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Insufficient Funds");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("status", HttpStatus.NOT_FOUND.value());
        errorResponse.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
