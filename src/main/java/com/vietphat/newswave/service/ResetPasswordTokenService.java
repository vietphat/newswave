package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.resetpasswordtoken.ResetPasswordTokenDTO;
import org.springframework.data.domain.Pageable;

public interface ResetPasswordTokenService {

    ResetPasswordTokenDTO findById(Long id);

    ResetPasswordTokenDTO findAll(Pageable pageable, String search);

}
