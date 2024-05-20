package com.example.thesis_new.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.thesis_new.dto.NewDeliveryPerson;
import com.example.thesis_new.entity.*;
import com.example.thesis_new.repository.*;
import com.example.thesis_new.util.FileUploadUtil;
import com.fasterxml.jackson.databind.ext.SqlBlobSerializer;
import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FoodRepository foodRespository;

    @Autowired
    private OrderRepository orderRespository;

    @Autowired
    private DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string}")
    private String connectionString;

    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void init(){
        System.out.println(connectionString);
        blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(connectionString)
                .buildClient();

    }


    @Override
    public void adminDashboardSetup(Model model){

        List<Long> topListID = orderItemRepository.getListFoodIdDesc();

        List<Food> topList = new ArrayList<>();

        if(!topListID.isEmpty()) {
            for (Long id: topListID) {
                topList.add(foodRespository.findfoodById(id));
            }
        }


        model.addAttribute("topList", topList);

        model.addAttribute("count", userRepository.countByRoleid(2));
        model.addAttribute("fcount", foodRespository.countByNotDelete());
        model.addAttribute("ocount", orderRespository.count());
    }

    @Override
    public void getCustomerList(Model model){
        List<User> listUsers = userRepository.getUserByRoleid(2);
        model.addAttribute("listUsers", listUsers);
    }

    @Override
    public void getFoodList(Model model, String keyword){
        List<Food> listFoods = null;
        if(keyword == null){
             listFoods = foodRespository.findAllDesc();
        }else{
            listFoods = foodRespository.search(keyword);
        }
        Food food = new Food();
        model.addAttribute("food", food);
        model.addAttribute("listFoods", listFoods);
    }

    @Override
    public boolean addNewFood(Food food, BindingResult bindingResult, MultipartFile multipartFile ) throws IOException {

        if(food.getName() != null){
            String name = food.getName();
            Food check = foodRespository.findByName(name);
            if(check != null){
                bindingResult.addError(new FieldError("food", "name", "Food name is exist"));
            }
        }

        if(bindingResult.hasErrors()){
            return false;
        }

        food.setAvgRating(5);

//        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//        food.setPhotos(fileName);
//
//        Food savedFood = foodRespository.save(food);
//
//
//        String uploadDir = "src/main/resources/static/food-photos/" + savedFood.getId();
//
//        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        String blobFilename = multipartFile.getOriginalFilename();
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(blobFilename);

        blobClient.upload(multipartFile.getInputStream(), multipartFile.getSize(), true );

        String imgUrl = blobClient.getBlobUrl();

        food.setPhotos(blobFilename);

        food.setImageUrl(imgUrl);

        Food savedFood = foodRespository.save(food);


        return true;

    }

    @Override
    public void deleteFood(Long id) throws IOException {

        Food deletefood = foodRespository.findfoodById(id);

        List<OrderItem> orderItemList = orderItemRepository.getOrderItemByFoodId(id);

        if(orderItemList.isEmpty()){
            BlobClient blobClient = blobServiceClient.getBlobContainerClient(containerName).getBlobClient(deletefood.getPhotos());
            blobClient.delete();
            foodRespository.delete(deletefood);
        }else{
            deletefood.setIs_delete(true);
            foodRespository.save(deletefood);
        }

    }

    @Override
    public void editFoodView(Long id, Model model){
        Food editFood = foodRespository.findfoodById(id);

        model.addAttribute("food", editFood);
    }

    @Override
    public boolean editFood(Food food, BindingResult bindingResult){

        if(food.getName() != null){
            String name = food.getName();
            Food check = foodRespository.findByNameAndNotId(name, food.getId());
            if(check != null){
                bindingResult.addError(new FieldError("food", "name", "Food name is exist"));
            }
        }

        if(bindingResult.hasErrors()){
            return false;
        }

        Food editFood = foodRespository.findfoodById(food.getId());

        editFood.setName(food.getName());
        editFood.setCategory(food.getCategory());
        editFood.setPrice(food.getPrice());
        editFood.setDescription(food.getDescription());

        foodRespository.save(editFood);

        return true;
    }


    @Override
    public void adminOrderListView(Model model){
        List<Order> listOrder = orderRespository.getOrderListDesc();
        model.addAttribute("listOrder", listOrder);
        List<User> listUser = userRepository.getAvailableDelivery();
        model.addAttribute("listName", listUser );
    }

    @Override
    public void updateStatus(Long id, String status){
        Order order = orderRespository.findById(id).get();
        if(status.equals("Confirmed")){
            order.setStatus(Order.Status.Confirmed);
        }else if(status.equals("Prepared")){
            order.setStatus(Order.Status.Prepared);
        } else if (status.equals("Shipped")) {
            order.setStatus(Order.Status.Shipped);
        } else if (status.equals("Delivered")) {
            User user = userRepository.findById(order.getDeliveryPerson().getId()).get();
            user.getDeliveryInfo().setAvailable(true);
            user.getDeliveryInfo().setSalary(user.getDeliveryInfo().getSalary() + 2.0);
            userRepository.save(user);
            order.setStatus(Order.Status.Delivered);
        }

        orderRespository.saveAndFlush(order);
    }

    @Override
    public void updateDeliveryPerson(Long id, Long deliveryId){
        Order order = orderRespository.findById(id).get();
        User user = userRepository.findById(deliveryId).get();

        order.setDeliveryPerson(user);

        order.setStatus(Order.Status.Shipped);

        user.getDeliveryInfo().setAvailable(false);

        orderRespository.saveAndFlush(order);
        userRepository.saveAndFlush(user);

    }

    @Override
    public void getDeliveryPersonList(Model model){
        List<User> listUsers = userRepository.getUserByRoleid(3);
        System.out.println(listUsers);
        model.addAttribute("listDelivery", listUsers);
    }

    @Override
    public boolean addDeliveryPerson(NewDeliveryPerson user, BindingResult bindingResult ){

        if(user.getUsername() != null){
            String Username = user.getUsername();
            Optional<User> check = userRepository.findByUsername(Username);
            if(check.isPresent()){
                bindingResult.addError(new FieldError("user", "username", "username is exist"));
                return false;
            }
        }

        if(user.getPassword() != null && user.getRepassword() != null){
            if(!user.getPassword().equals(user.getRepassword())){
                bindingResult.addError(new FieldError("user", "Repassword", "Password not match"));
                return false;
            }
        }

        User newuser = new User();

        newuser.setUsername(user.getUsername());
        newuser.setName(user.getName());
        newuser.setPhonenumber(user.getPhonenumber());
        newuser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        Optional<Role> role = roleRepository.findByName("delivery");
        newuser.setRole(role.get());

        userRepository.save(newuser);

        DeliveryInfo deliveryInfo = new DeliveryInfo();
        Optional <User> current = userRepository.findByUsername(newuser.getUsername());
        deliveryInfo.setDeliveryPerson(current.get());

        deliveryInfoRepository.save(deliveryInfo);



        return true;



    }


}
