package com.example.fooddelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Double rating;

    @NotNull
    private Long ownerUserId;

    @NotNull
    private List<Cuisine> cuisines;

    @NotNull
    private RestaurantStatus restaurantStatus;
}