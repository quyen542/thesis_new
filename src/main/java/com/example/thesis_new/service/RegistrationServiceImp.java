package com.example.thesis_new.service;


import com.example.thesis_new.dto.registerDTO;
import com.example.thesis_new.entity.Role;
import com.example.thesis_new.entity.User;
import com.example.thesis_new.repository.RoleRepository;
import com.example.thesis_new.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Optional;
import java.util.UUID;


@Service
public class RegistrationServiceImp implements RegistrationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JavaMailSender mailSender;



    @Override
    public Boolean registAccount(registerDTO user, HttpServletRequest request, Model model) {


        if(user.getUsername() != null){
            String Username = user.getUsername();
            Optional<User> check = userRepository.findByUsername(Username);
            if(check.isPresent()){
                model.addAttribute("ERROR", "Username already existed!");
                return false;

            }
        }

        if(user.getEmail() != null){
            User check = userRepository.findByEmail(user.getEmail());
            if(check != null){
                model.addAttribute("ERROR", "email already existed!");
                return false;

            }
        }

        if(user.getPassword() != null && user.getRepassword() != null){
            if(!user.getPassword().equals(user.getRepassword())){
                model.addAttribute("ERROR", "password not match");
                return false;

            }
        }

        user.setVerificationCode(UUID.randomUUID().toString());
        String path = request.getRequestURL().toString();
        path = path.replace(request.getServletPath(), "");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        User newuser = new User();
        newuser.setUsername(user.getUsername());
        newuser.setName(user.getName());
        newuser.setPassword(user.getPassword());
        newuser.setEmail(user.getEmail());
        newuser.setConfirm(user.isConfirm());
        newuser.setVerificationCode(user.getVerificationCode());
        newuser.setRole(roleRepository.findByName(user.getRole()).get());

        userRepository.save(newuser);
        sendEmail(newuser, path);
        return true;
    }
    @Override
    public void sendEmail(User user, String path) {
        String from = "bistrorestaurant2024@gmail.com";
        String to = user.getEmail();
        String subject = "Account Verification";
        String content = "Dear [[name]],<br>" + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>" + "Thank you,<br>" + "Bistro Restaurant";

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(from, "Bistro Restaurant");
            helper.setTo(to);
            helper.setSubject(subject);

            content = content.replace("[[name]]", user.getUsername());
            String siteUrl = path + "/verify?code=" + user.getVerificationCode();


            content = content.replace("[[URL]]", siteUrl);

            helper.setText(content, true);

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean verifyAccount(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null) {
            return false;
        } else {

            user.setConfirm(true);
            user.setVerificationCode(null);

            userRepository.save(user);

            return true;
        }
    }

    @Override
    public boolean sendEmailGetPassword(String email, String path) {
        User user = userRepository.findByEmail(email);
        if(user == null){
            return false;
        }
        if (user != null) {
            String from = "bistrorestaurant2024@gmail.com";
            String to = email;
            String subject = "Reset Password";
            String content = "Dear [[name]],<br>" + "Please click the link below to reset your password:<br>"
                    + "<h3><a href=\"[[URL]]\" target=\"_self\">RESET PASSWORD</a></h3>" + "Thank you,<br>" + "Bistro Restaurant";

            try {

                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                helper.setFrom(from, "Bistro Restaurant");
                helper.setTo(to);
                helper.setSubject(subject);

                content = content.replace("[[name]]", user.getUsername());
                String siteUrl = path + "/resetPassword?username=" + user.getUsername();


                content = content.replace("[[URL]]", siteUrl);

                helper.setText(content, true);

                mailSender.send(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public User updatePassword(String username, String confirmPassword) {
        User existingUser = userRepository.findByUsername(username).get();
        existingUser.setPassword(BCrypt.hashpw(confirmPassword, BCrypt.gensalt()));
        return userRepository.save(existingUser);
    }

}
