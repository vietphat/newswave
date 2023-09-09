package com.vietphat.newswave.dto.user;

import com.vietphat.newswave.service.UserService;
import com.vietphat.newswave.validation.passwordmatches.PasswordMatches;
import com.vietphat.newswave.validation.uniquefield.UniqueField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@PasswordMatches
public class UserRegistrationDTO {

    @NotNull(message = "Tên đầy đủ là bắt buộc")
    private String fullName;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    @UniqueField(fieldName = "phoneNumber", service = UserService.class, message = "Số điện thoại đã được sử dụng")
    private String phoneNumber;

    @NotNull(message = "Email là bắt buộc")
    @Email(message = "Emai không hợp lệ")
    @UniqueField(fieldName = "email", service = UserService.class, message = "Email đã được tồn tại")
    private String email;

    @NotNull(message = "Tên đăng nhập là bắt buộc")
    @Size(min = 4, message = "Tên đăng nhập phải có ít nhất 4 ký tự")
    @UniqueField(fieldName = "username", service = UserService.class, message = "Tên đăng nhập đã tồn tại")
    @Pattern(regexp = "^(?=.*[a-zA-Z])([a-zA-Z0-9_.-]*[a-zA-Z0-9])?$", message = "Tên đăng nhập chỉ chứa các kí tự cho phép gồm: chữ in hoa, chữ in thường, chữ số (a-z, A-Z, 0-9), dấu gạch dưới, dấu gạch ngang và dấu chấm. Tên đăng nhập phải bắt đầu hoặc kết thúc bằng chữ cái hoặc chữ số và phải chứa ít nhất một chữ cái.")
    private String username;

    @NotNull(message = "Mật khẩu là bắt buộc")
    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")
    private String password;

    @NotNull(message = "Mật khẩu xác nhận là bắt buộc")
    private String confirmPassword;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String fullName, String phoneNumber, String email, String username, String password, String confirmPassword) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
