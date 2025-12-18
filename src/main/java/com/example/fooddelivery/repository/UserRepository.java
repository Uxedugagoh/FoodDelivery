package com.example.fooddelivery.repository;

import com.example.fooddelivery.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean findByEmail(String email);
}