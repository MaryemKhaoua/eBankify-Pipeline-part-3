package com.example.ebankify_security.dto;

import com.example.ebankify_security.domain.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private double balance;
    private String accountNumber;
    private AccountStatus status;
    private UserDto user;
    private long userId;
    private BankDto bank;
}
