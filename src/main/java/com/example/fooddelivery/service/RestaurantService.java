package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) { this.restaurantRepository = restaurantRepository; }

    public List<RestaurantEntity> findByCuisinesContaining(List<Cuisine> cuisines) {
        return restaurantRepository.findByCuisinesContaining(cuisines);
    }

    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        if (restaurantRepository.findByNameIgnoreCase(restaurantEntity.getName()).isPresent()) {
            throw new RuntimeException("Restaurant name already exists.");
        }
        restaurantRepository.save(restaurantEntity);
        return restaurantEntity;
    }
}