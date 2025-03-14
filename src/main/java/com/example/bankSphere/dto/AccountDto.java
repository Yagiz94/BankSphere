package com.example.bankSphere.dto;

import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.enums.ACCOUNT_TYPE;

import java.math.BigDecimal;
import java.util.List;

public class AccountDto {
    private BigDecimal balance;

    private ACCOUNT_TYPE accountType;

    private List<Transaction> transactions;

    // Default constructor for JPA
    public AccountDto() {
    }

    // Getters and Setters
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