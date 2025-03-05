package com.example.bankSphere.controller;

import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.repository.UserLoggerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserLoggerController {

    private final UserLoggerRepository userLoggerRepository;

    public UserLoggerController(UserLoggerRepository userLoggerRepository) {
        this.userLoggerRepository = userLoggerRepository;
    }

    @GetMapping("/api/user-logger")
    public ResponseEntity<List<UserLogger>> getAllUserLoggers() {
        return ResponseEntity.ok(this.userLoggerRepository.findAll());
    }
}
