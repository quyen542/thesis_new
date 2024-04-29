package com.example.thesis_new.repository;


import com.example.thesis_new.entity.Food;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
    @Query("SELECT f from Food f where f.name = ?1 and f.is_delete = false ")
    Food findByName(String name);

    @Query("SELECT f from Food f where f.name = ?1 and f.is_delete = false and f.id != ?2 ")
    Food findByNameAndNotId(String name, Long id);

    @Query("SELECT f from Food f where f.category = ?1 and f.is_delete = false order by f.avgRating desc ")
    List<Food> findByCategory(String category);

    @Query("SELECT f from Food f where f.is_delete = false order by f.avgRating desc ")
    List<Food> findAllDesc();


    @Query("SELECT f from Food f where f.id = ?1  ")
    Food findfoodById(Long id);
    @Transactional
    @Modifying
    @Query("delete from Food f where f.name = ?1")
    void deleteByName(String name);

    @Modifying
    @Query("DELETE FROM Food f WHERE f.id = ?1")
    void deleteById(Long id);

    @Query("SELECT distinct f.category from Food f  ")
    List<String> getFoodCategory();
    @Query("SELECT count(f) from Food f where f.is_delete = false  ")
    Long countByNotDelete();

}
