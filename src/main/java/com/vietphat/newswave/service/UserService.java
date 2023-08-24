package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.UserDTO;

public interface UserService {

    UserDTO findByUsernameAndStatus(String username, Integer status);

}
