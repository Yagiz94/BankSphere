// controller/AccountController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.TransactionDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    //TODO Implement the method
//    @GetMapping("/user/{userId}/account")
//    public ResponseEntity<Account> getAccountDetails(@PathVariable Long userId) {
//        Account account = accountService.getAccountByID(userId);
//        return ResponseEntity.ok(account);
//    }

    @PostMapping("/user/account/{accountId}/transaction/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId) {
        // Validate and retrieve account object for processing transaction
        Account account = accountService.retrieveAccountByIdForTransaction(accountId);
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType().getValue());
        transaction.setTimestamp(transactionDto.getTimestamp());
        transaction.setStatus(transactionDto.getStatus());
        return ResponseEntity.ok(accountService.withdraw(transaction, account));
    }

    @PostMapping("/user/account/{accountId}/transaction/deposit")
    public ResponseEntity<Transaction> deposit(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId) {
        // Validate and retrieve account object for processing transaction
        Account account = accountService.retrieveAccountByIdForTransaction(accountId);
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType().getValue());
        transaction.setTimestamp(transactionDto.getTimestamp());
        transaction.setStatus(transactionDto.getStatus());
        return ResponseEntity.ok(accountService.deposit(transaction, account));
    }
}
