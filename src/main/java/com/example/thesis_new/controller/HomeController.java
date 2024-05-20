package com.example.thesis_new.controller;


import com.example.thesis_new.dto.CheckOutInfor;
import com.example.thesis_new.dto.PassChange;
import com.example.thesis_new.dto.registerDTO;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.global.GlobalData;
import com.example.thesis_new.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    UserDetailsService userDetailsService;

    private User Currentuser = null;




    @InitBinder
    public void initBinder(WebDataBinder dataBinder){
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/home")
    public String home(Model model){


        homeService.ratingFood(GlobalData.CurrentRatingTime);


        homeService.homeSetup(model, null, GlobalData.CurrentCategory, GlobalData.Currentkeyword);

        model.addAttribute("user", "f");

        return "demo";
    }

    @GetMapping("/customer/home")
    public String homeCustomer(Model model, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }


        homeService.ratingFood(GlobalData.CurrentRatingTime);


        homeService.homeSetup(model, userDetails.getUsername(), GlobalData.CurrentCategory, GlobalData.Currentkeyword);

        model.addAttribute("user", "t");

        return "index";
    }

    @PostMapping("/customer/likefood")
    public String likeFood(Model model, @RequestParam("id") Long id, @RequestParam("q1") Double quality, @RequestParam("q2") Double price, @RequestParam("q3") Double packaged, Principal principal ){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        homeService.likeFood(model, id, userDetails.getUsername(), quality, price, packaged);


        return "redirect:/customer/home";
    }

    @PostMapping("/customer/unlikefood")
    public String unlikeFood(Model model, @RequestParam("id") Long id, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        homeService.unlikeFood(model, id, userDetails.getUsername());


        return "redirect:/customer/home";
    }

    @PostMapping("/customer/dislikefood")
    public String dislikeFood(Principal principal, Model model, @RequestParam("id") Long id, @RequestParam("q1") Double quality, @RequestParam("q2") Double price, @RequestParam("q3") Double packaged ){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        homeService.dislikeFood(model, id, userDetails.getUsername(), quality, price, packaged);


        return "redirect:/customer/home";
    }

    @PostMapping("/customer/undislikefood")
    public String undislikeFood(Principal principal, Model model, @RequestParam("id") Long id){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }
        homeService.undislikeFood(model, id, userDetails.getUsername());


        return "redirect:/customer/home";
    }

    @GetMapping("/login")
    public String login_View( @RequestParam(name = "error", required = false) String error, Model model){

        if (error != null) {
            model.addAttribute("ERROR", "Invalid username or password or account is not verified");
        }
        return "LogIn";
    }

    @GetMapping("/signup")
    public String signup_View(Model model){

        return "SignUp";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("USER") registerDTO userDTO, Model model, HttpServletRequest request){

        System.out.println(userDTO.toString());
        userDTO.setVerificationCode(UUID.randomUUID().toString());

        Boolean result = registrationService.registAccount(userDTO, request, model);

        if (result){
            model.addAttribute("ERROR", "Sign up successfully, check email to verify ");
            return "SignUp";
        }
        else{
            return "SignUp";
        }

    }

    @GetMapping("/verify")
    public String verifyAccount(@Param("code") String code) {
        registrationService.verifyAccount(code);
        return "Verification";
    }

    @GetMapping("/forgotPassword")
    public String ForgotPasswordView() {
        return "ResetPasswordEmail";
    }

    @PostMapping("/forgotPassword")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request, Model model) {
        String path = request.getRequestURL().toString();
        path = path.replace(request.getServletPath(), "");
        Boolean result = registrationService.sendEmailGetPassword(email, path);
        if(!result){
            model.addAttribute("ERROR", "Email not exist");
            return "ResetPasswordEmail";
        }
        model.addAttribute("ERROR", "Check your email to access");
        return "ResetPasswordEmail";
    }

    @GetMapping("/resetPassword")
    public String resetPassword(@Param("username") String username, Model model) {
        model.addAttribute("username", username);
        return "ResetPassword";
    }

    @PostMapping("/resetPassword")
    public String processResetPassword(@RequestParam("username") String username, @RequestParam("confirmPassword") String confirmPassword) {
        registrationService.updatePassword(username, confirmPassword);
        return "redirect:/login";
    }


    @GetMapping("/customer/profile")
    public String profile_view(Model model, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        homeService.profileView(model, userDetails.getUsername());


        return "Profile";
    }

    @PostMapping("/customer/updateprofile")
    public String updateprofile(@Valid @ModelAttribute("user") User customer, BindingResult bindingResult,  RedirectAttributes redirectAttributes, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        homeService.updateProfile(userDetails.getUsername(), customer, bindingResult);


        return "redirect:/customer/profile";
    }

    @GetMapping("/customer/changepass")
    public String changepass_view(Model model, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        homeService.cartBarStatus(userDetails.getUsername(), model);

        model.addAttribute("pass", new PassChange());


//        homeService.cartBarStatus( Currentuser, model);


        return "ChangePassword";
    }

    @PostMapping("/customer/changepassimpl")
    public String updatepass(@Valid @ModelAttribute("pass") PassChange passChange, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }
        Boolean check = homeService.updatePass(userDetails.getUsername(), passChange, bindingResult);

        if(!check){
            homeService.cartBarStatus(userDetails.getUsername(), model);
            return "ChangePassword";
        }


        return "redirect:/customer/profile";
    }


    @GetMapping("/customer/check-out")
    public String checkout_view(Model model, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }


        homeService.checkOutView(model, userDetails.getUsername());


        return "CheckOut";
    }

    @PostMapping("/customer/addtocart")
    public String addtocart(Model model, @RequestParam("id") Long id, @RequestParam("quantity") int quantity, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }
        homeService.addToCart(model, userDetails.getUsername(), id, quantity);
        return "redirect:/customer/home";
    }

    @PostMapping("/customer/updateShoppingCart")
    public String updateCartItem(@RequestParam("item_id") Long id, @RequestParam("quantity") int quantity, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        homeService.updateCart(id, quantity);

        return "redirect:/customer/check-out";
    }

    @GetMapping("/customer/removeItem/{id}")
    public String removeItem(@PathVariable("id") Long id, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        homeService.removeItem(id, userDetails.getUsername());

        return "redirect:/customer/check-out";
    }

    @GetMapping("/customer/check-out-infor")
    public String checkoutinfor_view(Model model, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }

        boolean check = homeService.checkOutInforView(model, userDetails.getUsername());

        if(!check){
            return "CartEmpty";

        }

        return "CheckOutInfor";
    }

    @PostMapping("/customer/place-order")
    public String placeOrder(@RequestParam("cart_id") Long id, @ModelAttribute("user") CheckOutInfor checkOutInfor, @RequestParam("phonenumber") String phonenumber, @RequestParam("payment_method") String pMethod, @RequestParam("amount") Double orderTotal,
                             @RequestParam("orderInfo") String orderInfo,  HttpServletRequest request , Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        if(pMethod.equals("COD")){
            homeService.placeOrder(id, checkOutInfor, phonenumber, userDetails.getUsername(), false);
        }else if (pMethod.equals("Online")){
            GlobalData.cartIdTemp = id;
            GlobalData.checkOutInforTemp = checkOutInfor;
            GlobalData.phonenumberTemp = phonenumber;
            Double total = orderTotal * 24000;
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(total.intValue(), orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }

        return "redirect:/customer/order-list";
    }

    @GetMapping("/customer/order-list")
    public String orderlist_view(Model model, Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
            model.addAttribute("user", userDetails);
        }
        homeService.orderListView(model, userDetails.getUsername());
        return "OrderList";
    }

    @GetMapping("/customer/trackOrder/{id}")
    public String trackOrder(@PathVariable("id") Long id,  Principal principal){

        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }

        return "redirect:/order-list";
    }

    @PostMapping("/update-category")
    public String updateCategory(@RequestParam("category") String category, Model model){


        if(category.equals("null")){
            GlobalData.CurrentCategory = null;
            GlobalData.Currentkeyword = null;
        }else{
            GlobalData.CurrentCategory = category;
            GlobalData.Currentkeyword = null;
        }

        return "redirect:/home#menu";
    }

    @PostMapping("/update-rating")
    public String updateRatingTime(@RequestParam("time") String time, Model model){



        GlobalData.CurrentRatingTime = time;



        return "redirect:/home#menu";
    }

    @PostMapping("/customer/delivery-rating")
    public String updateDeliveryRating(@RequestParam("rate") Double rate, @RequestParam("id") Long orderId, Model model){

        homeService.deliveryRating(rate, orderId);

        homeService.ratingDeliveryPeron();



        return "redirect:/customer/order-list";
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
    public String GetMapping(HttpServletRequest request, Model model, Principal principal){
        UserDetails userDetails = null;
        if(principal != null) {
            userDetails = userDetailsService.loadUserByUsername(principal.getName());
        }
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
            homeService.placeOrder(GlobalData.cartIdTemp, GlobalData.checkOutInforTemp, GlobalData.phonenumberTemp, userDetails.getUsername(), true);
            GlobalData.cartIdTemp = null;
            GlobalData.checkOutInforTemp = null;
            GlobalData.phonenumberTemp = null;
            return "ordersuccess";
        }

        return "orderfail";
    }
    @PostMapping("/search")
    public String searchProduct(@RequestParam("keyword") String keyword){

        GlobalData.Currentkeyword = keyword;
        GlobalData.CurrentCategory = null;

        return "redirect:/home#menu";
    }

    @PostMapping("/customer/search")
    public String searchProductCustomer(@RequestParam("keyword") String keyword){

        GlobalData.Currentkeyword = keyword;
        GlobalData.CurrentCategory = null;

        return "redirect:/customer/home#menu";
    }


    @PostMapping("/customer/update-category")
    public String updateCategoryCustomer(@RequestParam("category") String category, Model model){


        if(category.equals("null")){
            GlobalData.CurrentCategory = null;
            GlobalData.Currentkeyword = null;
        }else{
            GlobalData.CurrentCategory = category;
            GlobalData.Currentkeyword = null;
        }

        return "redirect:/customer/home#menu";
    }

    @PostMapping("/customer/update-rating")
    public String updateRatingTimeCustomer(@RequestParam("time") String time, Model model){



        GlobalData.CurrentRatingTime = time;



        return "redirect:/customer/home#menu";
    }

    @GetMapping("/error")
    public String error(){


        return "NoPermissionAdmin";
    }







}
