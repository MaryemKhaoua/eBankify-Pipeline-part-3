package com.example.ebankify_security.mapper;

import com.example.ebankify_security.dto.BankDto;
import com.example.ebankify_security.domain.entities.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankMapper Instance = Mappers.getMapper(BankMapper.class);
    BankDto toDto(Bank bank);
    Bank toEntity(BankDto bankDto);

}