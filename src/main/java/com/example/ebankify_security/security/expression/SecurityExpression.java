package com.example.ebankify_security.security.expression;

import com.example.ebankify_security.domain.entities.User;
import com.example.ebankify_security.domain.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityExpression {

    protected User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


    protected boolean isAdmin() {
        return getCurrentUser().getRoles().stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
    }

    protected boolean isEmployee() {
        return getCurrentUser().getRoles().stream()
                .anyMatch(role -> role.getName().equals("EMPLOYEE"));
    }


}
