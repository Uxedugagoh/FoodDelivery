package com.example.fooddelivery.repository;

import com.example.fooddelivery.dto.Cuisine;
import com.example.fooddelivery.entity.RestaurantEntity;
import com.example.fooddelivery.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query("""
                select distinct r
                from RestaurantEntity r
                join r.cuisines c
                where c in :cuisines
            """)
    List<RestaurantEntity> findByAnyCuisineIn(@Param("cuisines") List<Cuisine> cuisines);

    Optional<RestaurantEntity> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    Optional<RestaurantEntity> findByIdAndOwnerUser(Long id, UserEntity ownerUser);
}