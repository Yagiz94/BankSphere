package com.example.bankSphere.dto;

import com.example.bankSphere.enums.KYC_STATUS;
import com.example.bankSphere.enums.ROLE;

public class UserDto {
    private String username;
    private String email;

    private ROLE role;

    private KYC_STATUS kycStatus;

    public UserDto(String username, String email) {
        this.username = username;
        this.email = email;
        this.role = ROLE.PERSONAL_USER;
        this.kycStatus = KYC_STATUS.PENDING;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public CharSequence getPassword() {
        return null;
    }

    public String getPhone() {
        return null;
    }

    public ROLE getRole() {
        return role;
    }

    public KYC_STATUS getKycStatus() {
        return kycStatus;
    }
}
