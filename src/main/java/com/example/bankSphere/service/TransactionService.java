// service/TransactionService.java
package com.example.bankSphere.service;

import com.example.bankSphere.entity.Account;
import com.example.bankSphere.entity.Transaction;
import com.example.bankSphere.exception.InsufficientFundException;
import com.example.bankSphere.repository.AccountRepository;
import com.example.bankSphere.repository.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;  // Used for event-driven messaging

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
        rabbitTemplate.convertAndSend("transaction.exchange", "transaction.routing", transaction);

        return transaction;
    }

    public Transaction deposit(Transaction transaction, Account account) {
        // Add amount and update balance (transactional handling recommended)
        account.setBalance(account.getBalance().add(transaction.getAmount()));
        accountRepository.save(account);

        // Update transaction status and save
        transaction.setStatus("SUCCESS");
        transaction = transactionRepository.save(transaction);

        // Publish transaction event to messaging system (e.g., RabbitMQ)
        rabbitTemplate.convertAndSend("transaction.exchange", "transaction.routing", transaction);

        return transaction;
    }
}
