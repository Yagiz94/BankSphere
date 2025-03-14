package com.example.bankSphere.exception;

public class UserAccountNotFoundException extends RuntimeException {
    public UserAccountNotFoundException(String message) {
        super(message);
    }
}
