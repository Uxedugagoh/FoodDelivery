package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.entity.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper
public interface RestaurantMapper {
    RestaurantDto toDto(RestaurantEntity entity);
    RestaurantEntity toEntity(RestaurantDto restaurantDto);
}