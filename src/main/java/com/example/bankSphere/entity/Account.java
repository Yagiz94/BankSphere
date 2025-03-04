package com.example.bankSphere.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long AccountID;

    // Association to a User
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Assuming 'CustomerID' is the column name in the database
    private User user;

    private BigDecimal balance;

    // Default constructor for JPA
    public Account() {}

    // Constructor with parameters for easier creation
    public Account(User user, BigDecimal balance) {
        this.user = user;
        this.balance = balance;
    }

    // Getters and Setters
    public long getId() {
        return AccountID;
    }

    public void setId(long id) {
        this.AccountID = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{id=" + AccountID + ", user=" + user + ", balance=" + balance + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return AccountID == account.AccountID;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(AccountID);
    }
}
