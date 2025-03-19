// controller/AdminController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.dto.UserResponseDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.enums.TRANSACTION_TYPE;
import com.example.bankSphere.repository.AccountRepository;
import com.example.bankSphere.repository.UserRepository;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.AdminService;
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
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<UserResponseDto> getAllUsers() {
        return adminService.getAllUsers();
    }

//    @GetMapping("/accounts")
//    public List<AccountDto> getAllAccounts() {
//        return adminService.getAllAccountDtos();
//    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        System.out.println("Loading all transactions...");
        List<Transaction> transactions = adminService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        System.out.println("Deleting user with id: " + id);
        adminService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
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

    @PostMapping("/{userId}/account")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody Account accountDto) {
        try {
            // Ensure the user ID is set in the AccountDto
            accountDto.setUserById(userId, userRepository);

            // Create the account (this will save the account in the database)
            Account createdAccount = accountService.createAccount(accountDto);

            // Initial deposit amount (e.g., $100)
            BigDecimal initialDeposit = new BigDecimal("1000.00");  // Example initial deposit amount

            // Create a new transaction for the deposit
            Transaction depositTransaction = new Transaction();
            depositTransaction.setAmount(initialDeposit);
            depositTransaction.setType(TRANSACTION_TYPE.Deposit.getValue());  // Set transaction type as Deposit
            depositTransaction.setStatus("SUCCESS");  // Set status as SUCCESS
            depositTransaction.setTimestamp(LocalDateTime.now());  // Use current timestamp directly

            // Set the account for the transaction to ensure account_id is populated
            depositTransaction.setAccount(createdAccount);  // Associate the transaction with the created account

            // Create and save the deposit transaction
            accountService.deposit(depositTransaction, createdAccount);

            // Return the created account with the initial deposit
            return ResponseEntity.ok(createdAccount);

        } catch (Exception e) {
            // Log the error and return a generic error response without a message
            e.printStackTrace(); // Optional: print the stack trace for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
