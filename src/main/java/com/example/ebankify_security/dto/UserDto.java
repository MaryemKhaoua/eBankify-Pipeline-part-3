package com.example.ebankify_security.dto;

import com.example.ebankify_security.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private String email;
    private double monthlyIncome;
    private int creditScore;
    private Role role;
}