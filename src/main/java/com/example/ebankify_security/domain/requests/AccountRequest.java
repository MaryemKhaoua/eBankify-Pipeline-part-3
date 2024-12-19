package com.example.ebankify_security.domain.requests;

import com.example.ebankify_security.domain.entities.Bank;
import com.example.ebankify_security.domain.enums.AccountStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountRequest {
    @NotNull(message = "Balance cannot be null")
    @Min(value = 0, message = "Balance must be positive")
    private double balance;

    @NotNull(message = "Account number cannot be null")
    private String accountNumber;

    @NotNull(message = "Account status cannot be null")
    private AccountStatus status;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Bank ID cannot be null")
    private Long bankId;
}