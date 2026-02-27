package com.example.fooddelivery.mapper;

import com.example.fooddelivery.dto.MenuDto;
import com.example.fooddelivery.entity.MenuEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MenuMapper {
    @Mapping(target = "id", ignore = true)
    MenuEntity toEntity(MenuDto menuDto);

    MenuDto toDto(MenuEntity menuEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    MenuEntity updateEntity(@MappingTarget MenuEntity menuEntity, MenuDto menuDto);
}