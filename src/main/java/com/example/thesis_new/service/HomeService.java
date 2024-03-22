package com.example.thesis_new.service;


import com.example.thesis_new.dto.CheckOutInfor;
import com.example.thesis_new.dto.PassChange;
import org.springframework.ui.Model;
import com.example.thesis_new.entity.User;
import org.springframework.validation.BindingResult;

public interface HomeService {

    User loginAccount(User user, BindingResult bindingResult);

    void homeSetup(Model model, User Currentuser, String currentCategory);

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

    public void placeOrder(Long id, CheckOutInfor checkOutInfor,String phonenumber, User Currentuser);
    void orderListView(Model model, User Currentuser);

    void trackOrder(Long id);

}
