package com.vietphat.newswave.controller.auth;

import com.vietphat.newswave.dto.ResetPasswordDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.service.EmailService;
import com.vietphat.newswave.service.ForgotPasswordService;
import com.vietphat.newswave.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/xac-thuc")
public class AuthController {

    private UserService userService;

    private EmailService emailService;

    private ForgotPasswordService forgotPasswordService;


    @Autowired
    public AuthController(UserService userService, EmailService emailService, ForgotPasswordService forgotPasswordService) {

        this.userService = userService;
        this.emailService = emailService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @GetMapping("/dang-nhap")
    public String loginPage(@RequestParam(name = "incorrectCredentials", required = false) String incorrectCredentials,
                            @RequestParam(name = "logout", required = false) String logout,
                            Model model) {

        if (incorrectCredentials != null && incorrectCredentials.equals("true")) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Tài khoản hoặc mật khẩu chưa chính xác");
        }

        if (logout != null && logout.equals("true")) {
            model.addAttribute("alert", "success");
            model.addAttribute("message", "Đăng xuất thành công");
        }

        return "views/auth/login.html";
    }

    @GetMapping("/dang-ky")
    public String signupPage(Model model) {

        model.addAttribute("user", new UserRegistrationDTO());

        return "views/auth/signup.html";
    }

    @PostMapping("/dang-ky")
    public String signup(@Valid @ModelAttribute("user") UserRegistrationDTO userRegistrationDTO,
                         BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/auth/signup.html";
        }

        UserEntity user = userService.register(userRegistrationDTO);
        if (user != null) {
            redirectAttributes.addFlashAttribute("alert", "success");
            redirectAttributes.addFlashAttribute("message", "Đăng ký tài khoản thành công!");
            redirectAttributes.addFlashAttribute("username", user.getUsername());
        }

        return "redirect:/xac-thuc/dang-nhap";
    }

    @GetMapping("/tu-choi-truy-cap")
    public String accessDeniedPage() {

        return "views/auth/access-denied.html";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}
