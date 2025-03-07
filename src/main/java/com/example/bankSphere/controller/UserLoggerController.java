package com.example.bankSphere.controller;

import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.repository.UserLoggerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserLoggerController {

    private final UserLoggerRepository userLoggerRepository;

    public UserLoggerController(UserLoggerRepository userLoggerRepository) {
        this.userLoggerRepository = userLoggerRepository;
    }

    @GetMapping("/api/log/list")
    public ResponseEntity<List<UserLogger>> getUserLoggers() {
        return ResponseEntity.ok(this.userLoggerRepository.findAll());
    }
    @PostMapping("/api/log/insert")
    public ResponseEntity<UserLogger> createUserLogger(@RequestBody UserLogger userLogger) {
        System.out.println("Adding a new user logger");
        UserLogger savedUserLogger = userLoggerRepository.save(userLogger);
        return ResponseEntity.ok(savedUserLogger);
    }
    @DeleteMapping("/api/user-logger/{id}")
    public ResponseEntity<Void> deleteUserLogger(@PathVariable String id) {
        System.out.println("Deleting user logger with id:" + id);
        userLoggerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
