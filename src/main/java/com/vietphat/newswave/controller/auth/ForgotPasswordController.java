package com.vietphat.newswave.controller.auth;

import com.vietphat.newswave.dto.user.ResetPasswordDTO;
import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.service.EmailService;
import com.vietphat.newswave.service.ForgotPasswordService;
import com.vietphat.newswave.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/xac-thuc")
public class ForgotPasswordController {

    private UserService userService;

    private ForgotPasswordService forgotPasswordService;

    private EmailService emailService;

    @Autowired
    public ForgotPasswordController(UserService userService,
                                    ForgotPasswordService forgotPasswordService,
                                    EmailService emailService) {

        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
        this.emailService = emailService;
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
        ResetPasswordTokenEntity token = forgotPasswordService.createToken(user);

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

    @GetMapping("/tao-lai-mat-khau/{tokenStr}")
    public String resetPasswordPage(Model model, @PathVariable String tokenStr) {

        model.addAttribute("user", new ResetPasswordDTO());
        return "views/auth/reset-password";
    }

    @PostMapping(value = "/tao-lai-mat-khau/{tokenStr}")
    public String resetPassword(@Valid @ModelAttribute("user") ResetPasswordDTO resetPasswordDTO,
                                BindingResult bindingResult, @PathVariable String tokenStr,
                                Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "views/auth/reset-password";
        }

        ResetPasswordTokenEntity resetPasswordToken = forgotPasswordService.findResetPasswordTokenByToken(tokenStr);
        // kiểm tra token có hợp lệ không
        boolean tokenIsValid = forgotPasswordService.validateToken(resetPasswordToken);
        if (!tokenIsValid) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Token không hợp lệ hoặc đã quá hạn.");
            return "views/auth/reset-password";
        }

        UserEntity user = userService.findByIdAndStatus(resetPasswordToken.getUser().getId(), UserStatus.ACTIVE);
        // kiểm tra user có tồn tại hay không
        if (user == null) {
            model.addAttribute("alert", "danger");
            model.addAttribute("message", "Tài khoản không tồn tại hoặc đã bị chặn.");
            return "views/auth/reset-password";
        }

        // lưu mật khẩu mới
        resetPasswordDTO.setId(user.getId());
        userService.resetPassword(resetPasswordDTO);

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "Tạo lại mật khẩu thành công");
        redirectAttributes.addFlashAttribute("username", user.getUsername());

        return "redirect:/xac-thuc/dang-nhap";
    }
}
