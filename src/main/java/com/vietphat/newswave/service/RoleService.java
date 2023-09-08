package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.role.RoleDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {

    List<RoleDTO> findAll();

    RoleDTO findAll(Pageable pageable, String search);

    RoleDTO findById(Long id);

}
