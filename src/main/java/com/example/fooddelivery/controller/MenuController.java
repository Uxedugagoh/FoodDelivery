package com.example.fooddelivery.controller;

import com.example.fooddelivery.dto.MenuDto;
import com.example.fooddelivery.dto.MenuStatus;
import com.example.fooddelivery.mapper.MenuMapper;
import com.example.fooddelivery.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @PutMapping("/{id}")
    public MenuDto changeDish(@PathVariable Long id, @RequestBody MenuDto menuDto) {
        return menuMapper.toDto(menuService.changeDish(id, menuDto));
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteMenu(@PathVariable Long id) {
        menuService.deleteDish(id);
    }

    @PreAuthorize("hasAnyRole('RESTAURANT_OWNER', 'ADMIN')")
    @PatchMapping("/{id}/availability")
    public MenuDto changeAvailability(@PathVariable Long id, @RequestParam MenuStatus menuStatus) {
        return menuMapper.toDto(menuService.changeDishAvailability(id, menuStatus));
    }

}