// controller/AuthController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserRequestDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.entity.UserResponse;
import com.example.bankSphere.exception.UserAlreadyExistsException;
import com.example.bankSphere.exception.UserFieldsMissingException;
import com.example.bankSphere.exception.UserLoginCredentialsInvalidException;
import com.example.bankSphere.exception.UserNotFoundException;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.AuthService;
import com.example.bankSphere.service.UserLoggerService;
import com.mongodb.MongoSocketException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService userService;

    @Autowired
    private UserLoggerService userLoggerService;

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequestDto userDto) {

        // check if the request body is missing
        if (userDto == null) {
            return ResponseEntity.badRequest().body("Missing request body!");
        }

        // check if the request body contains missing fields
        String missingFields = getMissingFields(userDto);
        if (!missingFields.isEmpty()) {
            throw new UserFieldsMissingException("Missing fields: " + missingFields);
        }

        // register a new user with a new account and log the registry
        User user;
        try {
            user = userService.registerUser(userDto);
        } catch (RuntimeException e) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        try {
            userLoggerService.saveUserLogger(
                    new UserLogger(user.getUsername(), user.getEmail(), Instant.now().toString(), "New User"));
        } catch (MongoSocketException e) {
            System.out.println("Something went wrong with logging but user registration is successful");
        }

        // successful registration responds a token value
        return ResponseEntity.ok("{\n" +
                "\t\"message\": \"User registered successfully!\"" + "\n" +
                "\t\"token\": \"" + generateToken(user.getUsername(), user.getPassword()) + "\"\n}");

    }

    // check missing JSON attributes (fields)
    private String getMissingFields(UserRequestDto userDto) {
        List<String> missingFields = new ArrayList<>();
        if (userDto.getUsername() == null) missingFields.add("username");
        if (userDto.getPassword() == null) missingFields.add("password");
        if (userDto.getPhone() == null) missingFields.add("phone number");
        return String.join(", ", missingFields);
    }

    // Login endpoint would typically validate credentials and return a JWT token.
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequestDto userDto) {

        //Validate the login credentials
        User user = userService.retrieveUserByName(userDto.getUsername());

        //Check whether the user exists
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }

        // Validate the password
        else if (!user.getPassword().equals(userDto.getPassword())) {
            throw new UserLoginCredentialsInvalidException("Invalid login credentials!");
        }
        // If everything is valid
        return ResponseEntity.ok(new UserResponse("Login successful!"));
    }

    // secure generator
    private String generateToken(String username, String password) {
        // Define a secure secret key (using HMAC SHA-256)
        byte[] secretKeyBytes = new byte[32];  // 32 bytes = 256 bits for HS256
        new java.security.SecureRandom().nextBytes(secretKeyBytes);
        Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);  // Using Keys.hmacShaKeyFor for HS256 algorithm

        // Set token expiration (e.g., 1 minute from now)
        long expirationTime = 60000; // 60,000 ms = 1 minute

        // Create JWT token using claims and signing with the secure key
        return Jwts.builder()
                .claim("username", username) // Add username as a claim
                .claim("password", password) // Add password as a claim (optional, not recommended in production)
                .claim("iat", new Date()) // Set issued at (iat) claim
                .claim("exp", new Date(System.currentTimeMillis() + expirationTime)) // Set expiration (exp) claim
                .signWith(secretKey)  // Sign the token with the secure key
                .compact();
    }

}
