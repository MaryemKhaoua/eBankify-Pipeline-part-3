package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long>{
}
