package com.example.ebankify_security.vm;

import com.example.ebankify_security.dto.LoanDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanVM {
    private LoanDto loan;
    private String message;
    private int statusCode;
    private List<LoanDto> loans;
}
