// entity/Transaction.java
package com.example.bankSphere.entity;

import com.example.bankSphere.enums.TRANSACTION_TYPE;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionID;

    private BigDecimal amount;
    private TRANSACTION_TYPE type; // e.g., "TRANSFER", "BILL_PAYMENT"
    private LocalDateTime timestamp;

    // Status: SUCCESS, PENDING, FAILED, etc.
    private String status;

    // Getters and Setters
    public Long getTransactionID() {
        return transactionID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public TRANSACTION_TYPE getType() {
        return type;
    }

    public void setType(int type) {
        this.type = TRANSACTION_TYPE.values()[type];
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                ", timestamp=" + timestamp +
                ", status='" + status + '\'' +
                '}';
    }
}
