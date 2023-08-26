package com.vietphat.newswave.dto;

import com.vietphat.newswave.enums.UserStatus;

import java.util.Date;
import java.util.List;

public class UserDTO extends BaseDTO {

    private String fullName;

    private String email;

    private String phoneNumber;

    private Date dateOfBirth;

    private String address;

    private String username;

    private String password;

    private Date lastLogin;

    private UserStatus status;

    private List<RoleDTO> roles;

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

}
