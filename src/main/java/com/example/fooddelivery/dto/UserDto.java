package com.example.fooddelivery.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private UserRole role;
}