package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.ResetPasswordDTO;
import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.dto.UserRegistrationDTO;
import com.vietphat.newswave.entity.RoleEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserRole;
import com.vietphat.newswave.enums.UserStatus;
import com.vietphat.newswave.repository.RoleRepository;
import com.vietphat.newswave.repository.UserRepository;
import com.vietphat.newswave.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository) {

        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDTO findUserWithRolesById(Long id) {

        UserEntity user = userRepository.findUserWithRolesById(id);

        return (user == null) ? null : modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserWithRolesByUsernameAndStatus(String username, UserStatus userStatus) {

        UserEntity user = userRepository.findUserWithRolesByUsernameAndStatus(username, userStatus);

        return (user == null) ? null : modelMapper.map(user, UserDTO.class);
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

        // thêm quyền USER
        RoleEntity role = roleRepository.findByCode(UserRole.USER);
        user.addRole(role);

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
    @Transactional
    public UserDTO save(UserDTO userDTO) {

        // set encoded password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        // set status
        userDTO.setStatus(UserStatus.ACTIVE);

        // find role entities
        List<RoleEntity> roles = userDTO.getRoleCodes()
                .stream()
                .map(roleCode -> roleRepository.findByCodeString(roleCode))
                .collect(Collectors.toList());

        // map userDTO -> entity
        UserEntity user = modelMapper.map(userDTO, UserEntity.class);

        // add role to user entity
        user.addRoles(roles);

        // save user
        UserEntity createdUser = userRepository.save(user);

        if (user != null) {
            // calculate the last page (including new user)
            UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);

            int lastPage = (int) Math.ceil((double) userRepository.count() / 5);
            createdUserDTO.setCurrentPage(lastPage);

            return createdUserDTO;
        }

        return null;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) throws UnsupportedOperationException {
        Assert.notNull(fieldName);

        if (!fieldName.equals("email") && !fieldName.equals("username") && !fieldName.equals("phoneNumber")) {
            throw new UnsupportedOperationException("Trường này không được hỗ trợ xác thực là duy nhất");
        }

        if (value == null) {
            return false;
        }

        switch (fieldName) {
            case "email":
                return userRepository.existsByEmail(value.toString());
            case "username":
                return userRepository.existsByUsername(value.toString());
            case "phoneNumber":
                return userRepository.existsByPhoneNumber(value.toString());
            default:
                return false;
        }
    }
}
