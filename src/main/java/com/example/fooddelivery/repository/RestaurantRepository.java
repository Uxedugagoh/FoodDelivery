package com.example.fooddelivery.repository;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    List<RestaurantEntity> findByCuisinesContaining(List<Cuisine> cuisines);

    Optional<RestaurantEntity> findByNameIgnoreCase(String name);
}