// entity/Account.java
package com.example.bankapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Association to a User
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal balance;

    public void setUser(User user) {
    }

    // Additional fields (account type, currency, etc.)
    // Getters and Setters
    // ...
}
