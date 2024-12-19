package com.example.ebankify_security.mapper;

import com.example.ebankify_security.dto.TransactionDto;
import com.example.ebankify_security.domain.entities.Transaction;
import com.example.ebankify_security.domain.requests.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDto toDto(Transaction transaction);
    Transaction toEntity(TransactionDto transactionDto);
    Transaction toEntity(TransactionRequest transactionRequest);
    List<TransactionDto> toDtoList(List<Transaction> transactions);
}


