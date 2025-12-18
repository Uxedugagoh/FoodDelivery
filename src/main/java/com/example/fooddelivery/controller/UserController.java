package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.UserEntityMapper;
import com.example.fooddelivery.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserEntityMapper userEntityMapper;

    @GetMapping
    public List<UserDto> findAllUsers() {
        // Используем логику преобразования маппера здесь, а не в сервисе, потому что может потребоваться прямой
        // доступ к entity
        return userService.findAll().stream().map(userEntityMapper::toDto).toList();
    }

}