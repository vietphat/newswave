package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.enums.UserStatus;

public interface UserService {

    UserDTO findByUsernameAndStatus(String username, UserStatus userStatus);

}
