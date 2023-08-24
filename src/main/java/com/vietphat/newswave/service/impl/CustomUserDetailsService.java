package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.MyUser;
import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.dto.UserDTO;
import com.vietphat.newswave.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDTO userDTO = userService.findByUsernameAndStatus(username, 1);

        if (userDTO == null) {
            throw new UsernameNotFoundException("Username not found");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
        }

        MyUser myUser = new MyUser(userDTO.getUsername(), userDTO.getPassword(),
                true, true, true, true, authorities);

        BeanUtils.copyProperties(userDTO, myUser);

        return myUser;
    }
}
