package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a JOIN FETCH a.user")
    List<Account> findAllWithUser();
}