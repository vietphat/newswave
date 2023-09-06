package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.user.ResetPasswordDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.dto.user.UserRegistrationDTO;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import org.springframework.data.domain.Pageable;

public interface UserService extends UniqueFieldService {

    UserDTO findUserWithRolesById(Long id);

    UserDTO findUserWithRolesByUsernameAndStatus(String username, UserStatus userStatus);

    UserDTO findByUsernameAndStatus(String username, UserStatus userStatus);

    UserEntity findByEmailAndStatus(String email, UserStatus userStatus);

    UserEntity register(UserRegistrationDTO userDTO);

    void delete(UserEntity entity);

    void delete(String[] userIds);

    UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO);

    UserDTO findById(Long id);

    UserEntity findByIdAndStatus(Long id, UserStatus status);

    UserDTO findAll(Pageable pageable, String search);

    Long getTotalItems();

    UserDTO save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

}
