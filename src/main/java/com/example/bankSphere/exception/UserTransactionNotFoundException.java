package com.example.bankSphere.exception;

public class UserTransactionNotFoundException extends RuntimeException {
    public UserTransactionNotFoundException(String message) {
        super(message);
    }
}
