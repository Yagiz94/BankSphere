// entity/User.java
package com.example.bankapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String phone;

    // For simplicity, using a string role; you could also use a separate Role entity
    private String role;

    // KYC status: PENDING, VERIFIED, or REJECTED
    private String kycStatus;

    public String getId() {
        return String.valueOf(Math.random());
    }

    public void setUsername(Object username) {

    }

    public void setEmail(String email) {
    }

    public void setPassword(String encode) {

    }

    public void setPhone(Object phone) {

    }

    public void setRole(String user) {

    }

    public void setKycStatus(String pending) {

    }

    // Getters and Setters
    // ...
}
