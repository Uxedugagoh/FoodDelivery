package com.example.fooddelivery.entity;

import com.example.fooddelivery.dto.Cuisine;
import com.fasterxml.jackson.databind.annotation.EnumNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(nullable = false)
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private List<Cuisine> cuisines;
}