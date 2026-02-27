package com.example.fooddelivery.entity;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.dto.RestaurantStatus;
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

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private UserEntity ownerUser;

    @OneToMany(mappedBy = "restaurant")
    private List<MenuEntity> menuEntityList;

    @Column(nullable = false)
    @ElementCollection()
    @Enumerated(EnumType.STRING)
    private List<Cuisine> cuisines;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RestaurantStatus restaurantStatus = RestaurantStatus.OPEN;
}