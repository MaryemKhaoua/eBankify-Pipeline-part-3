package com.example.ebankify_security.repository;

import com.example.ebankify_security.domain.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}