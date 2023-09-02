package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.entity.RoleEntity;
import com.vietphat.newswave.repository.RoleRepository;
import com.vietphat.newswave.service.RoleService;
import org.modelmapper.ModelMapper;
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
}
