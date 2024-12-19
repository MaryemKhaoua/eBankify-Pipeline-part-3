package com.example.ebankify_security.exeption;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {

        super(message);
    }
}
