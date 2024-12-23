package com.example.ebankify_security;

import com.example.ebankify_security.domain.entities.Account;
import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.requests.AccountRequest;
import com.example.ebankify_security.dto.AccountDto;
import com.example.ebankify_security.mapper.AccountMapper;
import com.example.ebankify_security.mapper.UserMapper;
import com.example.ebankify_security.repository.AccountRepository;
import com.example.ebankify_security.repository.UserRepository;
import com.example.ebankify_security.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        // Arrange
        AccountRequest request = new AccountRequest();
        request.setUserId(1L);
        request.setAccountNumber("123456");
        request.setBalance(1000.0);

        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setUser(user);

        Account savedAccount = new Account();
        savedAccount.setId(1L);
        savedAccount.setUser(user);

        AccountDto accountDto = new AccountDto();

        when(accountMapper.toEntity(request)).thenReturn(account);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(accountRepository.save(account)).thenReturn(savedAccount);
        when(accountMapper.toDto(savedAccount)).thenReturn(accountDto);
        when(userMapper.toDto(user)).thenReturn(accountDto.getUser());

        // Act
        AccountDto result = accountService.createAccount(request);

        // Assert
        assertNotNull(result);
        verify(accountRepository).save(account);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAccountById() {
        // Arrange
        Long accountId = 1L;
        Account account = new Account();
        account.setId(accountId);
        User user = new User();
        account.setUser(user);

        AccountDto accountDto = new AccountDto();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(accountMapper.toDto(account)).thenReturn(accountDto);
        when(userMapper.toDto(user)).thenReturn(accountDto.getUser());

        // Act
        AccountDto result = accountService.getAccountById(accountId);

        // Assert
        assertNotNull(result);
        verify(accountRepository).findById(accountId);
    }

    @Test
    void testUpdateAccount() {
        // Arrange
        Long accountId = 1L;
        AccountRequest request = new AccountRequest();
        request.setUserId(2L);
        request.setBalance(2000.0);
        request.setAccountNumber("654321");

        Account existingAccount = new Account();
        existingAccount.setId(accountId);
        User existingUser = new User();
        existingUser.setId(1L);
        existingAccount.setUser(existingUser);

        User newUser = new User();
        newUser.setId(2L);

        Account updatedAccount = new Account();
        updatedAccount.setId(accountId);
        updatedAccount.setUser(newUser);

        AccountDto accountDto = new AccountDto();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(existingAccount));
        when(userRepository.findById(2L)).thenReturn(Optional.of(newUser));
        when(accountRepository.save(existingAccount)).thenReturn(updatedAccount);
        when(accountMapper.toDto(updatedAccount)).thenReturn(accountDto);
        when(userMapper.toDto(newUser)).thenReturn(accountDto.getUser());

        // Act
        AccountDto result = accountService.updateAccount(accountId, request);

        // Assert
        assertNotNull(result);
        verify(accountRepository).findById(accountId);
        verify(userRepository).findById(2L);
        verify(accountRepository).save(existingAccount);
    }

    @Test
    void testDeleteAccount() {
        // Arrange
        Long accountId = 1L;

        // Act
        accountService.deleteAccount(accountId);

        // Assert
        verify(accountRepository).deleteById(accountId);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<Account> accounts = List.of(new Account(), new Account());
        List<AccountDto> accountDtos = List.of(new AccountDto(), new AccountDto());

        when(accountRepository.findAllWithUser()).thenReturn(accounts);
        when(accountMapper.toDtoList(accounts)).thenReturn(accountDtos);

        // Act
        List<AccountDto> result = accountService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(accountRepository).findAllWithUser();
    }
}
