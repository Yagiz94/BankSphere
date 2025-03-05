// entity/User.java
package com.example.bankSphere.entity;

import com.example.bankSphere.enums.KYC_STATUS;
import com.example.bankSphere.enums.ROLE;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phoneNumber", nullable = false, unique = true)
    private String phoneNumber;

    // For simplicity, using a string role; you could also use a separate Role entity
    @Column(name = "role", nullable = false)
    private ROLE role;

    // KYC status: PENDING, VERIFIED, or REJECTED
    @Column(name = "kycStatus", nullable = false)
    private KYC_STATUS kycStatus;

    public User(String username, String email, String password, String phoneNumber, ROLE role, KYC_STATUS kycStatus) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.kycStatus = kycStatus;
    }

    public User() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ROLE getRole() {
        return role;
    }
    public void setRole(ROLE role) { this.role = role; }


    public void setKycStatus(KYC_STATUS kycStatus) {
        this.kycStatus.setValue(kycStatus.getValue());
    }

    public KYC_STATUS getKycStatus() {
        return kycStatus;
    }


}
