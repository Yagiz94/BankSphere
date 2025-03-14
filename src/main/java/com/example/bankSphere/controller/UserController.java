package com.example.bankSphere.controller;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    // TODO
    @PostMapping("/{userId}/createAccount")
    public ResponseEntity<Account> createAccount(@RequestBody AccountDto accountDto, @PathVariable Long userId) {
        // Validate user first
        userService.validateUserByIdForAccount(userId);
        Account account = new Account();
        account.setBalance(BigDecimal.ZERO);
        account.setAccountType(accountDto.getAccountType().getValue());
        return ResponseEntity.ok(userService.createAccount(account));
    }
}
