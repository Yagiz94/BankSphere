package com.example.bankSphere.exception;

public class UserFieldsMissingException extends RuntimeException {
    public UserFieldsMissingException(String message) {
        super(message);
    }
}
