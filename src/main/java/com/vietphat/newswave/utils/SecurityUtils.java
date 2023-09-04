package com.vietphat.newswave.utils;

import com.vietphat.newswave.dto.user.MyUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

public class SecurityUtils {

    public static MyUser getPrincipal() {

        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user;
    }

    public static List<String> getAuthorities() {

        List<String> authorities = new ArrayList<>();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        for (GrantedAuthority authority : grantedAuthorities) {
            authorities.add(authority.getAuthority());
        }

        return authorities;
    }
}
