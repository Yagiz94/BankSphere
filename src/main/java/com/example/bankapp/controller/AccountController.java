// controller/AccountController.java
package com.example.bankapp.controller;

import com.example.bankapp.entity.Account;
import com.example.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{username}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable String username) {
        Account account = accountService.getAccountByUsername(username);
        return ResponseEntity.ok(account);
    }
}
