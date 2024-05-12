package com.example.thesis_new.service;

import com.example.thesis_new.entity.Order;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.repository.OrderRepository;
import com.example.thesis_new.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryPersonServiceImp implements DeliveryPersonService {
    @Autowired
    private OrderRepository orderRespository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void deliveryDashboardSetup(Model model, String username){
        Optional<User> currentuser = userRepository.findByUsername(username);

        model.addAttribute("scount", currentuser.get().getDeliveryInfo().getSalary());
        model.addAttribute("ocount", orderRespository.numberOfOrderOfDeliver(currentuser.get().getId()));
        model.addAttribute("rcount", currentuser.get().getDeliveryInfo().getRating());


    }

    @Override
    public void deliveryOrderListSetup(Model model, String username ){
        Optional<User> currentuser = userRepository.findByUsername(username);

        List<Order> orderList = orderRespository.getOrderByDeliveryPersonID(currentuser.get().getId());
        model.addAttribute("listOrder", orderList);
    }

    @Override
    public void updateDeliveryStatus(Long id, String status, String username ){
        Optional<User> currentuser = userRepository.findByUsername(username);

        Order order = orderRespository.findById(id).get();

        if(status.equals("Cooked")){
            order.setStatus(Order.Status.Prepared);
        } else if (status.equals("Shipped")) {
            order.setStatus(Order.Status.Shipped);
        } else if (status.equals("Delivered")) {
            order.setStatus(Order.Status.Delivered);
        }

        currentuser.get().getDeliveryInfo().setAvailable(true);
        currentuser.get().getDeliveryInfo().setSalary(currentuser.get().getDeliveryInfo().getSalary() + 2.0);


        userRepository.save(currentuser.get());
        orderRespository.saveAndFlush(order);
    }
}
