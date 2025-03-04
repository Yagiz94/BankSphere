// entity/Transaction.java
package com.example.bankapp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal amount;
    private String type; // e.g., "TRANSFER", "BILL_PAYMENT"
    private LocalDateTime timestamp;

    // Status: SUCCESS, PENDING, FAILED, etc.
    private String status;

    // Getters and Setters
    // ...
}
