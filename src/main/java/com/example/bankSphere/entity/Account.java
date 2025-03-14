package com.example.bankSphere.entity;

import com.example.bankSphere.enums.ACCOUNT_TYPE;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountID;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "transactions")
    private List<Transaction> transactions;
    @Column(name = "accountType", nullable = false)
    private ACCOUNT_TYPE accountType;

    @Column(name = "balance")
    private BigDecimal balance;

    // Default constructor for JPA
    public Account() {
    }

    // Getters and Setters
    public long getAccountId() {
        return accountID;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setAccountType(int accountType) {
        this.accountType = ACCOUNT_TYPE.values()[accountType];
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public ACCOUNT_TYPE getAccountType() {
        return accountType;
    }

    @Override
    public String toString() {
        return "\"Account\":" + "{" +
                "\n\"accountType\":" + accountType +
                ", \n\"balance\":" + balance +
                ", \n\"transactions\":[\n" + showTransactions(transactions) + "\n]" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountID == account.accountID;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(accountID);
    }

    public StringBuilder showTransactions(List<Transaction> transactions) {
        StringBuilder sb = new StringBuilder();
        for (Transaction transaction : transactions) {
            sb.append(transaction);
        }
        return sb;
    }
}
