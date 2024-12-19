package com.example.ebankify_security.service;

import com.example.ebankify_security.dto.LoanDto;
import com.example.ebankify_security.domain.entities.Loan;
import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.requests.LoanRequest;
import com.example.ebankify_security.exeption.LoanNotFoundException;
import com.example.ebankify_security.exeption.UserNotFoundException;
import com.example.ebankify_security.mapper.LoanMapper;
import com.example.ebankify_security.mapper.UserMapper;
import com.example.ebankify_security.repository.LoanRepository;
import com.example.ebankify_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {
    private LoanRepository loanRepository;
    private LoanMapper loanMapper;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public LoanDto saveLoan(LoanRequest loanRequest) {
        Loan loan = loanMapper.toEntity(loanRequest);
        User user=userRepository.findById(loanRequest.getUserId())
                .orElseThrow(()->new UserNotFoundException("User not found"));
        loan.setUser(user);
        Loan savedLoan = loanRepository.save(loan);
        LoanDto loanDto = loanMapper.toDto(savedLoan);
        loanDto.setUser(userMapper.toDto(user));
        return loanDto;
    }
    public LoanDto getLoanById(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        LoanDto loanDto = loanMapper.toDto(loan);
        User user = userRepository.findById(loan.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        loanDto.setUser(userMapper.toDto(user));
        return loanDto;
    }
    public void deleteLoan(Long loanId) {
        loanRepository.deleteById(loanId);
    }
    public List<LoanDto> findAll(){
        List<Loan> loans = loanRepository.findAllWithUser();
        return loanMapper.toDtoList(loans);
    }
}
