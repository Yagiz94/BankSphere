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

    //TODO: Implement the method
//    public Account getAccountByID(Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User id not found"));
//        return accountRepository.findByUser(user)
//                .orElseThrow(() -> new RuntimeException("Account not found"));
//    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account retrieveAccountByIdForTransaction(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new UserAccountNotFoundException("Account not found for processing current transaction."));
    }

    public Transaction withdraw(Transaction transaction, Account account) {
        return transactionService.withdraw(transaction, account);
    }

    public Transaction deposit(Transaction transaction, Account account) {
        return transactionService.deposit(transaction, account);
    }
}
