package com.example.fooddelivery.entity;

import com.example.fooddelivery.dto.MenuStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "menu")
@Getter
@Setter
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @JoinColumn(name = "restaurant_id")
    @ManyToOne
    private RestaurantEntity restaurant;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MenuStatus status;
}