// service/TransactionService.java
package com.example.bankSphere.service;

import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.exception.InsufficientFundException;
import com.example.bankSphere.repository.AccountRepository;
import com.example.bankSphere.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    /* TODO Rabbit Message query sample that can be used in the project
    @Autowired
    private RabbitTemplate rabbitTemplate;  // Used for event-driven messaging
    */

    public Transaction withdraw(Transaction transaction, Account account) {
        // check balance for a transfer
        if (account.getBalance().compareTo(transaction.getAmount()) < 0) {
            throw new InsufficientFundException("Insufficient balance amount to withdraw from account");
        }

        // Deduct amount and update balance (transactional handling recommended)
        account.setBalance(account.getBalance().subtract(transaction.getAmount()));
        accountRepository.save(account);

        // Update transaction status and save
        transaction.setStatus("SUCCESS");
        transaction = transactionRepository.save(transaction);

        // Publish transaction event to messaging system (e.g., RabbitMQ)
        //TODO rabbitTemplate.convertAndSend("transaction.exchange", "transaction.routing", transaction);

        return transaction;
    }

    @Transactional
    public Transaction deposit(Transaction transaction, Account account) {
        // Check if deposit amount is positive
        if (transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero.");
        }

        // Add amount to the account balance and update it
        account.setBalance(account.getBalance().add(transaction.getAmount()));

        // Save updated account
        accountRepository.save(account);

        // Set transaction status to "SUCCESS"
        transaction.setStatus("SUCCESS");

        // Save the transaction
        transaction = transactionRepository.save(transaction);

        // Optionally, publish the transaction event to a messaging system (e.g., RabbitMQ)
        // TODO: rabbitTemplate.convertAndSend("transaction.exchange", "transaction.routing", transaction);

        // Return the updated transaction
        return transaction;
    }
}
