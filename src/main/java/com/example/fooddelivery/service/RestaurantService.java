package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantEntity> findByCuisinesContaining(List<Cuisine> cuisines) {
        return restaurantRepository.findByCuisinesContaining(cuisines);
    }

    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantEntity findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id = " + id + " not found"));
    }

    public RestaurantEntity changeRestaurantProfile(RestaurantDto restaurantDto) {
        if (restaurantDto.getName() != null && restaurantRepository.findByNameIgnoreCase(restaurantDto.getName()).isPresent()) {
            throw new EntityExistsException("Restaurant name already exists.");
        }
        return null;
    }

    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        if (restaurantRepository.findByNameIgnoreCase(restaurantEntity.getName()).isPresent()) {
            throw new EntityExistsException("Restaurant name already exists.");
        }
        restaurantRepository.save(restaurantEntity);
        return restaurantEntity;
    }
}