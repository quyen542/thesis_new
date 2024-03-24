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

    @Override
    public boolean checkUser(User Currentuser){
        boolean check = true;
        if(Currentuser!= null){
            System.out.println(Currentuser.getRole().getName());
            if(!Currentuser.getRole().getName().equals("customer")) {
                check = false;
            }
        }
        else {
            check = false;
        }
        return check;
    }

    @Override
    public void ratingFood(Food food){
        if(food.getRating() < 5.0){
            double countQuantity = orderItemRepository.countOrderItemByFoodId(food.getId());
            double increase =  Math.floor(countQuantity/3);
            double check = countQuantity/3 - increase;
            if( check > 0.5){
                increase += 0.5;
            }

            double countLike = food.getLikes().size();
            check = countLike;
            while (check < 1){
                increase += 0.5;
                check = check / 3;
            }

            food.setRating(food.getRating() + increase);

            if(food.getRating() > 5){

            }

        }
    }

    @Override
    public void likeFood(Model model, Long id, User currentuser){
        Optional<User> user = userRepository.findById(currentuser.getId());
        Food food = foodRespository.findfoodById(id);

        user.get().getLikedFood().add(food);
        food.getLikes().add(user.get());

        userRepository.save(user.get());
        foodRespository.save(food);
    }

    @Override
    public void unlikeFood(Model model, Long id, User currentuser){
        Optional<User> user = userRepository.findById(currentuser.getId());
        Food food = foodRespository.findfoodById(id);

        user.get().getLikedFood().remove(food);
        food.getLikes().remove(user.get());

        userRepository.save(user.get());
        foodRespository.save(food);
    }

    public void dislikeFood(Model model, Long id, User currentuser){
        Optional<User> user = userRepository.findById(currentuser.getId());
        Food food = foodRespository.findfoodById(id);

        user.get().getDislikedFood().add(food);
        food.getDislikes().add(user.get());

        userRepository.save(user.get());
        foodRespository.save(food);
    }

    @Override
    public void undislikeFood(Model model, Long id, User currentuser){
        Optional<User> user = userRepository.findById(currentuser.getId());
        Food food = foodRespository.findfoodById(id);

        user.get().getDislikedFood().remove(food);
        food.getDislikes().remove(user.get());

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
    public void homeSetup(Model model, User Currentuser, String currentCategory){
        if(Currentuser != null){
            Optional<User> newuser = userRepository.findById(Currentuser.getId());
            model.addAttribute("currentuser", newuser.get());

            if(Currentuser.getRole().getName().equals("customer")) {
                Cart check = cartrepo.findByUserID(Currentuser.getId());
                if (check == null) {
                    model.addAttribute("Cart", new Cart());
                } else {
                    model.addAttribute("Cart", check);
                }
            }
        }

        List<Long> topListID = orderItemRepository.getListFoodIdDesc();

        List<Food> topList = new ArrayList<>();

        for(Long id: topListID){
            topList.add(foodRespository.findfoodById(id));
        }

        List<Food> listFoods = new ArrayList<>();

        if(currentCategory != null){
            listFoods = foodRespository.findByCategory(currentCategory);
        }else{
            listFoods = foodRespository.findAll();
        }

        List<String> categories = foodRespository.getFoodCategory();

        model.addAttribute("topList", topList);
        model.addAttribute("listFoods", listFoods);
        model.addAttribute("listCategories", categories);

    }

    @Override
    public void profileView(Model model, User Currentuser ){
        Cart check = cartrepo.findByUserID(Currentuser.getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }

        User user = new User();
        user.setName(Currentuser.getName());
        user.setEmail(Currentuser.getEmail());
        user.setPassword(Currentuser.getPassword());
        user.setRepassword(Currentuser.getRepassword());
        user.setPhonenumber(Currentuser.getPhonenumber());
        user.setAddress(Currentuser.getAddress());
        model.addAttribute("user", user);

    }

    @Override
    public User updateProfile( User Currentuser, User update, BindingResult bindingResult ){
        System.out.println(update.getLat());
        Optional<User> u = userRepository.findByUsername(Currentuser.getUsername());

        u.get().setName(update.getName());
        u.get().setEmail(update.getEmail());
        u.get().setAddress(update.getAddress());
        u.get().setPhonenumber(update.getPhonenumber());
        u.get().setLat(update.getLat());
        u.get().setLon(update.getLon());


        bindingResult.addError(new FieldError("user", "Repassword", "Update successful"));



        userRepository.save(u.get());

        return  u.get();
    }

    @Override
    public void cartBarStatus(User Currentuser, Model model){
        Cart check = cartrepo.findByUserID(Currentuser.getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }
    }

    @Override
    public Boolean updatePass(User Currentuser, PassChange passChange, BindingResult bindingResult){
        Optional<User> u = userRepository.findByUsername(Currentuser.getUsername());

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
    public void checkOutView(Model model, User Currentuser ){
        Cart check = cartrepo.findByUserID(Currentuser.getId());
        if(check == null){
            model.addAttribute("Cart", new Cart() );
        }
        else{
            model.addAttribute("Cart", check );
        }
    }

    @Override
    public void addToCart(Model model, User Currentuser, Long id, int quantity ){
        Cart check = cartrepo.findByUserID(Currentuser.getId());
        Optional<User> user = userRepository.findByUsername(Currentuser.getUsername());
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
    public void removeItem(Long id, User CurrentUser){
        Cart cart = cartrepo.findByUserID(CurrentUser.getId());
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
    public boolean checkOutInforView(Model model, User Currentuser){
        model.addAttribute("user", Currentuser);
        Cart check = cartrepo.findByUserID(Currentuser.getId());
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
    public void placeOrder(Long id, CheckOutInfor checkOutInfor,String phonenumber, User Currentuser){


        Cart cart = cartrepo.findcartById(id);
        Optional<User> user = userRepository.findByUsername(Currentuser.getUsername());
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
    public void orderListView(Model model, User Currentuser){
        Optional<User> user = userRepository.findByUsername(Currentuser.getUsername());
        System.out.println(user.get().toString());
        model.addAttribute("user", user.get());
    }

    @Override
    public void trackOrder(Long id){
//        Currentuser = repo.findByEmail(Currentuser.getEmail());

    }

}
