package com.example.ebankify_security.mapper;

import com.example.ebankify_security.domain.requests.LoanRequest;
import com.example.ebankify_security.dto.LoanDto;
import com.example.ebankify_security.domain.entities.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanMapper Instance = Mappers.getMapper(LoanMapper.class);
    LoanDto toDto(Loan loan);
    Loan toEntity(LoanRequest loanDto);

    List<LoanDto> toDtoList(List<Loan> loans);
}