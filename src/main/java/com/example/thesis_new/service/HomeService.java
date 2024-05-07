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

    void homeSetup(Model model, User Currentuser, String currentCategory, String currentKeyword);

    void profileView(Model model, User Currentuser );

    User updateProfile( User Currentuser, User update, BindingResult bindingResult );

    void cartBarStatus(User Currentuser, Model model);

    Boolean updatePass(User Currentuser, PassChange passChange, BindingResult bindingResult);

    void checkOutView(Model model, User Currentuser);

    void addToCart(Model model, User CurrentUser, Long id, int quantity);

    boolean checkUser(User Currentuser);

    void updateCart(Long id, int quantity);

    void removeItem(Long id, User CurrentUser);

    boolean checkOutInforView(Model model, User Currentuser);

    public void placeOrder(Long id, CheckOutInfor checkOutInfor,String phonenumber, User Currentuser,  boolean is_payment);
    void orderListView(Model model, User Currentuser);

    void trackOrder(Long id);

    void ratingFood(String time);

    void likeFood(Model model, Long id, User currentuser, Double quality, Double price, Double packaged);

    void unlikeFood(Model model, Long id, User currentuser);

    void dislikeFood(Model model, Long id, User currentuser, Double quality, Double price, Double packaged);

    void undislikeFood(Model model, Long id, User currentuser);

    void deliveryRating(Double rate, Long orderID);

    void ratingDeliveryPeron();

    List<Food> searchFood(String keyword);

}
