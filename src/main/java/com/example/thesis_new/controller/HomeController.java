package com.example.thesis_new.controller;


import com.example.thesis_new.dto.CheckOutInfor;
import com.example.thesis_new.dto.PassChange;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {


    @Autowired
    RegistrationService registrationService;

    @Autowired
    HomeService homeService;

    @Autowired
    AdminService adminService;

    @Autowired
    DeliveryPersonService deliveryPersonService;

    @Autowired
    private VNPayService vnPayService;


    private String userExsiit = "f";

    private User Currentuser = null;

    private String CurrentCategory = null;

    private String Currentkeyword = null;

    private String CurrentRatingTime = "day";

    private CheckOutInfor checkOutInforTemp = null;

    private Long cartIdTemp = null;

    private String phonenumberTemp = null;





    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/home")
    public String home(Model model){



        homeService.ratingFood(CurrentRatingTime);



        homeService.homeSetup(model, Currentuser, CurrentCategory, Currentkeyword);

        model.addAttribute("user", userExsiit);

        return "index";
    }

    @PostMapping("/likefood")
    public String likeFood(Model model, @RequestParam("id") Long id, @RequestParam("q1") Double quality, @RequestParam("q2") Double price, @RequestParam("q3") Double packaged ){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        homeService.likeFood(model, id, Currentuser, quality, price, packaged);


        return "redirect:/home";
    }

    @PostMapping("/unlikefood")
    public String unlikeFood(Model model, @RequestParam("id") Long id){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        homeService.unlikeFood(model, id, Currentuser);


        return "redirect:/home";
    }

    @PostMapping("/dislikefood")
    public String dislikeFood(Model model, @RequestParam("id") Long id, @RequestParam("q1") Double quality, @RequestParam("q2") Double price, @RequestParam("q3") Double packaged ){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }
        homeService.dislikeFood(model, id, Currentuser, quality, price, packaged);


        return "redirect:/home";
    }

    @PostMapping("/undislikefood")
    public String undislikeFood(Model model, @RequestParam("id") Long id){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }
        homeService.undislikeFood(model, id, Currentuser);


        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login_View(Model model){


        userExsiit = "f";
        Currentuser = null;
        adminService.setCurrentuser(null);
        deliveryPersonService.setCurrentuser(null);
        User user = new User();
        model.addAttribute("user", user);
        return "LogIn";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model){

//        User outhUser = userService.user(user.getEmail(), user.getPassword());
//        System.out.println(outhUser);
        User result = homeService.loginAccount(user, bindingResult);


        if(result != null) {
            adminService.setCurrentuser(result);
            deliveryPersonService.setCurrentuser(result);

            if (result.getRole().getName().equals("admin")) {

                return "redirect:/admin-dashboard";
            } else if (result.getRole().getName().equals("customer")) {
                userExsiit = "t";

                model.addAttribute("user", userExsiit);

                Currentuser = result;
                return "redirect:/home";
            } else if (result.getRole().getName().equals("delivery")){

                return "redirect:/delivery-dashboard";
            }
        }
        return "LogIn";

    }

    @GetMapping("/signup")
    public String signup_View(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "SignUp";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        Boolean result = registrationService.registAccount(user, bindingResult);

        if (result){
            bindingResult.addError(new FieldError("user", "Repassword", "Signup successful"));
            return "SignUp";
        }
        else{
            return "SignUp";
        }

    }


    @GetMapping("/profile")
    public String profile_view(Model model){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        if(Currentuser!= null){
            System.out.println(Currentuser.getRole().getName());
            if(!Currentuser.getRole().getName().equals("customer")) {
            }

        }

        homeService.profileView(model, Currentuser);


        return "Profile";
    }

    @PostMapping("/updateprofile")
    public String updateprofile(@Valid @ModelAttribute("user") User customer, BindingResult bindingResult,  RedirectAttributes redirectAttributes){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        Currentuser =  homeService.updateProfile(Currentuser, customer, bindingResult);


        System.out.println(Currentuser.toString());
        return "redirect:/profile";
    }

    @GetMapping("/changepass")
    public String changepass_view(Model model){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        homeService.cartBarStatus(Currentuser, model);

        model.addAttribute("pass", new PassChange());


//        homeService.cartBarStatus( Currentuser, model);


        return "ChangePassword";
    }

    @PostMapping("/changepassimpl")
    public String updatepass(@Valid @ModelAttribute("pass") PassChange passChange, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        Boolean check = homeService.updatePass(Currentuser, passChange, bindingResult);

        if(!check){
            homeService.cartBarStatus(Currentuser, model);
            return "ChangePassword";
        }


        return "redirect:/profile";
    }


    @GetMapping("/check-out")
    public String checkout_view(Model model){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }


        homeService.checkOutView(model, Currentuser);


        return "CheckOut";
    }

    @PostMapping("/addtocart")
    public String addtocart(Model model, @RequestParam("id") Long id, @RequestParam("quantity") int quantity){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }
        homeService.addToCart(model, Currentuser, id, quantity);
        return "redirect:/home";
    }

    @PostMapping("/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id, @RequestParam("quantity") int quantity){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        homeService.updateCart(id, quantity);

        return "redirect:/check-out";
    }

    @GetMapping("/removeItem/{id}")
    public String removeItem(@PathVariable("id") Long id){
        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        homeService.removeItem(id, Currentuser);

        return "redirect:/check-out";
    }

    @GetMapping("/check-out-infor")
    public String checkoutinfor_view(Model model){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        boolean check = homeService.checkOutInforView(model, Currentuser);

        if(!check){
            return "CartEmpty";

        }

        return "CheckOutInfor";
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam("cart_id") Long id, @ModelAttribute("user") CheckOutInfor checkOutInfor, @RequestParam("phonenumber") String phonenumber, @RequestParam("payment_method") String pMethod, @RequestParam("amount") Double orderTotal,
                             @RequestParam("orderInfo") String orderInfo,  HttpServletRequest request ){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        if(pMethod.equals("COD")){
            homeService.placeOrder(id, checkOutInfor, phonenumber, Currentuser, false);
        }else if (pMethod.equals("Online")){
            cartIdTemp = id;
            checkOutInforTemp = checkOutInfor;
            phonenumberTemp = phonenumber;
            Double total = orderTotal * 24000;
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(total.intValue(), orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }

        return "redirect:/order-list";
    }

    @GetMapping("/order-list")
    public String orderlist_view(Model model){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }
        homeService.orderListView(model, Currentuser);
        return "OrderList";
    }

    @GetMapping("/trackOrder/{id}")
    public String trackOrder(@PathVariable("id") Long id){

        if(!homeService.checkUser(Currentuser)){
            return "NoPermissionUser";
        }

        return "redirect:/order-list";
    }

    @PostMapping("/update-category")
    public String updateCategory(@RequestParam("category") String category, Model model){


        if(category.equals("null")){
            CurrentCategory = null;
            Currentkeyword = null;
        }else{
            CurrentCategory = category;
            Currentkeyword = null;
        }

        return "redirect:/home#menu";
    }

    @PostMapping("/update-rating")
    public String updateRatingTime(@RequestParam("time") String time, Model model){



        CurrentRatingTime = time;



        return "redirect:/home#menu";
    }

    @PostMapping("/delivery-rating")
    public String updateDeliveryRating(@RequestParam("rate") Double rate, @RequestParam("id") Long orderId, Model model){

        homeService.deliveryRating(rate, orderId);

        homeService.ratingDeliveryPeron();



        return "redirect:/order-list";
    }

    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment")
    public String GetMapping(HttpServletRequest request, Model model){
        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        model.addAttribute("orderId", orderInfo);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("paymentTime", paymentTime);
        model.addAttribute("transactionId", transactionId);

        if(paymentStatus == 1){
            homeService.placeOrder(cartIdTemp, checkOutInforTemp, phonenumberTemp, Currentuser, true);
            cartIdTemp = null;
            checkOutInforTemp = null;
            phonenumberTemp = null;
            return "ordersuccess";
        }

        return "orderfail";
    }
    @PostMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword){

        Currentkeyword = keyword;
        CurrentCategory = null;

        return "redirect:/home#menu";
    }





}
