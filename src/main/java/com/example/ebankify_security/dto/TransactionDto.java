package com.example.ebankify_security.dto;


import com.example.ebankify_security.domain.enums.TransactionStatus;
import com.example.ebankify_security.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private TransactionType type;
    private double amount;
    private TransactionStatus status;
    private AccountDto sourceAccount;
    private AccountDto destinationAccount;
}