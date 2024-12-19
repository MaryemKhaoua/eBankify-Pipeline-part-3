package com.example.ebankify_security.service;

import com.example.ebankify_security.dto.AccountDto;
import com.example.ebankify_security.domain.entities.Account;
import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.requests.AccountRequest;
import com.example.ebankify_security.mapper.AccountMapper;
import com.example.ebankify_security.mapper.UserMapper;
import com.example.ebankify_security.repository.AccountRepository;
import com.example.ebankify_security.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {
    private  AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private UserRepository userRepository;
    private UserMapper userMapper;

    public AccountDto createAccount(AccountRequest accountRequest) {
        Account account = accountMapper.toEntity(accountRequest);
        User user = userRepository.findById(accountRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        account.setUser(user);
        Account savedAccount = accountRepository.save(account);
        AccountDto accountDto = accountMapper.toDto(savedAccount);
        accountDto.setUser(userMapper.toDto(user));
        return accountDto;
    }
    public AccountDto getAccountById(Long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account not found");
        }
        AccountDto accountDto = accountMapper.toDto(account.get());
        accountDto.setUser(userMapper.toDto(account.get().getUser()));
        return accountDto;
    }
    public AccountDto updateAccount(Long accountId, AccountRequest accountRequest) {
        Optional<Account> accountOptional = accountRepository.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new RuntimeException("Account not found");
        }

        Account account = accountOptional.get();
        account.setBalance(accountRequest.getBalance());
        account.setAccountNumber(accountRequest.getAccountNumber());
        account.setStatus(accountRequest.getStatus());

        User user = userRepository.findById(accountRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        account.setUser(user);

        Account savedAccount = accountRepository.save(account);

        AccountDto accountDto = accountMapper.toDto(savedAccount);
        accountDto.setUser(userMapper.toDto(user));
        return accountDto;
    }
    public void deleteAccount(Long accountId) {
        accountRepository.deleteById(accountId);
    }
    public List<AccountDto> findAll(){
        List<Account> accounts = accountRepository.findAllWithUser();
        return accountMapper.toDtoList(accounts);
    }
}
