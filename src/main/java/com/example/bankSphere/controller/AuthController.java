// controller/AuthController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserRequestDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.entity.UserResponse;
import com.example.bankSphere.entity.UserSecretKey;
import com.example.bankSphere.exception.UserAlreadyExistsException;
import com.example.bankSphere.exception.UserFieldsMissingException;
import com.example.bankSphere.exception.UserLoginCredentialsInvalidException;
import com.example.bankSphere.exception.UserNotFoundException;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.AuthService;
import com.example.bankSphere.service.UserLoggerService;
import com.example.bankSphere.service.UserSecretKeyService;
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
import java.util.Base64;
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

    @Autowired
    private UserSecretKeyService userSecretKeyService; // Inject the service

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
                "\t\"token\": \"" + generateToken(user.getUsername()) + "\"\n}");

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

    private String generateToken(String username) {
        // Set token expiration (1 hour from now)
        long expirationTime = 3600000; // 1 hour in milliseconds

        // Generate or retrieve the secret key
        String secretKeyBase64 = userSecretKeyService.generateAndStoreSecretKey(username, expirationTime);

        // Decode Base64 secret key
        byte[] secretKeyBytes = Base64.getDecoder().decode(secretKeyBase64);
        Key secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        // Create JWT token using claims and signing with the secure key
        String token = Jwts.builder()
                .setSubject(username)  // Use 'sub' for username
                .claim("username", username)  // Explicitly add 'username' to claims
                .setIssuedAt(new Date())  // Correct way to set 'iat'
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))  // Correct way to set 'exp'
                .signWith(secretKey)  // Sign with user-specific secret key
                .compact();

        // Debug prints
        System.out.println("Generated token: " + token);
        System.out.println("Secret key length: " + secretKeyBytes.length);
        System.out.println("Secret key (Base64): " + secretKeyBase64);

        return token;
    }

}
