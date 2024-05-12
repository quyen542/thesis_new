package com.example.thesis_new.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var roles = authorities.stream().map(r -> r.getAuthority()).findFirst();

        if (roles.orElse("").equals("admin")) {
            response.sendRedirect("/admin/admin-dashboard");
        } else if (roles.orElse("").equals("customer")) {
            response.sendRedirect("/customer/home");
        }else if (roles.orElse("").equals("delivery")) {
            response.sendRedirect("/delivery/delivery-dashboard");
        } else {
            response.sendRedirect("/error");
        }
    }
}
