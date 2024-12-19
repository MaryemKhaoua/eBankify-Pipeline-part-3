package com.example.ebankify_security.domain.response;

import com.example.ebankify_security.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private AccountDto account;
    private String message;
    private int statusCode;
    private List<AccountDto> accounts;
}
