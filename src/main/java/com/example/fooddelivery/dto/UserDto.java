package com.example.fooddelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private UserRole role;
}