package com.example.ebankify_security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoanDto {
    private Long id;
    private double principal;
    private double interestRate;
    private int termMonths;
    private boolean approved;
    private UserDto user;
}
