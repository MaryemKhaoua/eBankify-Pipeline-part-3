package com.example.ebankify_security.domain.requests;

import com.example.ebankify_security.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(value = 18, message = "Age must be at least 18 years")
    private int age;

    @Email(message = "Email address is not valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Monthly income cannot be null")
    private double monthlyIncome;

    @NotNull(message = "Credit score cannot be null")
    private int creditScore;

    @NotNull(message = "Role cannot be null")
    private Role role;

    @NotNull(message = "Activation status cannot be null")
    private boolean active;
}