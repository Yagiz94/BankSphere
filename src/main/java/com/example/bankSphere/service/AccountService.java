// service/AccountService.java
package com.example.bankSphere.service;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.mapper.AccountMapper;
import com.example.bankSphere.repository.AccountRepository;
import com.example.bankSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    public Account getAccountByID(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User id not found"));
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

    public List<AccountDto> getAllAccountDtos() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountMapper::toDto)
                .collect(Collectors.toList());
    }

}
