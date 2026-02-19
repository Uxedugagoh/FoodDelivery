package com.example.fooddelivery.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Double rating;
    @NotNull
    private List<Cuisine> cuisines;
}