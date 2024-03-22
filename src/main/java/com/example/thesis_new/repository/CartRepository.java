package com.example.thesis_new.repository;

import com.example.thesis_new.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("SELECT c from Cart c where c.user.id = ?1  ")
    Cart findByUserID(Long id);

    @Query("SELECT c from Cart c where c.id = ?1  ")
    Cart findcartById(Long id);




}
