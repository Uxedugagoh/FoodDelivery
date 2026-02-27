package com.example.fooddelivery.repository;


import com.example.fooddelivery.entity.MenuEntity;
import com.example.fooddelivery.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {

    void deleteByIdAndRestaurantOwnerUser(Long id, UserEntity restaurantOwnerUser);

    Optional<MenuEntity> findByIdAndRestaurantOwnerUser(Long id, UserEntity restaurantOwnerUser);
}