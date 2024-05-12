package com.example.thesis_new.service;


import com.example.thesis_new.dto.CheckOutInfor;
import com.example.thesis_new.dto.PassChange;
import com.example.thesis_new.entity.*;
import com.example.thesis_new.repository.*;
import org.springframework.ui.Model;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

@Service
public class HomeServiceImp implements HomeService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CartRepository cartrepo;

    @Autowired
    private FoodRepository foodRespository;

    @Autowired
    private CartItemRepository cartItemRespository;

    @Autowired
    private OrderRepository orderRespository ;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private  FoodRatingRepository foodRatingRepository;


    @Override
    public void ratingFood(String time){
        List<Food> listFoods = new ArrayList<>();
        listFoods = foodRespository.findAll();
        for(Food food: listFoods){
            if(!food.getRatingList().isEmpty()) {
                Double serviceRating = foodRatingRepository.getAvgOfPackagedRating(food.getId());
                Double qualityRating = foodRatingRepository.getAvgOfQualityRating(food.getId());
                Double priceRating = foodRatingRepository.getAvgOfPriceRating(food.getId());
                Double rating = (serviceRating + qualityRating + priceRating) / 3;
                food.setServiceRating((double) Math.round(serviceRating * 10) / 10);
                food.setQualityRating((double) Math.round(qualityRating * 10) / 10);
                food.setPriceRating((double) Math.round(priceRating * 10) / 10);
                food.setAvgRating(rating);
            }else{
                food.setAvgRating(3);
            }
        }

        for(Food food: listFoods) {
            if (food.getAvgRating() <= 5.0 && food.getAvgRating() >= 0.0) {

                double increase = 0;

                if(time.equals("day")) {
                    if (orderItemRepository.countOrderItemByFoodIdInDay(food.getId()) != null) {
                        double countQuantity = orderItemRepository.countOrderItemByFoodIdInDay(food.getId());
                        increase = Math.floor(countQuantity / 3);
                        double checkOrder = countQuantity / 3 - increase;
                        if (checkOrder > 0.5) {
                            increase += 0.5;
                        }
                    }
                }else if(time.equals("week")){
                    if (orderItemRepository.countOrderItemByFoodIdInWeek(food.getId()) != null) {
                        double countQuantity = orderItemRepository.countOrderItemByFoodIdInWeek(food.getId());
                        System.out.println(countQuantity);
                        increase = Math.floor(countQuantity / 21);
                        double checkOrder = countQuantity / 21 - increase;
                        if (checkOrder > 0.5) {
                            increase += 0.5;
                        }
                    }
                }else if(time.equals("month")){
                    if (orderItemRepository.countOrderItemByFoodIdInMonth(food.getId()) != null) {
                        double countQuantity = orderItemRepository.countOrderItemByFoodIdInMonth(food.getId());
                        System.out.println(countQuantity);
                        increase = Math.floor(countQuantity / 90);
                        double checkOrder = countQuantity / 90 - increase;
                        if (checkOrder > 0.5) {
                            increase += 0.5;
                        }
                    }
                }

                double countLike = food.getLikes().size();
                double checkLike = countLike / 3;
                while (checkLike >= 1) {
                    increase += 0.5;
                    checkLike--;
                }

                food.setAvgRating(food.getAvgRating() + increase);

                if (food.getAvgRating() > 5) {
                    double backTo5 = food.getAvgRating() - 5;
                    food.setAvgRating(food.getAvgRating() - backTo5);
                }

                double decrease = 0;

                double countDisLike = food.getDislikes().size();

                double checkDisLike = countDisLike / 3;

                while (checkDisLike >= 1) {
                    decrease -= 0.5;
                    checkDisLike--;
                }

                food.setAvgRating(food.getAvgRating() + decrease);

                if (food.getAvgRating() < 0) {
                    double backTo0 = food.getAvgRating() - 0;
                    food.setAvgRating(food.getAvgRating() - backTo0);
                }

                foodRespository.save(food);
            }
        }
    }

    @Override
    public void likeFood(Model model, Long id, String username, Double quality, Double price, Double packaged){
        Optional<User> user = userRepository.findByUsername(username);
        Food food = foodRespository.findfoodById(id);
        FoodRating foodRating = new FoodRating();

        user.get().getLikedFood().add(food);
        food.getLikes().add(user.get());

        foodRating.setCustomer(user.get());
        foodRating.setFoodRating(food);
        foodRating.setQuality(quality);
        foodRating.setPrice(price);
        foodRating.setService(packaged);

        user.get().getRatingList().add(foodRating);
        food.getRatingList().add(foodRating);


        foodRatingRepository.save(foodRating);
        userRepository.save(user.get());
        foodRespository.save(food);
    }

    @Override
    public void unlikeFood(Model model, Long id, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Food food = foodRespository.findfoodById(id);
        FoodRating foodRating = foodRatingRepository.findByCustomerIdAndFoodId(user.get().getId(), food.getId());


        user.get().getLikedFood().remove(food);
        user.get().getRatingList().remove(foodRating);
        food.getLikes().remove(user.get());
        food.getRatingList().remove(foodRating);


        foodRatingRepository.delete(foodRating);

        userRepository.save(user.get());
        foodRespository.save(food);
    }

    public void dislikeFood(Model model, Long id, String username, Double quality, Double price, Double packaged){
        Optional<User> user = userRepository.findByUsername(username);
        Food food = foodRespository.findfoodById(id);
        FoodRating foodRating = new FoodRating();


        user.get().getDislikedFood().add(food);
        food.getDislikes().add(user.get());

        foodRating.setCustomer(user.get());
        foodRating.setFoodRating(food);
        foodRating.setQuality(quality);
        foodRating.setPrice(price);
        foodRating.setService(packaged);

        user.get().getRatingList().add(foodRating);
        food.getRatingList().add(foodRating);


        foodRatingRepository.save(foodRating);

        userRepository.save(user.get());
        foodRespository.save(food);
    }

    @Override
    public void undislikeFood(Model model, Long id, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Food food = foodRespository.findfoodById(id);
        FoodRating foodRating = foodRatingRepository.findByCustomerIdAndFoodId(user.get().getId(), food.getId());


        user.get().getDislikedFood().remove(food);
        user.get().getRatingList().remove(foodRating);
        food.getDislikes().remove(user.get());
        food.getRatingList().remove(foodRating);

        foodRatingRepository.delete(foodRating);
        userRepository.save(user.get());
        foodRespository.save(food);
    }


    @Override
    public User loginAccount(User user, BindingResult bindingResult) {
        User result = new User();

        if(user.getUsername() != null){
            String username = user.getUsername();
            Optional<User> check = userRepository.findByUsername(username);
            if(check.isPresent()){
                if(!BCrypt.checkpw(user.getPassword(), check.get().getPassword())) {
                    bindingResult.addError(new FieldError("user", "password", "Password not match"));
                    return null;
                }
                result = check.get();
            }
            else{
                bindingResult.addError(new FieldError("user", "username", "username not exist"));
                return null;
            }
        }
        return result;
    }

    @Override
    public void homeSetup(Model model, String username, String currentCategory, String currentKeyword){
        if(username != null){
            Optional<User> newuser = userRepository.findByUsername(username);
            model.addAttribute("currentuser", newuser.get());

            if(newuser.get().getRole().getName().equals("customer")) {
                Cart check = cartrepo.findByUserID(newuser.get().getId());
                if (check == null) {
                    model.addAttribute("Cart", new Cart());
                } else {
                    model.addAttribute("Cart", check);
                }
            }
        }

        List<Food> listFoods = new ArrayList<>();

        if(currentCategory != null){
            listFoods = foodRespository.findByCategory(currentCategory);
        } else if (currentKeyword != null) {
            listFoods = foodRespository.search(currentKeyword);
        } else{
            listFoods = foodRespository.findAllDesc();
        }


        List<Long> topListID = orderItemRepository.getListFoodIdDesc();

        List<Food> topList = new ArrayList<>();

        if(!topListID.isEmpty()) {
            for (Long id : topListID) {
                topList.add(foodRespository.findfoodById(id));
            }
        }


        List<String> categories = foodRespository.getFoodCategory();


        if(currentCategory != null){
            model.addAttribute("keyword", currentKeyword);
        }else{
            model.addAttribute("keyword", " ");
        }
        model.addAttribute("topList", topList);
        model.addAttribute("listFoods", listFoods);
        model.addAttribute("listCategories", categories);

    }

    @Override
    public void profileView(Model model, String username ){
        Optional<User> Currentuser = userRepository.findByUsername(username);

        Cart check = cartrepo.findByUserID(Currentuser.get().getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }

        User user = new User();
        user.setName(Currentuser.get().getName());
        user.setEmail(Currentuser.get().getEmail());
        user.setPassword(Currentuser.get().getPassword());
        user.setRepassword(Currentuser.get().getRepassword());
        user.setPhonenumber(Currentuser.get().getPhonenumber());
        user.setAddress(Currentuser.get().getAddress());
        model.addAttribute("user", user);

    }

    @Override
    public void updateProfile( String username, User update, BindingResult bindingResult ){
        System.out.println(update.getLat());
        Optional<User> u = userRepository.findByUsername(username);

        u.get().setName(update.getName());
        u.get().setEmail(update.getEmail());
        u.get().setAddress(update.getAddress());
        u.get().setPhonenumber(update.getPhonenumber());
        u.get().setLat(update.getLat());
        u.get().setLon(update.getLon());


        bindingResult.addError(new FieldError("user", "Repassword", "Update successful"));



        userRepository.save(u.get());
    }

    @Override
    public void cartBarStatus(String username, Model model){
        Optional<User> Currentuser = userRepository.findByUsername(username);
        Cart check = cartrepo.findByUserID(Currentuser.get().getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }
    }

    @Override
    public Boolean updatePass(String username, PassChange passChange, BindingResult bindingResult){
        Optional<User> u = userRepository.findByUsername(username);

        if(!BCrypt.checkpw(passChange.getCurrentPass(), u.get().getPassword())) {
            bindingResult.addError(new FieldError("pass", "currentPass", "Password is not correct"));
            return false;
        }

        if(passChange.getNewPass() != null && passChange.getRePass() != null){
            if(!passChange.getNewPass().equals(passChange.getRePass())){
                bindingResult.addError(new FieldError("pass", "rePass", "Password not match"));
                return false;
            }
        }


        User user =  u.get();
        user.setRepassword(passChange.getNewPass());
        user.setPassword(BCrypt.hashpw(passChange.getNewPass(), BCrypt.gensalt()));

        System.out.println(user.getRepassword());

        userRepository.save(user);

        return true;

    }

    @Override
    public void checkOutView(Model model, String username ){
        Optional<User> Currentuser = userRepository.findByUsername(username);
        Cart check = cartrepo.findByUserID(Currentuser.get().getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }
    }

    @Override
    public void addToCart(Model model, String username, Long id, int quantity ){

        Optional<User> user = userRepository.findByUsername(username);
        Cart check = cartrepo.findByUserID(user.get().getId());
        if (check == null) {
            Cart cart = new Cart();

            CartItem cartItem = new CartItem();
            cartItem.setQuantity(quantity);
            cartItem.setDate(new Date());
            cartItem.setFood(foodRespository.findfoodById(id));
            cartItem.setTotalPrice(cartItem.getTotalPrice());
            cartItem.setCart(cart);

            cart.getCartItems().add(cartItem);
            cart.setUser(user.get());
            cart.setDate(new Date());
            cartrepo.save(cart);
        } else {
            boolean exist = false;
            Food f = foodRespository.findfoodById(id);
            for (CartItem item : check.getCartItems()) {
                if (f.getId().equals(item.getFood().getId())) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartrepo.save(check);
                    exist = true;
                    break;
                }
            }
            if (exist == false) {
                CartItem cartItem = new CartItem();
                cartItem.setQuantity(quantity);
                cartItem.setDate(new Date());
                cartItem.setFood(foodRespository.findfoodById(id));
                cartItem.setTotalPrice(cartItem.getTotalPrice());
                cartItem.setCart(check);
                check.getCartItems().add(cartItem);
                cartrepo.save(check);
            }
        }
    }

    @Override
    public void updateCart(Long id, int quantity){

        CartItem item =  cartItemRespository.findById(id).get();
        item.setQuantity(quantity);
        cartItemRespository.saveAndFlush(item);
    }

    @Override
    public void removeItem(Long id, String username){
        Optional<User> user = userRepository.findByUsername(username);
        Cart cart = cartrepo.findByUserID(user.get().getId());
        Collection<CartItem> items = cart.getCartItems();
        CartItem cartItem = null;
        for(CartItem item: items){
            if(item.getId().equals(id)){
                cartItem = item;
            }
        }
        items.remove(cartItem);
        cartItemRespository.delete(cartItem);
        cart.setCartItems(items);
        cartrepo.save(cart);
    }

    @Override
    public boolean checkOutInforView(Model model, String username){
        Optional<User> user = userRepository.findByUsername(username);
        model.addAttribute("user", user.get());
        Cart check = cartrepo.findByUserID(user.get().getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
            return false;
        }
        else{
            model.addAttribute("Cart", check );
            if(check.getCartItems().isEmpty()){
                return false;
            }
        }

        return true;
    }

    @Override
    public void placeOrder(Long id, CheckOutInfor checkOutInfor,String phonenumber, String username, boolean is_payment){


        Cart cart = cartrepo.findcartById(id);
        Optional<User> user = userRepository.findByUsername(username);
        Collection<CartItem> items = cart.getCartItems();
        Order order = new Order();
        order.setUser(user.get());
        order.setLat(checkOutInfor.getLat());
        order.setLon(checkOutInfor.getLon());
        order.setDate(new Date());
        order.setDeliveryAddress(checkOutInfor.getAddress());
        order.setPhonenumber(phonenumber);
        order.setName(checkOutInfor.getName());
        order.setTotalPrice(cart.getTotalPrice() + 2);
        order.setIs_payment(is_payment);
        order.setOrderItems(new ArrayList<>());

        for(CartItem item: items){
            OrderItem orderItem= new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItem.setFood(item.getFood());
            orderItem.setTotalPrice(item.getTotalPrice());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }


        orderRespository.save(order);
        user.get().getOrders().add(order);
        userRepository.save(user.get());
        cartItemRespository.deleteByCartId(cart.getId());

    }

    @Override
    public void orderListView(Model model, String username){
        Optional<User> user = userRepository.findByUsername(username);
        System.out.println(user.get().toString());
        model.addAttribute("user", user.get());
    }

    @Override
    public void trackOrder(Long id){
//        Currentuser = repo.findByEmail(Currentuser.getEmail());

    }

    @Override
    public void deliveryRating(Double rate, Long orderID){
        Order order = orderRespository.getById(orderID);
        order.setDeliveryRating(rate);

        orderRespository.save(order);


    }

    @Override
    public void ratingDeliveryPeron(){
        List<User> deliverpersons = userRepository.getUserByRoleid(3);

        for(User person: deliverpersons){

            if(orderRespository.getAvgDeliveryRating(person.getId()) != null){
                Double rate = (double) Math.round(orderRespository.getAvgDeliveryRating(person.getId()) * 10) / 10;
                person.getDeliveryInfo().setRating(rate);
                userRepository.save(person);
            }

        }
    }

    @Override
    public List<Food> searchFood(String keyword){
        if (keyword != null) {
            return foodRespository.search(keyword);
        }
        return foodRespository.findAll();
    }


}
