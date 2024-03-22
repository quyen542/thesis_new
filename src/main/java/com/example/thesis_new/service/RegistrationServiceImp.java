package com.example.thesis_new.service;


import com.example.thesis_new.entity.Role;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.repository.RoleRepository;
import com.example.thesis_new.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;


@Service
public class RegistrationServiceImp implements RegistrationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;




    @Override
    public Boolean registAccount(User user, BindingResult bindingResult) {

        if(user.getUsername() != null){
            String Username = user.getUsername();
            Optional<User> check = userRepository.findByUsername(Username);
            if(check.isPresent()){
                bindingResult.addError(new FieldError("user", "username", "username is exist"));
            }
        }

        if(user.getPassword() != null && user.getRepassword() != null){
            if(!user.getPassword().equals(user.getRepassword())){
                bindingResult.addError(new FieldError("user", "Repassword", "Password not match"));
            }
        }

        if(bindingResult.hasErrors()){
            return false;
        }

        Optional<Role> role = roleRepository.findByName("customer");
        user.setRole(role.get());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        userRepository.save(user);
        System.out.println("check on");
        return true;
    }
}
