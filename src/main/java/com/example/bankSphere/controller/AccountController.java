// controller/AccountController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.TransactionDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/user/{userId}/account")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long userId) {
        try {
            // Retrieve account details by userId
            Account account = accountService.getAccountByUserId(userId);
            return ResponseEntity.ok(account);
        } catch (RuntimeException e) {
            // If account or user is not found, return 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/user/account/{accountId}/transaction/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId) {
        // Simple validation checks for the withdrawal request
        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>("Amount must be greater than zero.", HttpStatus.BAD_REQUEST);
        }
        if (transactionDto.getStatus() == null || transactionDto.getStatus().isEmpty()) {
            return new ResponseEntity<>("Status is required.", HttpStatus.BAD_REQUEST);
        }
        if (transactionDto.getTimestamp() == null) {
            return new ResponseEntity<>("Invalid timestamp format.", HttpStatus.BAD_REQUEST);
        }

        // Retrieve account object for the transaction
        Account account = accountService.retrieveAccountByIdForTransaction(accountId);
        if (account == null) {
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        // Ensure the account has enough balance for the withdrawal
        if (account.getBalance().compareTo(transactionDto.getAmount()) < 0) {
            return new ResponseEntity<>("Insufficient funds.", HttpStatus.BAD_REQUEST);
        }

        // Create the transaction and set necessary fields
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType().getValue());
        transaction.setTimestamp((transactionDto.getTimestamp())); // Ensure correct parsing
        transaction.setStatus(transactionDto.getStatus());

        // IMPORTANT: Set the account for the transaction to ensure account_id is populated
        transaction.setAccount(account);  // Associate the transaction with the account

        try {
            // Process the withdrawal by calling the service method
            accountService.withdraw(transaction);

            // Return the updated account information after withdrawal
            return ResponseEntity.ok("Withdraw operation is successful");
        } catch (Exception e) {
            // Log the exception (use a logger for production)
            e.printStackTrace();
            return new ResponseEntity<>("Error processing the withdrawal.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/user/account/{accountId}/transaction/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionDto transactionDto, @PathVariable Long accountId) {
        // Simple checks
        if (transactionDto.getAmount() == null || transactionDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return new ResponseEntity<>("Amount must be greater than zero.", HttpStatus.BAD_REQUEST);
        }
        if (transactionDto.getStatus() == null || transactionDto.getStatus().isEmpty()) {
            return new ResponseEntity<>("Status is required.", HttpStatus.BAD_REQUEST);
        }
        if (transactionDto.getTimestamp() == null) {
            return new ResponseEntity<>("Invalid timestamp format.", HttpStatus.BAD_REQUEST);
        }

        // Validate and retrieve account object for processing transaction
        Account account = accountService.retrieveAccountByIdForTransaction(accountId);
        if (account == null) {
            return new ResponseEntity<>("Account not found.", HttpStatus.NOT_FOUND);
        }

        // Create transaction and set account
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType().getValue());
        transaction.setTimestamp(transactionDto.getTimestamp());  // Ensure proper conversion
        transaction.setStatus(transactionDto.getStatus());

        // IMPORTANT: Set the account on the transaction
        transaction.setAccount(account);  // Associate the account with the transaction

        try {
            // Perform the deposit
            accountService.deposit(transaction);

            // Return the updated account
            return ResponseEntity.ok("Deposit operation is successful");
        } catch (Exception e) {
            // Log the exception (use a logger for production)
            e.printStackTrace();
            return new ResponseEntity<>("Error processing the deposit.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
