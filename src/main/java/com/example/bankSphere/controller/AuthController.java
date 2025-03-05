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
    public ResponseEntity<String> registerUser(@RequestBody(required = false) UserDto userDto) {
        System.out.println("Endpoint hit!"); // Debugging

        if (userDto == null) {
            return ResponseEntity.badRequest().body("Request body is missing!");
        }

        System.out.println("Request body received: " + userDto);
        User user = userService.registerUser(userDto);
        return ResponseEntity.ok("Registration processed successfully!");


        // Optionally create an account record for the new user
    }

    // Login endpoint would typically validate credentials and return a JWT token.
    // ...
}
