package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll().stream().map(restaurantMapper::toDto).toList();
    }

    @PostMapping
    public RestaurantDto registerRestaurant(@RequestBody RestaurantDto restaurantDto) {
        restaurantDto.setId(null);
        RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurantDto);
        return restaurantMapper.toDto(restaurantService.registerRestaurant(restaurantEntity));
    }
}