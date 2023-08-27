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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/quen-mat-khau")
    public String forgotPassword(@RequestParam("email") String email, Model model) {
        if (email.isEmpty()) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Vui lòng nhập email.");
            return "views/auth/forgot-password.html";
        }

        // 1. kiểm tra email nhập vào có tồn tại trong hệ thống hay không
        UserEntity user = userService.findByEmailAndStatus(email, UserStatus.ACTIVE);

        if (user == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Không tìm thấy email trong hệ thống.");
            return "views/auth/forgot-password.html";
        }

        // 2. kiểm tra email nhập vào đã có reset password token hay chưa
        // nếu có -> xóa token cũ
        ResetPasswordTokenEntity oldToken = forgotPasswordService.findResetPasswordTokenByUserId(user.getId());
        if (oldToken != null) {
            forgotPasswordService.deleteResetPasswordToken(oldToken);
        }

        // 2. tạo token -> lưu token vào db và gửi mail
        ResetPasswordTokenEntity token  = forgotPasswordService.createToken(user);

        // lưu token vào database
        token = forgotPasswordService.saveToken(token);

        // gửi mail
        if (token == null || !emailService.sendResetPasswordUrl(token, user)) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Có lỗi hệ thống trong quá trình gửi mail.");
            return "views/auth/forgot-password.html";
        }

        model.addAttribute("alert", "success");
        model.addAttribute("message", "Chúng tôi đã gửi một liên kết đặt lại mật khẩu đến email của bạn.");
        return "views/auth/forgot-password.html";
    }

    @GetMapping("/tao-lai-mat-khau")
    public String resetPasswordPage(Model model,
                                    @RequestParam(value = "token", required = false) String tokenStr,
                                    @RequestParam(value = "userId", required = false) String userId) {

        model.addAttribute("token", tokenStr);
        model.addAttribute("userId", userId);
        model.addAttribute("user", new ResetPasswordDTO());
        return "views/auth/reset-password";
    }

    @PostMapping("/tao-lai-mat-khau")
    public String resetPassword(@Valid @ModelAttribute("user") ResetPasswordDTO resetPasswordDTO,
                                BindingResult bindingResult,
                                @RequestParam(value = "token", required = false) String tokenStr,
                                @RequestParam(value = "userId", required = false) String userId,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return "views/auth/reset-password";
        }

        ResetPasswordTokenEntity resetPasswordToken = forgotPasswordService.findResetPasswordTokenByToken(tokenStr);
        UserEntity user = userService.findByIdAndStatus(Long.parseLong(userId), UserStatus.ACTIVE);

        // kiểm tra user có tồn tại hay không
        if (user == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Tài khoản không tồn tại hoặc đã bị chặn.");
            return "views/auth/reset-password";
        }

        // kiểm tra token có hợp lệ không
        boolean tokenIsValid = forgotPasswordService.validateToken(resetPasswordToken, user);
        if (!tokenIsValid) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Token không hợp lệ hoặc đã quá hạn.");
            return "views/auth/reset-password";
        }

        // lưu mật khẩu mới
        userService.resetPassword(user, resetPasswordDTO);

        // TODO: set username
        return "views/auth/login";
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
