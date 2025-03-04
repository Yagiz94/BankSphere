// controller/TransactionController.java
package com.example.bankapp.controller;

import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transferFunds(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.processTransaction(transactionDto);
        return ResponseEntity.ok(transaction);
    }
}
