package com.example.fooddelivery.repository;

import com.example.fooddelivery.dto.UserRole;
import com.example.fooddelivery.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginIgnoreCase(String login);

    List<UserEntity> findAllByRole(UserRole role);
}