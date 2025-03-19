package com.example.bankSphere.dto;

import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.enums.ACCOUNT_TYPE;

import java.math.BigDecimal;
import java.util.List;

public class AccountDto {

    private Long userId; // Add userId to associate with the account
    private BigDecimal balance;
    private ACCOUNT_TYPE accountType;
    private List<Transaction> transactions; // Can still be included if necessary

    // Default constructor for JPA
    public AccountDto() {
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    public void setAccountType(ACCOUNT_TYPE accountType) {
        this.accountType = accountType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
