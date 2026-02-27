package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @GetMapping
    public List<RestaurantDto> findAll(@RequestParam(required = false) List<Cuisine> cuisines) {
        if (cuisines == null || cuisines.isEmpty()) {
            return restaurantService.findAll().stream().map(restaurantMapper::toDto).toList();
        }
        return restaurantService.findByAnyCuisineIn(cuisines).stream().map(restaurantMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public RestaurantDto findById(@PathVariable Long id) {
        return restaurantMapper.toDto(restaurantService.findById(id));
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @PutMapping("/{id}")
    public RestaurantDto changeRestaurantProfile(@RequestBody RestaurantDto restaurantDto, @PathVariable Long id) {
        return restaurantMapper.toDto(restaurantService.changeRestaurantProfile(restaurantDto, id));
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @PostMapping
    public RestaurantDto registerRestaurant(@RequestBody RestaurantDto restaurantDto) {
        return restaurantMapper.toDto(restaurantService.registerRestaurant(restaurantDto));
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public RestaurantDto closeRestaurant(@PathVariable Long id) {
        return restaurantMapper.toDto(restaurantService.closeRestaurant(id));
    }
}