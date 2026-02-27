package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.dto.RestaurantStatus;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.entity.UserEntity;
import com.example.fooddelivery.mapper.RestaurantMapper;
import com.example.fooddelivery.repository.RestaurantRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserService userService;

    public List<RestaurantEntity> findByAnyCuisineIn(List<Cuisine> cuisines) {
        return restaurantRepository.findByAnyCuisineIn(cuisines);
    }

    public List<RestaurantEntity> findAll() {
        return restaurantRepository.findAll();
    }

    public RestaurantEntity findById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id = " + id + " not found"));
    }

    @Transactional
    public RestaurantEntity changeRestaurantProfile(RestaurantDto restaurantDto, Long restaurantId) {
        RestaurantEntity currentRestaurant = getUsersRestaurantById(restaurantId);
        // Проверка на занятость названия ресторана
        if (restaurantDto.getName() != null
                && restaurantRepository.existsByNameIgnoreCaseAndIdNot(restaurantDto.getName(), restaurantId)) {
            throw new EntityExistsException("Restaurant name already exists.");
        }
        restaurantMapper.updateRestaurantEntity(currentRestaurant, restaurantDto);
        restaurantRepository.save(currentRestaurant);
        return currentRestaurant;
    }

    @Transactional
    public RestaurantEntity registerRestaurant(RestaurantDto restaurantDto) {
        if (restaurantRepository.findByNameIgnoreCase(restaurantDto.getName()).isPresent()) {
            throw new EntityExistsException("Restaurant name already exists.");
        }
        UserEntity currentUser = userService.getCurrentUser();
        RestaurantEntity restaurantEntity = restaurantMapper.toEntity(restaurantDto);
        restaurantEntity.setOwnerUser(currentUser);
        restaurantRepository.save(restaurantEntity);
        return restaurantEntity;
    }

    @Transactional
    public RestaurantEntity closeRestaurant(Long id) {
        RestaurantEntity currentRestaurant = getUsersRestaurantById(id);
        currentRestaurant.setRestaurantStatus(RestaurantStatus.CLOSED);
        restaurantRepository.save(currentRestaurant);
        return currentRestaurant;
    }

    public RestaurantEntity getUsersRestaurantById(Long id) {
        UserEntity currentUser = userService.getCurrentUser();
        return restaurantRepository
                .findByIdAndOwnerUser(id, currentUser)
                .orElseThrow(() ->
                        new EntityNotFoundException("RestaurantNotFound")
                );
    }
}