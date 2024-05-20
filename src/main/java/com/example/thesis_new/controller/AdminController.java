package com.example.thesis_new.controller;

import com.example.thesis_new.dto.NewDeliveryPerson;
import com.example.thesis_new.dto.PassChange;
import com.example.thesis_new.entity.Food;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Controller
public class AdminController {

    @Autowired
    AdminService adminService;


    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/admin/admin-dashboard")
    public String dashboard_view(Model model){

        adminService.adminDashboardSetup(model);
        return "AdminDashboard";
    }

    @GetMapping("/admin/admin-dashboard-customer")
    public String dashboard_customer_view(Model model){

        adminService.getCustomerList(model);
        return "CustomerList";
    }

    @GetMapping("/admin/admin-dashboard-create-food")
    public String dashboard_create_food_view(Model model, String keyword){
        if(keyword != null){
            adminService.getFoodList(model, keyword);
        }else{
            adminService.getFoodList(model, null);
        }
        return "CreateFoodAndList";
    }

    @PostMapping("/admin/create-food")
    public String create_food(@Valid @ModelAttribute("food") Food food, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        boolean check = adminService.addNewFood(food, bindingResult, multipartFile);

        if(!check){
            return "CreateFoodAndList";
        }

        return "redirect:/admin/admin-dashboard-create-food";
    }

    @GetMapping( "/admin/delete/{id}")
    public String delete_food(@PathVariable("id") Long id ) throws IOException {

        adminService.deleteFood(id);

        return  "redirect:/admin/admin-dashboard-create-food";
    }

    @GetMapping("/admin/edit-food/{id}")
    public String edit_food_view(@PathVariable("id") Long id, Model model){

        adminService.editFoodView(id, model);
        return "EditFood";
    }

    @PostMapping("/admin/editfood")
    public String editfood(@Valid @ModelAttribute("food") Food food, BindingResult bindingResult,  RedirectAttributes redirectAttributes){


        boolean check = adminService.editFood(food, bindingResult);

        if(!check){
            return "EditFood";
        }

        return  "redirect:/admin/admin-dashboard-create-food";
    }

    @GetMapping("/admin/admin-order-list")
    public String dashboard_order_list(Model model){



        adminService.adminOrderListView(model);
        return "AdminOrderList";
    }

    @PostMapping("/admin/updatestatus")
    public String updateStatus(@RequestParam("order_id") Long id, @RequestParam("status") String status){

        adminService.updateStatus(id, status);
        return "redirect:/admin/admin-order-list";
    }

    @PostMapping("/admin/updatedeliveryperson")
    public String updateDeliveryPerson(@RequestParam("order_id") Long id, @RequestParam("DeliveryId") Long DeliveryId ){

        adminService.updateDeliveryPerson(id, DeliveryId);
        return "redirect:/admin/admin-order-list";
    }

    @GetMapping("/admin/admin-dashboard-delivery_person")
    public String dashboard_delivery_person_view(Model model){

        adminService.getDeliveryPersonList(model);
        return "AdminDeliveryPersonList";
    }


    @GetMapping("/admin/admin-dashboard-add-delivery_person")
    public String dashboard_add_delivery_person_view( Model model){

        model.addAttribute("user", new NewDeliveryPerson());

        return "AdminAddNewDeliveryPerson";
    }

    @PostMapping("/admin/adddeliveryperson")
    public String addNewDeliveryPerson(@Valid @ModelAttribute("user") NewDeliveryPerson user, BindingResult bindingResult, Model model){

        boolean check = adminService.addDeliveryPerson(user, bindingResult);

        if(!check){
            return "AdminAddNewDeliveryPerson";
        }

        return "redirect:/admin/admin-dashboard-delivery_person";
    }

}
