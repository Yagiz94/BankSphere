// controller/AuthController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        User user = userService.registerUser(userDto);
        // Optionally create an account record for the new user
        // ...
        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }

    // Login endpoint would typically validate credentials and return a JWT token.
    // ...
}
