package com.example.fooddelivery.service;

import com.example.fooddelivery.dto.MenuDto;
import com.example.fooddelivery.dto.MenuStatus;
import com.example.fooddelivery.entity.MenuEntity;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.repository.MenuRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private final UserService userService;
    @Autowired
    private MenuService self;

    public MenuEntity addDishToRestaurant(RestaurantEntity restaurant, MenuDto menuDto) {
        MenuEntity menuEntity = menuMapper.toEntity(menuDto);
        menuEntity.setRestaurant(restaurant);
        return menuRepository.save(menuEntity);
    }

    @Transactional
    public MenuEntity changeDish(Long id, MenuDto menuDto) {
        MenuEntity currentMenu = menuRepository.findByIdAndRestaurantOwnerUser(id, userService.getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException("No dishes with that id"));
        return menuRepository.save(menuMapper.updateEntity(currentMenu, menuDto));
    }

    @Transactional
    public void deleteDish(Long id) {
        menuRepository.deleteByIdAndRestaurantOwnerUser(id, userService.getCurrentUser());
    }


    public MenuEntity changeDishAvailability(Long id, MenuStatus menuStatus) {
        MenuDto menuDto = new MenuDto();
        menuDto.setStatus(menuStatus);
        return self.changeDish(id, menuDto);
    }
}