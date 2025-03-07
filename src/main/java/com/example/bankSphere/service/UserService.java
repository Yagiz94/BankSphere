// service/UserService.java
package com.example.bankSphere.service;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.enums.KYC_STATUS;
import com.example.bankSphere.enums.ROLE;
import com.example.bankSphere.repository.UserRepository;
import com.example.bankSphere.repository.UserLoggerRepository;
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

    public User registerUser(UserDto userDto) {
        // Define a User with userDto data
        User user = new User();

        System.out.println(userDto.getUsername() + "-" + userDto.getEmail() + "-" + userDto.getPhone());

        //TODO check whether username, phone exist --> verify user
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhone());
        user.setRole(userDto.getRole());

        // Call external KYC service to verify user documents
        boolean isKycVerified = kycService.verifyUser(userDto);
        user.setKycStatus(isKycVerified ? KYC_STATUS.VERIFIED : KYC_STATUS.REJECTED);

        return userRepository.save(user);
    }
    public User retrieveUser(User user) {
        UserLoggerRepository logger = null;
        return userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User id not found"));
    }

    // Additional methods (e.g. getUserByUsername, updateProfile)...
}