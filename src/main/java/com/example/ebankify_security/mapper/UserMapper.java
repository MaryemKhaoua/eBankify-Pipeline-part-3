package com.example.ebankify_security.mapper;

import com.example.ebankify_security.domain.requests.UserRequest;
import com.example.ebankify_security.dto.UserAuthDto;
import com.example.ebankify_security.dto.UserDto;
import com.example.ebankify_security.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDto toDto(User user);
    UserAuthDto toUserAuthDto(User user);
    User toEntity(UserDto userDto);
    User toEntity(UserRequest userRequest);
}
