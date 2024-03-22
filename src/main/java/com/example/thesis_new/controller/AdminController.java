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

    private User currentuser = null;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/admin-dashboard")
    public String dashboard_view(Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.adminDashboardSetup(model);
        return "AdminDashboard";
    }

    @GetMapping("/admin-dashboard-customer")
    public String dashboard_customer_view(Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.getCustomerList(model);
        return "CustomerList";
    }

    @GetMapping("/admin-dashboard-create-food")
    public String dashboard_create_food_view(Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.getFoodList(model);
        return "CreateFoodAndList";
    }

    @PostMapping("/create-food")
    public String create_food(@Valid @ModelAttribute("food") Food food, BindingResult bindingResult, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        boolean check = adminService.addNewFood(food, bindingResult, multipartFile);

        if(!check){
            return "CreateFoodAndList";
        }

        return "redirect:/admin-dashboard-create-food";
    }

    @GetMapping( "/delete/{name}")
    public String delete_food(@PathVariable("name") String name ) throws IOException {
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.deleteFood(name);

        return  "redirect:/admin-dashboard-create-food";
    }

    @GetMapping("/edit-food/{name}")
    public String edit_food_view(@PathVariable("name") String name, Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.editFoodView(name, model);
        return "EditFood";
    }

    @PostMapping("/editfood")
    public String editfood(@Valid @ModelAttribute("food") Food food, BindingResult bindingResult,  RedirectAttributes redirectAttributes){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }

        boolean check = adminService.editFood(food, bindingResult);

        if(!check){
            return "EditFood";
        }

        return  "redirect:/admin-dashboard-create-food";
    }

    @GetMapping("/admin-order-list")
    public String dashboard_order_list(Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }


        adminService.adminOrderListView(model);
        return "AdminOrderList";
    }

    @PostMapping("/updatestatus")
    public String updateStatus(@RequestParam("order_id") Long id, @RequestParam("status") String status){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.updateStatus(id, status);
        return "redirect:/admin-order-list";
    }

    @PostMapping("/updatedeliveryperson")
    public String updateDeliveryPerson(@RequestParam("order_id") Long id, @RequestParam("DeliveryId") Long DeliveryId ){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.updateDeliveryPerson(id, DeliveryId);
        return "redirect:/admin-order-list";
    }

    @GetMapping("/admin-dashboard-delivery_person")
    public String dashboard_delivery_person_view(Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        adminService.getDeliveryPersonList(model);
        return "AdminDeliveryPersonList";
    }


    @GetMapping("/admin-dashboard-add-delivery_person")
    public String dashboard_add_delivery_person_view( Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }

        model.addAttribute("user", new NewDeliveryPerson());

        return "AdminAddNewDeliveryPerson";
    }

    @PostMapping("/adddeliveryperson")
    public String addNewDeliveryPerson(@Valid @ModelAttribute("user") NewDeliveryPerson user, BindingResult bindingResult, Model model){
        if(!adminService.checkAdmin()){
            return "NoPermissionAdmin";
        }
        boolean check = adminService.addDeliveryPerson(user, bindingResult);

        if(!check){
            return "AdminAddNewDeliveryPerson";
        }

        return "redirect:/admin-dashboard-delivery_person";
    }

}
