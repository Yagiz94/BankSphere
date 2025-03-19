// service/AccountService.java
package com.example.bankSphere.service;

import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.exception.UserAccountNotFoundException;
import com.example.bankSphere.repository.AccountRepository;
import com.example.bankSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Method to retrieve account by id and userId
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account getAccountByUserId(Long userId) {
        // Find the user by ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User id not found"));

        // Find the account associated with the user
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found for the given user"));
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account retrieveAccountByIdForTransaction(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new UserAccountNotFoundException("Account not found for processing current transaction."));
    }

    public Transaction withdraw(Transaction transaction) {
        return transactionService.withdraw(transaction);
    }

    public Transaction deposit(Transaction transaction) {
        return transactionService.deposit(transaction);
    }
}
