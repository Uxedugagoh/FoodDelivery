package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.dto.UserRole;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.UserEntityMapper;
import com.example.fooddelivery.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public List<UserDto> findAllUsersByRole(@RequestParam(required = false) String role) {
        // Используем логику преобразования маппера здесь, а не в сервисе, потому что может потребоваться прямой
        // доступ к entity
        if (role == null || role.isBlank()) {
            return userService.findAll().stream().map(userEntityMapper::toDto).toList();
        }
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        return userService.findAllByRole(userRole).stream().map(userEntityMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserProfile(@PathVariable Long id) {
        return userEntityMapper.toDto(userService.getUserById(id));
    }

    @PostMapping
    public UserDto registerUser(@RequestBody UserDto userDto) {
        userDto.setId(null);
        UserEntity userEntity = userEntityMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityMapper.toDto(userService.register(userEntity));
    }

    @PutMapping("/{id}")
    public UserDto changeUserProfile(@PathVariable Long id, @RequestBody UserDto userDto) {
        UserEntity userEntity = userEntityMapper.toEntity(userDto);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userEntityMapper.toDto(userService.changeUserProfile(id, userEntity));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public UserDto deleteUserProfile(@PathVariable Long id) {
        return userEntityMapper.toDto(userService.deleteUserById(id));
    }


}