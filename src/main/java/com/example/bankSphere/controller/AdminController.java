// controller/AdminController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.dto.UserDto;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }
}
