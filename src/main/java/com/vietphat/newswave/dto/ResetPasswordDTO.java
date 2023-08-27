package com.vietphat.newswave.dto;

import com.vietphat.newswave.validation.PasswordMatches;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches
public class ResetPasswordDTO {

    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")
    private String password;

    @NotBlank(message = "Mật khẩu xác nhận là bắt buộc")
    private String confirmPassword;

    public ResetPasswordDTO() {
    }

    public ResetPasswordDTO(String password, String confirmPassword) {
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
