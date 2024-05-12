package com.example.thesis_new.service;


import com.example.thesis_new.dto.CheckOutInfor;
import com.example.thesis_new.dto.PassChange;
import com.example.thesis_new.entity.Food;
import org.springframework.ui.Model;
import com.example.thesis_new.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface HomeService {

    User loginAccount(User user, BindingResult bindingResult);

    void homeSetup(Model model, String username, String currentCategory, String currentKeyword);

    void profileView(Model model, String username );

    void updateProfile( String username, User update, BindingResult bindingResult );

    void cartBarStatus(String username, Model model);

    Boolean updatePass(String username, PassChange passChange, BindingResult bindingResult);

    void checkOutView(Model model, String username);

    void addToCart(Model model, String username, Long id, int quantity);

    void updateCart(Long id, int quantity);

    void removeItem(Long id, String username);

    boolean checkOutInforView(Model model, String username);

    public void placeOrder(Long id, CheckOutInfor checkOutInfor,String phonenumber, String username,  boolean is_payment);
    void orderListView(Model model, String username);

    void trackOrder(Long id);

    void ratingFood(String time);

    void likeFood(Model model, Long id, String username, Double quality, Double price, Double packaged);

    void unlikeFood(Model model, Long id, String username);

    void dislikeFood(Model model, Long id, String username, Double quality, Double price, Double packaged);

    void undislikeFood(Model model, Long id, String username);

    void deliveryRating(Double rate, Long orderID);

    void ratingDeliveryPeron();

    List<Food> searchFood(String keyword);

}
