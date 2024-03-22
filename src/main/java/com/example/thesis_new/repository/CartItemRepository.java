package com.example.thesis_new.repository;

import com.example.thesis_new.entity.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    @Modifying
    @Query("delete from CartItem ci  where ci.cart.id = ?1")
    void deleteByCartId(Long id);
}

