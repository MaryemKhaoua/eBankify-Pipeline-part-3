package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.Account;
import com.example.ebankify_security.domain.entities.Transaction;
import com.example.ebankify_security.domain.enums.TransactionStatus;
import com.example.ebankify_security.domain.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountOrDestinationAccount(Account sourceAccount, Account destinationAccount);
    List<Transaction> findByAmount(double amount);
    List<Transaction> findByType(String type);

    List<Transaction> findByTypeAndStatus(TransactionType transactionType, TransactionStatus transactionStatus);
}