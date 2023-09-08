package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.user.ResetPasswordDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.dto.user.UserRegistrationDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

        if (user == null) {
            return null;
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // map roles -> roleCodes
        userDTO.mapRolesToRoleCodes();

        return userDTO;
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
    public void delete(String[] userIds) {

        try {
            for (String userId : userIds) {
                UserEntity user = userRepository.findById(Long.parseLong(userId)).orElse(null);

                if (user != null) {

                    // delete all the comments
                    user.setComments(null);

                    // TODO: delete all the posts

                    // delete all the user_role contains this user
                    user.setRoles(null);

                    // delete all the saved_post
                    user.setSavedPosts(null);

                    // finally, delete this user
                    userRepository.delete(user);

                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    @Transactional
    public UserDTO resetPassword(ResetPasswordDTO resetPasswordDTO) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        UserEntity user = userRepository.findById(resetPasswordDTO.getId()).orElse(null);

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.mapRolesToRoleCodes();

        return userDTO;
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
    public UserDTO findAll(Pageable pageable, String search, String roleCode) {

        UserDTO userDTO = new UserDTO();

        Page<UserEntity> page = userRepository.searchUsersWithPagination(pageable, search, roleCode);

        if (search != null) {
            userDTO.setSearch(search);
        }

        if (roleCode != null) {
            userDTO.setRoleCode(roleCode);
        }

        userDTO.setCurrentPage(page.getNumber() + 1);
        userDTO.setTotalPages(page.getTotalPages());
        userDTO.setListResult(
                page.getContent().stream()
                        .map(user -> modelMapper.map(user, UserDTO.class))
                        .collect(Collectors.toList())
        );

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
            UserDTO createdUserDTO = modelMapper.map(createdUser, UserDTO.class);

            // calculate the last page (including new user)
            int lastPage = (int) Math.ceil((double) userRepository.count() / 5);
            createdUserDTO.setCurrentPage(lastPage);

            return createdUserDTO;
        }

        return null;
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {

        UserEntity user = userRepository.findById(userDTO.getId()).orElse(null);

        // phục hồi trường password
        userDTO.setPassword(user.getPassword());

        // copy các thuộc tính đã thay đổi qua user
        BeanUtils.copyProperties(userDTO, user);

        // chuyển roleCodes thành role và copy qua user entity
        List<RoleEntity> roles = userDTO.getRoleCodes().stream()
                .map(roleCode -> roleRepository.findByCodeString(roleCode))
                .collect(Collectors.toList());

        user.setRoles(roles);

        // lưu thay đổi
        UserEntity updatedUser = userRepository.save(user);

        // trả về dto
        UserDTO updatedUserDTO = modelMapper.map(updatedUser, UserDTO.class);

        // map roles -> roleCodes
        updatedUserDTO.mapRolesToRoleCodes();

        return updatedUserDTO;
    }

    @Override
    public void saveLastLogin(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        user.setLastLogin(new Date());

        userRepository.save(user);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName, String id) throws UnsupportedOperationException {

        Assert.notNull(fieldName);

        if (!fieldName.equals("email") && !fieldName.equals("username") && !fieldName.equals("phoneNumber")) {
            throw new UnsupportedOperationException("Trường này không được hỗ trợ xác thực duy nhất");
        }

        UserEntity user = null;
        if (id != null && !id.isEmpty()) {
            user = userRepository.findById(Long.parseLong(id)).orElse(null);
        }

        switch (fieldName) {
            case "email":
                if (user != null && user.getEmail().equals(value.toString())) {
                    return true;
                }
                return userRepository.existsByEmail(value.toString());
            case "username":
                if (user != null && user.getUsername().equals(value.toString())) {
                    return true;
                }
                return userRepository.existsByUsername(value.toString());
            case "phoneNumber":
                if (user != null && user.getPhoneNumber().equals(value.toString())) {
                    return true;
                }
                return userRepository.existsByPhoneNumber(value.toString());
            default:
                return false;
        }
    }
}
