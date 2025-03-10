// controller/AuthController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.entity.UserLogger;
import com.example.bankSphere.entity.UserResponse;
import com.example.bankSphere.exception.UserAlreadyExistsException;
import com.example.bankSphere.exception.UserFieldsMissingException;
import com.example.bankSphere.exception.UserLoginCredentialsInvalidException;
import com.example.bankSphere.exception.UserNotFoundException;
import com.example.bankSphere.service.UserLoggerService;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserLoggerService userLoggerService;

    private static final String SECRET_KEY = "your-secret-key";

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {


        // check if the request body is missing
        if (userDto == null) {
            return ResponseEntity.badRequest().body("Missing request body!");
        }

        // check if the request body contains missing fields
        String missingFields = getMissingFields(userDto);
        if (!missingFields.isEmpty()) {
            throw new UserFieldsMissingException("Missing fields: " + missingFields);
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
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        return ResponseEntity.ok("{\n" +
                "\t\"message\": \"User registered successfully!\"" + "\n" +
                "\t\"token\": \"" + tokenizer() + "\"\n}");
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
    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserDto userDto) {
        try {
            String token = tokenizer();
            //TODO validate token
            //TODO validate user login info
            User user = userService.retrieveUserByName(userDto.getUsername());
            if(user == null){
                throw new UserNotFoundException("User not found!");
            }
            else if(!user.getPassword().equals(userDto.getPassword())){
                throw new UserLoginCredentialsInvalidException("Invalid login credentials!");
            }
            else {
                return ResponseEntity.ok(new UserResponse("Login successful!"));
            }

        } catch (Exception e) {
            throw new UserLoginCredentialsInvalidException("Invalid login credentials!");
        }
    }

    private String tokenizer() {
        // 1. Create Header
        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(
                "{\"alg\":\"HS256\",\"typ\":\"JWT\"}".getBytes());

        // 2. Create Payload
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(
                ("{\"sub\":\"testUser\",\"role\":\"USER\",\"exp\":" + (System.currentTimeMillis() / 1000 + 600) + "}").getBytes()
        );

        // 3. Combine Header and Payload
        String unsignedToken = header + "." + payload;

        // 4. Generate Signature (Fake HMAC Implementation Without Dependencies)
        String signature = simpleHmacSha256(unsignedToken, SECRET_KEY);

        // 5. Combine All Parts of the JWT and return JWT token
        return unsignedToken + "." + signature;

    }

    private static String simpleHmacSha256(String data, String key) {
        StringBuilder sb = new StringBuilder();
        // Simplistic "hashing" algorithm: XOR each character of data with the key
        for (int i = 0; i < data.length(); i++) {
            char hashedChar = (char) (data.charAt(i) ^ key.charAt(i % key.length()));
            sb.append(hashedChar);
        }

        // Encode the result in Base64
        return Base64.getUrlEncoder().withoutPadding().encodeToString(sb.toString().getBytes());
    }
}
