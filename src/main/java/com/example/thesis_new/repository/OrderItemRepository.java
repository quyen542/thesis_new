package com.example.thesis_new.repository;

import com.example.thesis_new.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT distinct oi.food.id FROM OrderItem oi group by oi.food.id order by count(oi.food.id) desc LIMIT 10")
    List<Long> getListFoodIdDesc();

    @Query("SELECT sum(oi.quantity) FROM OrderItem oi where oi.food.id = ?1")
    Double countOrderItemByFoodId(Long id);
}
