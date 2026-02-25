package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.UserDto;
import com.example.fooddelivery.entity.UserEntity;
import org.mapstruct.*;

@Mapper
public interface UserEntityMapper {

    UserDto toDto(UserEntity entity);
    UserEntity toEntity(UserDto userDto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUserEntity(@MappingTarget UserEntity target, UserDto userDto);
}