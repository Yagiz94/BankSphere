// service/UserService.java
package com.example.bankSphere.service;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.enums.KYC_STATUS;
import com.example.bankSphere.repository.UserLoggerRepository;
import com.example.bankSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KycService kycService;

    @Autowired
    private UserLoggerRepository userLoggerRepository;

    // Register a new user
    public User registerUser(UserDto userDto) {

        // Define a User with userDto data
        User user = new User();

        // Set user attributes
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhone());
        user.setRole(userDto.getRole());

        // Call external KYC service to verify user documents
        boolean isKycVerified = kycService.verifyUser(userDto);
        user.setKycStatus(isKycVerified ? KYC_STATUS.VERIFIED : KYC_STATUS.REJECTED);

        // Verify if the user already exists before registering a new user
        if (checkUserExists(user)) {
            throw new RuntimeException("The user already exists");
        }

        // Register the user
        return userRepository.save(user);
    }

    private Boolean checkUserExists(User user) {
        return retrieveUserByName(user.getUsername())!=null;
    }

    // Retrieve a User by name
    public User retrieveUserByName(String username) {
        return userRepository.findByUsername(username);
    }

}