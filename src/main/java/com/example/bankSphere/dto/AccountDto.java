package com.example.bankSphere.dto;

import java.math.BigDecimal;

public class AccountDto {
    private Long id;
    private BigDecimal balance;
    private UserModelDto user;

    public AccountDto() {}

    public AccountDto(Long id, BigDecimal balance, UserModelDto user) {
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UserModelDto getUser() {
        return user;
    }

    public void setUser(UserModelDto user) {
        this.user = user;
    }
}