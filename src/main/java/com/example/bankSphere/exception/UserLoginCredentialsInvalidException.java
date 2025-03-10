package com.example.bankSphere.exception;

public class UserLoginCredentialsInvalidException extends RuntimeException{
    public UserLoginCredentialsInvalidException(String message) {
        super(message);
    }
}
