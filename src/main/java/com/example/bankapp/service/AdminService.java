// service/AdminService.java
package com.example.bankapp.service;

import com.example.bankapp.entity.Transaction;
import com.example.bankapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Additional administrative functions (user management, logs, etc.)
}
