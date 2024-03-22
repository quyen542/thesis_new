package com.example.thesis_new.repository;

import com.example.thesis_new.entity.Cart;
import com.example.thesis_new.entity.DeliveryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {
}
