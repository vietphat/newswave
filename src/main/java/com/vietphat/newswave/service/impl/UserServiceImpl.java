package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.repository.UserRepository;
import com.vietphat.newswave.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserEntity register(UserRegistrationDTO userRegistrationDTO) {

        // mã hóa mật khẩu khi đăng ký tài khoản
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRegistrationDTO.setPassword(passwordEncoder.encode(userRegistrationDTO.getPassword()));

        UserEntity user = modelMapper.map(userRegistrationDTO, UserEntity.class);

        return userRepository.save(user);
    }
}
