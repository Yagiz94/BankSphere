// service/TransactionService.java
package com.example.bankapp.service;

import com.example.bankapp.dto.TransactionDto;
import com.example.bankapp.entity.Account;
import com.example.bankapp.entity.Transaction;
import com.example.bankapp.repository.AccountRepository;
import com.example.bankapp.repository.TransactionRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;  // Used for event-driven messaging

    public Transaction processTransaction(TransactionDto transactionDto) {
        // Validate accounts, balance, etc.
        Account account = accountRepository.findById(transactionDto.getAccountId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Example: check balance for a transfer
        if (account.getBalance().compareTo(transactionDto.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        // Deduct amount and update balance (transactional handling recommended)
        account.setBalance(account.getBalance().subtract(transactionDto.getAmount()));
        accountRepository.save(account);

        // Create a transaction record
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDto.getAmount());
        transaction.setType(transactionDto.getType());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("SUCCESS");
        transaction = transactionRepository.save(transaction);

        // Publish transaction event to messaging system (e.g., RabbitMQ)
        rabbitTemplate.convertAndSend("transaction.exchange", "transaction.routing", transaction);

        return transaction;
    }
}
