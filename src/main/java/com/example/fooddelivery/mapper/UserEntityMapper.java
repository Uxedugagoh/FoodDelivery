package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper
public interface UserEntityMapper {

    UserDto toDto(UserEntity entity);
    UserEntity toEntity(UserDto userDto);
}