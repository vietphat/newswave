package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAll();

}
