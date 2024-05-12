package com.example.thesis_new.controller;

import com.example.thesis_new.service.DeliveryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class DeliveryController {

    @Autowired
    DeliveryPersonService deliveryPersonService;

    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping("/delivery/delivery-dashboard")
    public String dashboard_view(Model model, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        deliveryPersonService.deliveryDashboardSetup(model, userDetails.getUsername() );
        return "DeliveryDashboard";
    }

    @GetMapping("/delivery/delivery-dashboard-order")
    public String dashboard_oredr_view(Model model, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }
        deliveryPersonService.deliveryOrderListSetup(model, userDetails.getUsername());
        return "DeliveryOrderList";
    }

    @PostMapping("/delivery/update-delivery-status")
    public String dashboard_update_delivery_status(@RequestParam("order_id") Long id, @RequestParam("status") String status, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }
        deliveryPersonService.updateDeliveryStatus(id, status, userDetails.getUsername());
        return "redirect:/delivery/delivery-dashboard-order";
    }
}
