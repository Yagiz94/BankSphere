// controller/AdminController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.dto.TransactionDto;
import com.example.bankSphere.dto.UserResponseDto;
import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.service.AccountService;
import com.example.bankSphere.service.AdminService;
import com.example.bankSphere.service.TransactionService;
import com.example.bankSphere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

            // Return the created account with the initial deposit
            return ResponseEntity.ok("A new " + createdAccount.getAccountType() + " account has been created successfully.");

        } catch (Exception e) {
            // Log the error and return a generic error response without a message
            e.printStackTrace(); // Optional: print the stack trace for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/accounts/{id}/transactions")
//    public ResponseEntity<?> getAccountTransactions(@PathVariable Long id) {
//        try {
//            // Retrieve all transactions associated with the account
//            return ResponseEntity.ok(accountService.getAllTransactions(id));
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

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
