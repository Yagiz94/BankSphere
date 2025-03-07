// controller/AccountController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.entity.Account;
import com.example.bankSphere.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{userId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long userId) {
        Account account = accountService.getAccountByID(userId);
        return ResponseEntity.ok(account);
    }
}
