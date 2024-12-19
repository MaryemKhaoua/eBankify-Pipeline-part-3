package com.example.ebankify_security.domain.response;


import com.example.ebankify_security.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UserDto user;
    private String message;
    private int statusCode;
}
