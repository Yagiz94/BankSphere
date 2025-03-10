// controller/AuthController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.service.UserLoggerService;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoggerService userLoggerService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {

        // check if the request body is missing
        if (userDto == null) {
            return ResponseEntity.badRequest().body("Request body is missing!");
        }

        // check if the request body contains missing fields
        String missingFields = getMissingFields(userDto);
        if (!missingFields.isEmpty()) {
            return ResponseEntity.badRequest().body("The following fields are missing: " + missingFields);
        }

        // register the user or return an error
        try {
            User user = userService.registerUser(userDto);
            System.out.println(
                    userLoggerService.saveUserLogger(new UserLogger(
                            user.getUsername(),
                            user.getEmail(),
                            Instant.now().toString(),
                            "New User")
            ));
            return ResponseEntity.ok(user.getUsername() + " has been registered successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    // check missing JSON attributes (fields)
    private String getMissingFields(UserDto userDto) {
        List<String> missingFields = new ArrayList<>();
        if (userDto.getUsername() == null) missingFields.add("username");
        if (userDto.getPassword() == null) missingFields.add("password");
        if (userDto.getPhone() == null) missingFields.add("phone number");
        return String.join(", ", missingFields);
    }

    // Login endpoint would typically validate credentials and return a JWT token.
    // ...
}
