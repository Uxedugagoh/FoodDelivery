package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.repository.UserRepository;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.UserEntityMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> findAll() {
        // JPA repos
        return userRepository.findAll();
    }

}