// service/AdminService.java
package com.example.bankSphere.service;

import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.entity.User;
import com.example.bankSphere.exception.UserNotFoundException;
import com.example.bankSphere.repository.TransactionRepository;
import com.example.bankSphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Additional administrative functions (user management, logs, etc.)
    public void deleteUser(Long userId) {
        // Implement the logic to delete a user

        if (userRepository.existsById(String.valueOf(userId))) {
            userRepository.deleteById(String.valueOf(userId));
        } else {
            throw new UserNotFoundException("User not found, delete is not successful");
        }
    }
}
