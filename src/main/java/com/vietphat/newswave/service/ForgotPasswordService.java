package com.vietphat.newswave.service;

import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;

public interface ForgotPasswordService {

    ResetPasswordTokenEntity createToken(UserEntity user);

    ResetPasswordTokenEntity saveToken(ResetPasswordTokenEntity token);

    ResetPasswordTokenEntity findResetPasswordTokenByUserId(Long userId);

    void deleteResetPasswordToken(ResetPasswordTokenEntity token);

    ResetPasswordTokenEntity findResetPasswordTokenByToken(String tokenStr);

    boolean validateToken(ResetPasswordTokenEntity token);

}
