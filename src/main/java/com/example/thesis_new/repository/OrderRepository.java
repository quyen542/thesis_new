package com.example.thesis_new.repository;


import com.example.thesis_new.entity.Order;
import com.example.thesis_new.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT count(o) FROM Order o where o.deliveryPerson.id = ?1")
    int numberOfOrderOfDeliver(Long id);
    @Query("SELECT o FROM Order o where o.deliveryPerson.id = ?1 order by o.id desc")
    List<Order> getOrderByDeliveryPersonID(Long deliveryPersonId);

    @Query("SELECT avg(o.deliveryRating) FROM Order o where o.deliveryPerson.id = ?1")
    Double getAvgDeliveryRating(Long deliveryPersonId);

    @Query("SELECT o FROM Order o order by o.id desc")
    List<Order> getOrderListDesc();


}
