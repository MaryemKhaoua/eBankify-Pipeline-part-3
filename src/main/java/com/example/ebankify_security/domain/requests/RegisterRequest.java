package com.example.ebankify_security.domain.requests;

import com.example.ebankify_security.domain.enums.Role;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Monthly income cannot be null")
    private double monthlyIncome;

    @NotNull(message = "Credit score cannot be null")
    private int creditScore;

    @NotNull(message = "Activation status cannot be null")
    private boolean active;
    @NotNull
    private Long roles;

}