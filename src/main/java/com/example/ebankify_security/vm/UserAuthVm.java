package com.example.ebankify_security.vm;

import com.example.ebankify_security.dto.UserAuthDto;
import com.example.ebankify_security.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthVm {
    private UserAuthDto user;
    private String message;
    private int statusCode;
}
