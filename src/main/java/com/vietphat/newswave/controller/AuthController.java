package com.vietphat.newswave.controller;

import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/xac-thuc")
public class AuthController {

    private UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dang-nhap")
    public String loginPage() {

        return "views/auth/login.html";
    }

    @GetMapping("/dang-ky")
    public String signupPage(Model model) {

        model.addAttribute("user", new UserRegistrationDTO());

        return "views/auth/signup.html";
    }

    @PostMapping("/dang-ky")
    public String signup(@Valid @ModelAttribute("user") UserRegistrationDTO userRegistrationDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "views/auth/signup.html";
        }

        UserEntity user = userService.register(userRegistrationDTO);
        if (user != null) {

            // TODO: SET USERNAME

            return "views/auth/login.html";
        }

        return ""; // TODO: ADD SERVER ERROR PAGE
    }

    @GetMapping("/quen-mat-khau")
    public String forgotPasswordPage() {

        return "views/auth/forgot-password.html";
    }

    @GetMapping("/dang-xuat")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {

            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/dang-nhap";
    }

    @GetMapping("/tu-choi-truy-cap")
    public String accessDeniedPage() {

        return "views/auth/access-denied.html";
    }
}
