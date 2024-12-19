package com.example.ebankify_security.service;

import com.example.ebankify_security.dto.TransactionDto;
import com.example.ebankify_security.domain.entities.Account;
import com.example.ebankify_security.domain.entities.Transaction;
import com.example.ebankify_security.domain.enums.TransactionStatus;
import com.example.ebankify_security.domain.enums.TransactionType;
import com.example.ebankify_security.domain.requests.TransactionRequest;
import com.example.ebankify_security.exeption.*;
import com.example.ebankify_security.mapper.*;
import com.example.ebankify_security.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private AccountMapper accountMapper;

    public TransactionDto saveTransaction(TransactionRequest transactionRequest) {
        System.out.println(transactionRequest);
        Account sourceAccount = accountRepository.findById(transactionRequest.getSourceAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
        Account destinationAccount = accountRepository.findById(transactionRequest.getDestinationAccountId())
                .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

        if (sourceAccount.getBalance() < transactionRequest.getAmount()) {
            throw new InsufficientFundsException("Insufficient funds in the source account");
        }

        double transferLimit = 10000;
        if (transactionRequest.getAmount() > transferLimit) {
            throw new LimitExceededException("The amount exceeds the allowed transfer limit of " + transferLimit + " DH");
        }

        boolean isCrossBank = !sourceAccount.getBank().equals(destinationAccount.getBank());

        double transactionFee = calculateTransactionFee(transactionRequest.getType(), transactionRequest.getAmount(), isCrossBank);
        double totalAmount = transactionRequest.getAmount() + transactionFee;

        if (TransactionType.SCHEDULED.equals(transactionRequest.getType()) && transactionRequest.getNextExecutionDate() == null) {
            throw new IllegalArgumentException("The next execution date is required for a scheduled transaction");
        }

        Transaction transaction = Transaction.builder()
                .type(transactionRequest.getType())
                .amount(transactionRequest.getAmount())
                .status(TransactionStatus.PENDING)
                .sourceAccount(sourceAccount)
                .destinationAccount(destinationAccount)
                .nextExecutionDate(transactionRequest.getNextExecutionDate())
                .build();

        sourceAccount.setBalance(sourceAccount.getBalance() - totalAmount);
        destinationAccount.setBalance(destinationAccount.getBalance() + transactionRequest.getAmount());

        transactionRepository.save(transaction);
        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);

        return transactionMapper.toDto(transaction);
    }

    private double calculateTransactionFee(TransactionType type, double amount, boolean isCrossBank) {
        double baseFeeRate = (type == TransactionType.STANDARD) ? 0.01 : 0.02;
        double crossBankAdditionalFee = isCrossBank ? 0.005 : 0.0;
        double totalFeeRate = baseFeeRate + crossBankAdditionalFee;
        return amount * totalFeeRate;
    }

    public TransactionDto getTransactionById(Long transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        TransactionDto transactionDto = transactionMapper.toDto(transaction);
        transactionDto.setSourceAccount(accountMapper.toDto(transaction.getSourceAccount()));
        transactionDto.setDestinationAccount(accountMapper.toDto(transaction.getDestinationAccount()));

        return transactionDto;
    }

    public TransactionDto updateTransaction(Long transactionId, TransactionRequest transactionRequest) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));
        if (transactionRequest.getStatus() != null) {
            transaction.setStatus(transactionRequest.getStatus());
        }
        if (transactionRequest.getAmount() > 0) {
            Account sourceAccount = accountRepository.findById(transactionRequest.getSourceAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Source account not found"));
            Account destinationAccount = accountRepository.findById(transactionRequest.getDestinationAccountId())
                    .orElseThrow(() -> new AccountNotFoundException("Destination account not found"));

            boolean isCrossBank = !sourceAccount.getBank().equals(destinationAccount.getBank());

            double transactionFee = calculateTransactionFee(transaction.getType(), transactionRequest.getAmount(), isCrossBank);
            double totalAmount = transactionRequest.getAmount() + transactionFee;
            double transferLimit = 10000;
            if (transactionRequest.getAmount() > transferLimit) {
                throw new LimitExceededException("The amount exceeds the allowed transfer limit of " + transferLimit + " DH");
            }

            if (sourceAccount.getBalance() < totalAmount) {
                throw new InsufficientFundsException("Insufficient funds in the source account for the update");
            }

            sourceAccount.setBalance(sourceAccount.getBalance() - totalAmount);
            destinationAccount.setBalance(destinationAccount.getBalance() + transactionRequest.getAmount());

            transaction.setAmount(transactionRequest.getAmount());
            transaction.setSourceAccount(sourceAccount);
            transaction.setDestinationAccount(destinationAccount);

            accountRepository.save(sourceAccount);
            accountRepository.save(destinationAccount);
        }
        transactionRepository.save(transaction);
        return transactionMapper.toDto(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toDtoList(transactions);
    }

    public boolean cancelTransaction(Long transactionId) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (!transactionOptional.isPresent()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        Transaction transaction = transactionOptional.get();
        if (transaction.getStatus() == TransactionStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a transaction that is already completed");
        }
        transaction.setStatus(TransactionStatus.REJECTED);
        transactionRepository.save(transaction);
        return true;
    }

    public boolean acceptTransaction(Long transactionId) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(transactionId);
        if (!transactionOptional.isPresent()) {
            throw new TransactionNotFoundException("Transaction not found");
        }
        Transaction transaction = transactionOptional.get();
        if (transaction.getStatus() != TransactionStatus.PENDING) {
            throw new IllegalStateException("Cannot accept a transaction that is already validated");
        }
        transaction.setStatus(TransactionStatus.COMPLETED);
        transactionRepository.save(transaction);
        return true;
    }

    public List<TransactionDto> getTransactionHistoryByAccountId(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        List<Transaction> transactions = transactionRepository.findBySourceAccountOrDestinationAccount(account, account);

        return transactions.stream()
                .map(transactionMapper::toDto)
                .toList();
    }

    public List<TransactionDto> searchTransactionsByAmount(double amount) {
        List<Transaction> transactions = transactionRepository.findByAmount(amount);
        return transactionMapper.toDtoList(transactions);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void executeScheduledTransactions() {
        List<Transaction> scheduledTransactions = transactionRepository.findByTypeAndStatus(
                TransactionType.SCHEDULED, TransactionStatus.PENDING
        );

        for (Transaction transaction : scheduledTransactions) {
            if (transaction.getNextExecutionDate() != null &&
                    transaction.getNextExecutionDate().isEqual(LocalDate.now())) {

                Account sourceAccount = transaction.getSourceAccount();
                Account destinationAccount = transaction.getDestinationAccount();
                double totalAmount = transaction.getAmount() + calculateTransactionFee(transaction.getType(), transaction.getAmount(), !sourceAccount.getBank().equals(destinationAccount.getBank()));

                if (sourceAccount.getBalance() >= totalAmount) {
                    sourceAccount.setBalance(sourceAccount.getBalance() - totalAmount);
                    destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
                    transaction.setStatus(TransactionStatus.COMPLETED);

                    transaction.setNextExecutionDate(transaction.getNextExecutionDate().plusMonths(1));

                    accountRepository.save(sourceAccount);
                    accountRepository.save(destinationAccount);
                    transactionRepository.save(transaction);
                } else {
                    transaction.setStatus(TransactionStatus.REJECTED);
                    transactionRepository.save(transaction);
                }
            }
        }
    }
}