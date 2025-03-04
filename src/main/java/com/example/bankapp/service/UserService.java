// service/UserService.java
package com.example.bankapp.service;

import com.example.bankapp.dto.UserDto;
import com.example.bankapp.entity.User;
import com.example.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KycService kycService;

    public User registerUser(UserDto userDto) {
        // Check if email/username already exists (omitted for brevity)
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhone(userDto.getPhone());
        user.setRole("USER");
        user.setKycStatus("PENDING");

        // Call external KYC service to verify user documents
        boolean isKycVerified = kycService.verifyUser(userDto);
        user.setKycStatus(isKycVerified ? "VERIFIED" : "REJECTED");

        return userRepository.save(user);
    }

    // Additional methods (e.g. getUserByUsername, updateProfile)...
}