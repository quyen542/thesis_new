package com.example.thesis_new.service;


import com.example.thesis_new.entity.User;
import org.springframework.validation.BindingResult;


public interface RegistrationService {

    Boolean registAccount(User user, BindingResult bindingResult);

}
