// service/AccountService.java
package com.example.bankapp.service;

import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.User;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account getAccountByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public Account createAccountForUser(User user) {
        Account account = new Account();
        account.setUser(user);
        // Initial balance
        account.setBalance(BigDecimal.ZERO);
        return accountRepository.save(account);
    }
}
