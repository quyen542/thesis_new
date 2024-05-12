package com.example.thesis_new.service;
import com.example.thesis_new.dto.NewDeliveryPerson;
import com.example.thesis_new.entity.Food;
import com.example.thesis_new.entity.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdminService {

    void adminDashboardSetup(Model model);

    void getCustomerList(Model model);

    void getFoodList(Model model);

    boolean addNewFood(Food food, BindingResult bindingResult, MultipartFile multipartFile ) throws IOException;

    void deleteFood(Long id) throws IOException;

    void editFoodView(Long id, Model model);

    boolean editFood(Food food, BindingResult bindingResult);


    void adminOrderListView(Model model);

    void updateStatus(Long id, String status);

    void getDeliveryPersonList(Model model);

    boolean addDeliveryPerson(NewDeliveryPerson user,BindingResult bindingResult );

    void updateDeliveryPerson(Long id,  Long deliveryId);




}
