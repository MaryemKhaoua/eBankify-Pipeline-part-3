package com.example.ebankify_security.domain.requests;

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
public class LoanRequest {

    @NotNull(message = "Principal amount cannot be null")
    @Min(value = 0, message = "Principal amount cannot be negative")
    private double principal;

    @NotNull(message = "Interest rate cannot be null")
    @Min(value = 0, message = "Interest rate cannot be negative")
    private double interestRate;

    @NotNull(message = "Loan term must be specified")
    @Min(value = 1, message = "Loan term must be at least 1 month")
    private int termMonths;

    private boolean approved;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}
