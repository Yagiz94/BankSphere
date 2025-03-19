package com.example.bankSphere.service;

import com.example.bankSphere.entity.Account;
import com.example.bankSphere.exception.UserNotFoundException;
import com.example.bankSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    public void validateUserByIdForAccount(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User can't be validated during account creation process"));
    }

    public Account createAccount(Account account) {
        return accountService.createAccount(account);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
