package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.entity.RoleEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.repository.RoleRepository;
import com.vietphat.newswave.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private ModelMapper modelMapper;

    private RoleRepository roleRepository;

    public RoleServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTO> findAll() {

        List<RoleEntity> roles = roleRepository.findAll();

        List<RoleDTO> roleDTOS = roles.stream()
                                .map(roleEntity -> modelMapper.map(roleEntity, RoleDTO.class))
                                .collect(Collectors.toList());

        return roleDTOS;
    }

    @Override
    public RoleDTO findAll(Pageable pageable, String search) {

        RoleDTO roleDTO = new RoleDTO();

        Page<RoleEntity> page;
        if (search != null && !search.isEmpty()) {
            page = roleRepository.searchRolesWithPagination(search, pageable);
            roleDTO.setSearch(search);
        } else {
            page = roleRepository.findAll(pageable);
        }

        roleDTO.setCurrentPage(page.getNumber() + 1);
        roleDTO.setTotalPages(page.getTotalPages());
        roleDTO.setListResult(
                page.getContent().stream()
                        .map(role -> modelMapper.map(role, RoleDTO.class))
                        .collect(Collectors.toList())
        );

        return roleDTO;
    }

    @Override
    public RoleDTO findById(Long id) {

        RoleEntity role = roleRepository.findById(id).orElse(null);

        return modelMapper.map(role, RoleDTO.class);
    }
}
