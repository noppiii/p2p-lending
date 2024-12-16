package com.novianto.p2p.lending.service.transaction;

import com.novianto.p2p.lending.model.Loan;
import com.novianto.p2p.lending.model.Transaction;
import com.novianto.p2p.lending.model.User;
import com.novianto.p2p.lending.model.enumType.TransactionType;

import java.math.BigDecimal;

public interface TransactionService {

    Transaction createTransaction(Loan loan, User fromUser, User toUser, BigDecimal amount, TransactionType type);
}
