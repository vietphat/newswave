package com.vietphat.newswave.dto.user;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.dto.role.RoleDTO;
import com.vietphat.newswave.enums.UserRole;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.service.UserService;
import com.vietphat.newswave.validation.uniquefield.UniqueField;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserDTO extends BaseDTO<UserDTO> {

    @NotNull(message = "Tên đầy đủ là bắt buộc")
    private String fullName;

    @NotNull(message = "Email là bắt buộc")
    @Email(message = "Email không hợp lệ")
    @UniqueField(fieldName = "email", service = UserService.class, message = "Email đã được sử dụng")
    private String email;

    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không hợp lệ")
    @UniqueField(fieldName = "phoneNumber", service = UserService.class, message = "Số điện thoại đã được sử dụng")
    private String phoneNumber;

    private Date dateOfBirth;

    private String address;

    @NotNull(message = "Tên đăng nhập là bắt buộc")
    @UniqueField(fieldName = "username", service = UserService.class, message = "Tên đăng nhập đã được sử dụng")
    @Pattern(regexp = "^(?=.*[a-zA-Z])([a-zA-Z0-9_.-]*[a-zA-Z0-9])?$", message = "Tên đăng nhập chỉ chứa các kí tự cho phép gồm: chữ in hoa, chữ in thường, chữ số (a-z, A-Z, 0-9), dấu gạch dưới, dấu gạch ngang và dấu chấm. Tên đăng nhập phải bắt đầu hoặc kết thúc bằng chữ cái hoặc chữ số và phải chứa ít nhất một chữ cái.")
    private String username;

    @NotNull(message = "Mật khẩu là bắt buộc và không được chứa khoảng cách")
    @Size(min = 4, message = "Mật khẩu phải có ít nhất 4 ký tự")
    private String password;

    private Date lastLogin;

    private UserStatus status;

    @NotEmpty(message = "Hãy chọn ít nhất 1 quyền")
    private List<String> roleCodes = new ArrayList<>();

    private List<RoleDTO> roles = new ArrayList<>();

    private String roleCode;

//    private List<CommentDTO> comments;
//
//    private List<PostDTO> posts;

    public UserDTO() {
    }

    public UserDTO(Long id, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String fullName, String email, String phoneNumber, Date dateOfBirth, String address, String username, String password, Date lastLogin, UserStatus status, List<RoleDTO> roles) {
        super(id, createdBy, createdDate, modifiedBy, modifiedDate);
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.username = username;
        this.password = password;
        this.lastLogin = lastLogin;
        this.status = status;
        this.roles = roles;
//        this.comments = comments;
//        this.posts = posts;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public boolean containsRole(UserRole userRole) {

        boolean result = false;
        for (String roleCode : roleCodes) {
            result = roleCode.equals(userRole.name());
            if (result) {
                break;
            }
        }

        return result;
    }

    public void mapRolesToRoleCodes() {
        if (roles.isEmpty()) {
            return;
        }

        roleCodes = roles.stream().map(roleDTO -> roleDTO.getCode().name()).collect(Collectors.toList());
    }

}
