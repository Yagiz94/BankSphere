package com.example.bankSphere.dto;

import com.example.bankSphere.enums.KYC_STATUS;
import com.example.bankSphere.enums.ROLE;

public class UserDto {
    private String username;
    private String email;
    private ROLE role;
    private String phone;
    private String password;
    private KYC_STATUS kycStatus;

    public UserDto() {}

    public String getUsername() { return username; }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public ROLE getRole() {
        return role;
    }

    public KYC_STATUS getKycStatus() {
        return kycStatus;
    }
}
