package com.vietphat.newswave.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/xac-thuc")
public class AuthController {

    @RequestMapping("/dang-nhap")
    public String loginForm() {

        return "views/auth/login-form.html";
    }

    @RequestMapping("/dang-ky")
    public String signupForm() {

        return "views/auth/signup-form.html";
    }

    @RequestMapping("/dang-xuat")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/dang-nhap";
    }

    @RequestMapping("/tu-choi-truy-cap")
    public String accessDenied() {

        return "views/auth/access-denied.html";
    }
}
