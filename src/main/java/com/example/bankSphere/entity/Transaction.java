// entity/Transaction.java
package com.example.bankSphere.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TransactionID;

    // Reference to the Account
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private BigDecimal amount;
    private String type; // e.g., "TRANSFER", "BILL_PAYMENT"
    private LocalDateTime timestamp;

    // Status: SUCCESS, PENDING, FAILED, etc.
    private String status;

    public void setAccount(Account account) {

    }

    public void setAmount(Object amount) {

    }

    public void setType(Object type) {

    }

    public void setTimestamp(LocalDateTime now) {

    }

    public void setStatus(String success) {

    }

    // Getters and Setters
    // ...
}
