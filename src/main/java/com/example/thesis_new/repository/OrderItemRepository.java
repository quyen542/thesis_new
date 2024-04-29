package com.example.thesis_new.repository;

import com.example.thesis_new.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT distinct oi.food.id FROM OrderItem oi where oi.food.is_delete = false group by oi.food.id order by count(oi.food.id) desc LIMIT 10")
    List<Long> getListFoodIdDesc();

    @Query("SELECT sum(oi.quantity) FROM OrderItem oi where oi.food.id = ?1")
    Double countOrderItemByFoodId(Long id);
    @Query("SELECT oi FROM OrderItem oi where oi.food.id = ?1")
    List<OrderItem> getOrderItemByFoodId(Long id);

    @Query("SELECT sum(oi.quantity) FROM OrderItem oi, Order o where oi.food.id = ?1 and o.id = oi.order.id and o.date = CURDATE()")
    Double countOrderItemByFoodIdInDay(Long id);

    @Query(value = "SELECT sum(oi.quantity) FROM thesis_new.order_item oi, thesis_new.foodorder o where oi.food_id = ?1 and o.id = oi.order_id and o.date BETWEEN  (CURDATE() - INTERVAL 7 DAY) AND  CURDATE()  ;", nativeQuery = true)
    Double countOrderItemByFoodIdInWeek(Long id);

    @Query(value = "SELECT sum(oi.quantity) FROM thesis_new.order_item oi, thesis_new.foodorder o where oi.food_id = ?1 and o.id = oi.order_id and o.date BETWEEN  (CURDATE() - INTERVAL 30 DAY) AND  CURDATE()  ;", nativeQuery = true)
    Double countOrderItemByFoodIdInMonth(Long id);

}
