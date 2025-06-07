package com.obank.app.repositories;

import java.util.List;

import com.obank.app.models.Transaction;

public interface TransactionRepository {
    List<Transaction> findAllTransactionsByUserEmail(String email);

    void createTransactionOperation(Transaction transaction);
}
