package com.example.ebankify_security.controller;

import com.example.ebankify_security.dto.LoanDto;
import com.example.ebankify_security.domain.requests.LoanRequest;
import com.example.ebankify_security.vm.LoanVM;
import com.example.ebankify_security.service.LoanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@CrossOrigin(origins = "http://localhost:8080")
@AllArgsConstructor
public class LoanController {
    private LoanService loanService;
    @PostMapping("/save")
    public ResponseEntity<LoanVM> saveLoan(@Valid @RequestBody LoanRequest loanRequest){
        LoanDto loanDto = loanService.saveLoan(loanRequest);
        LoanVM loanVM = LoanVM.builder()
                .loan(loanDto)
                .message("Loan created successfully")
                .statusCode(201)
                .build();
        return ResponseEntity.ok(loanVM);
    }
    @GetMapping("/{id}")
    public ResponseEntity<LoanVM> getLoanById(@PathVariable Long id){
        LoanDto loanDto = loanService.getLoanById(id);
        LoanVM loanVM = LoanVM.builder()
                .loan(loanDto)
                .message("Loan found successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(loanVM);
    }
    @DeleteMapping("/id")
    public ResponseEntity<LoanVM> deleteLoan(@PathVariable Long id){
        loanService.deleteLoan(id);
        LoanVM loanVM = LoanVM.builder()
                .message("Loan deleted successfully")
                .statusCode(204)
                .build();
        return ResponseEntity.ok(loanVM);
    }
    @GetMapping
    public ResponseEntity<LoanVM> getAllLoans(){
        List<LoanDto> loanDtos = loanService.findAll();
        LoanVM loanVM = LoanVM.builder()
                .loans(loanDtos)
                .message("Loans retrieved successfully")
                .statusCode(200)
                .build();
        return ResponseEntity.ok(loanVM);
    }

}
