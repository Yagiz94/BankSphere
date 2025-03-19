// controller/AdminController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.dto.TransactionDto;
import com.example.bankSphere.dto.UserResponseDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.enums.TRANSACTION_TYPE;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.AdminService;
import com.example.bankSphere.service.TransactionService;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return adminService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        System.out.println("Deleting user with id: " + id);
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/{userId}/account")
    public ResponseEntity<String> createAccount(@PathVariable Long userId, @RequestBody Account account) {
        try {
            // Ensure the user ID is set in the AccountDto
            account.setUserById(userId, userService.getUserRepository());

            // Create the account (this will save the account in the database)
            Account createdAccount = accountService.createAccount(account);

            // Check if balance is provided in the request
            if (account.getBalance() != null && account.getBalance().compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal initialDepositValue = account.getBalance();  // Example initial deposit amount

                // Create a new transaction for the deposit
                Transaction depositTransaction = new Transaction();
                depositTransaction.setAmount(initialDepositValue);
                depositTransaction.setType(TRANSACTION_TYPE.Deposit.getValue());  // Set transaction type as Deposit
                depositTransaction.setStatus("SUCCESS");  // Set status as SUCCESS
                depositTransaction.setTimestamp(LocalDateTime.now());  // Use current timestamp directly

                // Set the account for the transaction to ensure account_id is populated
                depositTransaction.setAccount(createdAccount);  // Associate the transaction with the created account

                // Create and save the deposit transaction
                accountService.deposit(depositTransaction);
            }

            // Return the created account with the initial deposit
            return ResponseEntity.ok("A new " + createdAccount.getAccountType() + " account has been created successfully.");

        } catch (Exception e) {
            // Log the error and return a generic error response without a message
            e.printStackTrace(); // Optional: print the stack trace for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<AccountDto> getAllTransactions(@PathVariable Long id) {
        List<Transaction> transactions = transactionService.getAllTransactions(id);
        AccountDto accountDto = new AccountDto();

        List<TransactionDto> transactionDtos = transactions.stream().map(transaction -> {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAmount(transaction.getAmount());
            transactionDto.setType(transaction.getType().getValue());
            transactionDto.setStatus(transaction.getStatus());
            transactionDto.setTimestamp(transaction.getTimestamp());
            transactionDto.setAccount(accountDto);
            return transactionDto;
        }).toList();
        accountDto.setTransactions(transactionDtos);
        accountDto.setUserId(accountService.getAccountById(id).getUser().getId());
        accountDto.setBalance(accountService.getAccountById(id).getBalance());
        accountDto.setAccountType(accountService.getAccountById(id).getAccountType());
        accountDto.setId(id);

       return ResponseEntity.ok(accountDto);

    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        System.out.println("Deleting account with id: " + id);
        adminService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @DeleteMapping("/transactions/{id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
        System.out.println("Deleting user with id: " + id);
        adminService.deleteTransaction(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
