package com.example.thesis_new.service;

import com.example.thesis_new.entity.User;
import org.springframework.ui.Model;


public interface DeliveryPersonService {

    void deliveryDashboardSetup(Model model, String username );


    void deliveryOrderListSetup(Model model, String username );

    void updateDeliveryStatus(Long id, String status, String username );



}
