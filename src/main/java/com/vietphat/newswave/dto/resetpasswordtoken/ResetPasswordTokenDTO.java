package com.vietphat.newswave.dto.resetpasswordtoken;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.dto.user.UserDTO;

import java.util.Date;

public class ResetPasswordTokenDTO extends BaseDTO {

    private String token;

    private Date expiryDate;

    private UserDTO user;

    public ResetPasswordTokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
