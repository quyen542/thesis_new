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

    List<Order> getOrderByDeliveryPerson(User deliveryPerson);

}
