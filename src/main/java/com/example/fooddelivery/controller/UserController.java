package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.UserEntityMapper;
import com.example.fooddelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserEntityMapper userEntityMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, UserEntityMapper userEntityMapper, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userEntityMapper = userEntityMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        // Используем логику преобразования маппера здесь, а не в сервисе, потому что может потребоваться прямой
        // доступ к entity
        return userService.findAll().stream().map(userEntityMapper::toDto).toList();
    }

    @PostMapping
    public UserDto registerUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = userEntityMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityMapper.toDto(userService.register(userEntity));
    }
}