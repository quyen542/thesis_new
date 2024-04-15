package com.example.thesis_new.service;

import com.example.thesis_new.entity.Order;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.repository.OrderRepository;
import com.example.thesis_new.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class DeliveryPersonServiceImp implements DeliveryPersonService {
    @Autowired
    private OrderRepository orderRespository;

    @Autowired
    private UserRepository userRepository;

    private User currentuser;

    @Override
    public boolean checkDelivery(){
        if(currentuser!= null){
            if(!currentuser.getRole().getName().equals("delivery")) {
                return false;
            }
        }
        else{
            return false;
        }

        return true;
    }

    @Override
    public void deliveryDashboardSetup(Model model){

        model.addAttribute("scount", currentuser.getDeliveryInfo().getSalary());
        model.addAttribute("ocount", orderRespository.numberOfOrderOfDeliver(currentuser.getId()));
        model.addAttribute("rcount", currentuser.getDeliveryInfo().getRating());


    }

    @Override
    public void deliveryOrderListSetup(Model model){
        List<Order> orderList = orderRespository.getOrderByDeliveryPerson(currentuser);
        model.addAttribute("listOrder", orderList);
    }

    @Override
    public void setCurrentuser(User currentuser) {
        this.currentuser = currentuser;
    }

    @Override
    public void updateDeliveryStatus(Long id, String status){
        User user = userRepository.findById(currentuser.getId()).get();
        Order order = orderRespository.findById(id).get();

        if(status.equals("Cooked")){
            order.setStatus(Order.Status.Cooked);
        } else if (status.equals("Shipped")) {
            order.setStatus(Order.Status.Shipped);
        } else if (status.equals("Delivered")) {
            order.setStatus(Order.Status.Delivered);
        }

        user.getDeliveryInfo().setAvailable(true);
        user.getDeliveryInfo().setSalary(user.getDeliveryInfo().getSalary() + 2.0);

        currentuser = user;

        userRepository.save(user);
        orderRespository.saveAndFlush(order);
    }
}
