// controller/TransactionController.java
package com.example.bankSphere.controller;

import com.example.bankSphere.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/list")
    public ResponseEntity<String> getAllTransactions() {
        System.out.println("Received request!");
        return ResponseEntity.ok("API is working fine.");
    }
}
