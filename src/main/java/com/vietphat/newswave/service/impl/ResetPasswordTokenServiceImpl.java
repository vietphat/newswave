package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.RoleDTO;
import com.vietphat.newswave.dto.resetpasswordtoken.ResetPasswordTokenDTO;
import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.RoleEntity;
import com.vietphat.newswave.repository.ResetPasswordTokenRepository;
import com.vietphat.newswave.service.ResetPasswordTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private ModelMapper modelMapper;

    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    public ResetPasswordTokenServiceImpl(ModelMapper modelMapper, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.modelMapper = modelMapper;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Override
    public ResetPasswordTokenDTO findById(Long id) {

        ResetPasswordTokenEntity resetPasswordToken = resetPasswordTokenRepository.findById(id).orElse(null);

        return modelMapper.map(resetPasswordToken, ResetPasswordTokenDTO.class);
    }

    @Override
    public ResetPasswordTokenDTO findAll(Pageable pageable, String search) {

        ResetPasswordTokenDTO resetPasswordTokenDTO = new ResetPasswordTokenDTO();

        Page<ResetPasswordTokenEntity> page = resetPasswordTokenRepository.searchResetPasswordTokensWithPagination(search, pageable);

        if (search != null) {
            resetPasswordTokenDTO.setSearch(search);
        }

        resetPasswordTokenDTO.setCurrentPage(page.getNumber() + 1);
        resetPasswordTokenDTO.setTotalPages(page.getTotalPages());
        resetPasswordTokenDTO.setListResult(
                page.getContent().stream()
                        .map(resetPasswordToken -> modelMapper.map(resetPasswordToken, ResetPasswordTokenDTO.class))
                        .collect(Collectors.toList())
        );

        return resetPasswordTokenDTO;
    }
}
