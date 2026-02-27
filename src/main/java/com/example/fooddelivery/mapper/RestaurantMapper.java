package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.RestaurantDto;
import com.example.fooddelivery.entity.RestaurantEntity;
import org.mapstruct.*;

@Mapper
public interface RestaurantMapper {
    @Mapping(target = "ownerUserId", source = "ownerUser.id")
    RestaurantDto toDto(RestaurantEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    @Mapping(target = "menuEntityList", ignore = true)
    @Mapping(target = "restaurantStatus", ignore = true)
    RestaurantEntity toEntity(RestaurantDto restaurantDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ownerUser", ignore = true)
    @Mapping(target = "menuEntityList", ignore = true)
    void updateRestaurantEntity(@MappingTarget RestaurantEntity target, RestaurantDto restaurantDto);
}