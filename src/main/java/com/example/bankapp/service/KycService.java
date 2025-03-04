// service/KycService.java
package com.example.bankapp.service;

import com.example.bankapp.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class KycService {
    public boolean verifyUser(UserDto userDto) {
        // Call to external KYC API (simulate verification)
        // For now, assume verification always passes
        return true;
    }
}
