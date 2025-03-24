package com.example.bankSphere.service;

import com.example.bankSphere.entity.UserSecretKey;
import com.example.bankSphere.repository.UserSecretKeyRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Service
public class UserSecretKeyService {

    @Autowired
    private UserSecretKeyRepository userSecretKeyRepository;

    // Generate and save the secret key for the user
    public String generateAndStoreSecretKey(String username, long expirationTime) {
        // Generate a new secret key (HMAC SHA-256)
        byte[] secretKeyBytes = new byte[32];  // 32 bytes = 256 bits for HS256
        new java.security.SecureRandom().nextBytes(secretKeyBytes);
        Keys.hmacShaKeyFor(secretKeyBytes);  // Using Keys.hmacShaKeyFor for HS256 algorithm

        // Save the secret key and expiration time in the database
        saveSecretKey(username, secretKeyBytes, expirationTime);

        // Return the secret key (as a base64 encoded string) to be used for signing the JWT
        return Base64.getEncoder().encodeToString(secretKeyBytes);
    }

    // Save the secret key in the database
    private void saveSecretKey(String username, byte[] secretKeyBytes, long expirationTime) {
        UserSecretKey userSecretKey = new UserSecretKey();
        userSecretKey.setUsername(username);
        userSecretKey.setSecretKey(Base64.getEncoder().encodeToString(secretKeyBytes)); // Store as base64 string
        userSecretKey.setExpirationTime(new Date(System.currentTimeMillis() + expirationTime));

        userSecretKeyRepository.save(userSecretKey);
    }

    // Method to retrieve the secret key for a user by username
    public String retrieveSecretKey(String username) {
        // Find the user secret key by username
        Optional<UserSecretKey> secretKey = userSecretKeyRepository.findByUsername(username);

        // Ensure the secret key is not expired
        return secretKey.filter(key -> key.getExpirationTime().after(new Date()))  // Check expiration
                .map(UserSecretKey::getSecretKey)
                .orElse(null);
    }

}
