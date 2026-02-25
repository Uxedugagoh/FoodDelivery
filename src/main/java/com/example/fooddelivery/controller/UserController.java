package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.dto.UserRole;
import com.example.fooddelivery.mapper.UserEntityMapper;
import com.example.fooddelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserEntityMapper userEntityMapper;

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
        return userEntityMapper.toDto(userService.register(userDto));
    }

    @PutMapping
    public UserDto changeUserProfile(@RequestBody UserDto userDto) {
        return userEntityMapper.toDto(userService.changeUserProfile(userDto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public UserDto deleteUserProfile(@PathVariable Long id) {
        return userEntityMapper.toDto(userService.deleteUserById(id));
    }


}