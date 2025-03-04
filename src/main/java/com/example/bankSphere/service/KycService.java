// service/KycService.java
package com.example.bankSphere.service;

import com.example.bankSphere.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class KycService {
    public boolean verifyUser(UserDto userDto) {
        // Call to external KYC API (simulate verification)
        // For now, assume verification always passes
        return true;
    }
}
