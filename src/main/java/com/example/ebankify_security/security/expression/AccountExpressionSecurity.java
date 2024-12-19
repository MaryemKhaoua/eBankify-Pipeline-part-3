package com.example.ebankify_security.security.expression;


import com.example.ebankify_security.domain.enums.AccountStatus;
import com.example.ebankify_security.dto.AccountDto;
import com.example.ebankify_security.service.AccountService;
import com.example.ebankify_security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("accountSecurity")
@RequiredArgsConstructor
public class AccountExpressionSecurity extends SecurityExpression {

    private final AccountService accountService;
    private final UserService userService;

    public boolean canCreateAccount(Long UserId) {
        return  isAdmin() || getCurrentUser().getId().equals(UserId);
    }

    public boolean canModifyAccount(Long accountId) {
        AccountDto accountDto = accountService.getAccountById(accountId);
        return isAdmin() || userService.findById(accountDto.getUserId()).getId().equals(getCurrentUser().getId());
    }

}
