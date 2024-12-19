package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    @Query("SELECT l FROM Loan l JOIN FETCH l.user")
    List<Loan> findAllWithUser();
}

