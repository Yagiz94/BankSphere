// service/AdminService.java
package com.example.bankSphere.service;

import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.repository.TransactionRepository;
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
