package com.example.ebankify_security.mapper;

import com.example.ebankify_security.dto.AccountDto;
import com.example.ebankify_security.domain.entities.Account;
import com.example.ebankify_security.domain.requests.AccountRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring",uses = { UserMapper.class })
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);
    AccountDto toDto(Account account);
    List<AccountDto> toDtoList(List<Account> accounts);
    Account toEntity(AccountRequest accountRequest);
    Account toEntity(AccountDto accountDto);
}
