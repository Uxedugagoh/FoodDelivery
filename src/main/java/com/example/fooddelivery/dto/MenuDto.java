package com.example.fooddelivery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;


@Data
public class MenuDto implements Serializable {
    @NotNull
    Long id;

    @NotNull
    String name;

    @NotNull
    MenuStatus status;
}