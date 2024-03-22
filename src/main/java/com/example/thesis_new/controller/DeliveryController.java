package com.example.thesis_new.controller;

import com.example.thesis_new.service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeliveryController {

    @Autowired
    DeliveryPersonService deliveryPersonService;

    @GetMapping("/delivery-dashboard")
    public String dashboard_view(Model model){

        if(!deliveryPersonService.checkDelivery()){
            return "NoPermissionDelivery";
        }
        deliveryPersonService.deliveryDashboardSetup(model);
        return "DeliveryDashboard";
    }

    @GetMapping("/delivery-dashboard-order")
    public String dashboard_oredr_view(Model model){
        if(!deliveryPersonService.checkDelivery()){
            return "NoPermissionDelivery";
        }
        deliveryPersonService.deliveryOrderListSetup(model);
        return "DeliveryOrderList";
    }

    @PostMapping("/update-delivery-status")
    public String dashboard_update_delivery_status(@RequestParam("order_id") Long id, @RequestParam("status") String status){
        if(!deliveryPersonService.checkDelivery()){
            return "NoPermissionDelivery";
        }
        deliveryPersonService.updateDeliveryStatus(id, status);
        return "redirect:/delivery-dashboard-order";
    }
}
