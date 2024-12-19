package com.example.ebankify_security.controller;

import com.example.ebankify_security.dto.AccountDto;
import com.example.ebankify_security.domain.requests.AccountRequest;
import com.example.ebankify_security.vm.AccountVM;
import com.example.ebankify_security.service.AccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class AccountController {
    private AccountService accountService;

    @PreAuthorize("@accountSecurity.canCreateAccount(#accountRequest.userId)")

    @PostMapping("/save")
    public ResponseEntity<AccountVM> save(@Valid @RequestBody AccountRequest accountRequest) {
        AccountDto accountDto = accountService.createAccount(accountRequest);
        AccountVM accountVM = AccountVM.builder()
                .account(accountDto)
                .message("Account created successfully")
                .statusCode(201)
                .build();
        return ResponseEntity.status(201).body(accountVM);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AccountVM> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        AccountVM accountVM = AccountVM.builder()
                .account(accountDto)
                .message("Account retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(accountVM);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AccountVM> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountRequest accountRequest) {
        AccountDto accountDto = accountService.updateAccount(id, accountRequest);
        AccountVM accountVM = AccountVM.builder()
                .account(accountDto)
                .message("Account updated successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(accountVM);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountVM> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        AccountVM accountVM = AccountVM.builder()
                .message("Account deleted successfully")
                .statusCode(204)
                .build();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/all")
    public ResponseEntity<AccountVM> getAllAccounts() {
        List< AccountDto> accountDtos = accountService.findAll();
        AccountVM accountVM = AccountVM.builder()
                .accounts(accountDtos)
                .message("All accounts retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(accountVM);
    }

}