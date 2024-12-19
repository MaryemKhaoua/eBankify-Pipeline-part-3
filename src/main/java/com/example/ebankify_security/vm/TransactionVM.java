package com.example.ebankify_security.vm;

import com.example.ebankify_security.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionVM {
    private TransactionDto transactionDto;
    private String message;
    private int statusCode;
    private List<TransactionDto> transactions;
}
