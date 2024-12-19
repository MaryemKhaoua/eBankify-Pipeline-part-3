package com.example.ebankify_security.domain.requests;

import com.example.ebankify_security.domain.enums.TransactionStatus;
import com.example.ebankify_security.domain.enums.TransactionType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    @NotNull(message = "Transaction type cannot be null")
    private TransactionType type;

    @Min(value = 0, message = "Amount must be positive")
    private double amount;

    @NotNull(message = "Transaction status cannot be null")
    private TransactionStatus status;

    @NotNull(message = "Source account ID cannot be null")
    private Long sourceAccountId;

    @NotNull(message = "Destination account ID cannot be null")
    private Long destinationAccountId;

    @Future(message = "The next execution date must be in the future")
    private LocalDate nextExecutionDate;

    @AssertTrue(message = "Next execution date must be provided for a scheduled transaction")
    public boolean isNextExecutionDateValid() {
        return type != TransactionType.SCHEDULED || nextExecutionDate != null;
    }
}
