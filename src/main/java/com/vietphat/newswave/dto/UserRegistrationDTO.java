package com.vietphat.newswave.dto;

import com.vietphat.newswave.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@PasswordMatches
public class UserRegistrationDTO {

    @NotBlank(message = "Tên đầy đủ là bắt buộc")
    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 4, message = "Tên đăng nhập phải có ít nhất 4 ký tự")
    private String username;

    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")
    private String password;

    @NotBlank(message = "Mật khẩu xác nhận là bắt buộc")
    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String fullName, String email, String username, String password, String confirmPassword) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
