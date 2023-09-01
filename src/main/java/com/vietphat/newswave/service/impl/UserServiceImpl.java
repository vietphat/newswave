package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.ResetPasswordDTO;
import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.repository.UserRepository;
import com.vietphat.newswave.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ModelMapper modelMapper;

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {

        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findByUsernameAndStatus(String username, UserStatus userStatus) {

        UserEntity user = userRepository.findByUsernameAndStatus(username, userStatus);


        return (user == null) ? null : modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserEntity findByEmailAndStatus(String email, UserStatus userStatus) {
        return userRepository.findByEmailAndStatus(email, userStatus);
    }

    @Override
    public UserEntity register(UserRegistrationDTO userRegistrationDTO) {

        // mã hóa mật khẩu khi đăng ký tài khoản
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);

        return userRepository.save(user);
    }

    @Override
    public void delete(UserEntity entity) {
        userRepository.delete(entity);
    }

    @Override
    @Transactional
    public UserEntity resetPassword(UserEntity user, ResetPasswordDTO resetPasswordDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);

        UserDTO userDTO = modelMapper.map(user.get(), UserDTO.class);

        return userDTO;
    }

    @Override
    public UserEntity findByIdAndStatus(Long id, UserStatus status) {
        UserEntity user = userRepository.findByIdAndStatus(id, status);

        return user;
    }

    @Override
    public UserDTO findAll(Pageable pageable) {

        Page<UserEntity> page = userRepository.findAll(pageable);

        UserDTO userDTO = new UserDTO();

        userDTO.setListResult(
                page.getContent().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList())
        );

        userDTO.setCurrentPage(page.getNumber() + 1);
        userDTO.setTotalPages(page.getTotalPages());

        return userDTO;
    }

    @Override
    public Long getTotalItems() {
        return userRepository.count();
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        Assert.notNull(fieldName);

        if (!fieldName.equals("email") && !fieldName.equals("username")) {
            throw new UnsupportedOperationException("Trường này không được hỗ trợ xác thực là duy nhất");
        }

        if (value == null) {
            return false;
        }

        switch (fieldName) {
            case "email":
                return this.userRepository.existsByEmail(value.toString());
            case "username":
                return this.userRepository.existsByUsername(value.toString());
            default:
                return false;
        }
    }
}
