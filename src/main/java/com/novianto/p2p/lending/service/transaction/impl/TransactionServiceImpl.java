package com.novianto.p2p.lending.service.transaction.impl;

import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.Transaction;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.model.enumType.TransactionType;
import com.novianto.p2p.lending.repository.TransactionRepository;
import com.novianto.p2p.lending.service.auth.UserService;
import com.novianto.p2p.lending.service.global.LogService;
import com.novianto.p2p.lending.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final TransactionRepository transactionRepository;
    private final LogService logService;

    @Autowired
    public TransactionServiceImpl(UserService userService, TransactionRepository transactionRepository, LogService logService) {
        this.userService = userService;
        this.transactionRepository = transactionRepository;
        this.logService = logService;
    }

    @Override
    public Transaction createTransaction(Loan loan, User fromUser, User toUser, BigDecimal amount, TransactionType type) {
        userService.updateUserBalance(fromUser, amount.negate());
        userService.updateUserBalance(toUser, amount);

        Transaction transaction = Transaction.builder()
                .loan(loan)
                .fromUser(fromUser)
                .toUser(toUser)
                .amount(amount)
                .transactionDate(LocalDateTime.now())
                .transactionType(type)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        logService.log("INFO", "TransactionService", "Transaksi dibuat: ID=" + savedTransaction.getId() + ", Jenis=" + type, Thread.currentThread().getName());

        return savedTransaction;
    }
}
