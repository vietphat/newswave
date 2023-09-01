package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.ResetPasswordDTO;
import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends UniqueFieldService {

    UserDTO findByUsernameAndStatus(String username, UserStatus userStatus);

    UserEntity findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity register(UserRegistrationDTO userDTO);

    void delete(UserEntity entity);

    UserEntity resetPassword(UserEntity user, ResetPasswordDTO resetPasswordDTO);

    UserDTO findById(Long id);

    UserEntity findByIdAndStatus(Long id, UserStatus status);

    UserDTO findAll(Pageable pageable);

    Long getTotalItems();
}
