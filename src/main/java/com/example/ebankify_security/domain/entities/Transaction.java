package com.example.ebankify_security.domain.entities;

import com.example.ebankify_security.domain.enums.TransactionStatus;
import com.example.ebankify_security.domain.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;
    @Column(name = "next_execution_date")
    private LocalDate nextExecutionDate;

}