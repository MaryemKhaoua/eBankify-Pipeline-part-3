package com.example.ebankify_security.vm;

import com.example.ebankify_security.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVM {
    private UserDto user;
    private String message;
    private int statusCode;
}
