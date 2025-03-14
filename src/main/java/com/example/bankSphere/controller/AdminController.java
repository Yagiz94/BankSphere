// controller/AdminController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.AccountDto;
import com.example.bankSphere.dto.UserResponseDto;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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
}
