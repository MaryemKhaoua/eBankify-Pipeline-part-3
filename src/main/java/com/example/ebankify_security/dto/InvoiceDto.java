package com.example.ebankify_security.dto;

import com.example.ebankify_security.domain.enums.InvoiceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InvoiceDto {
    private Long id;
    private double amountDue;
    private Date dueDate;
    private UserDto user;
    private InvoiceStatus status;
}
