package com.example.thesis_new.service;

import com.example.thesis_new.entity.User;
import org.springframework.ui.Model;


public interface DeliveryPersonService {

    void deliveryDashboardSetup(Model model );

    void setCurrentuser(User currentuser);

    void deliveryOrderListSetup(Model model);

    void updateDeliveryStatus(Long id, String status);

    boolean checkDelivery();


}
