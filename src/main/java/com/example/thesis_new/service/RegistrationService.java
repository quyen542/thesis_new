package com.example.thesis_new.service;


import com.example.thesis_new.dto.registerDTO;
import com.example.thesis_new.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


public interface RegistrationService {

    Boolean registAccount(registerDTO user, HttpServletRequest request, Model model);

    void sendEmail(User user, String path);

    boolean verifyAccount(String verificationCode);

    boolean sendEmailGetPassword(String email, String path);

    User updatePassword(String username, String confirmPassword);

}
