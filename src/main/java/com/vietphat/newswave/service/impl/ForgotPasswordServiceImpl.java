package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.repository.ResetPasswordTokenRepository;
import com.vietphat.newswave.repository.UserRepository;
import com.vietphat.newswave.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private UserRepository userRepository;

    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Autowired
    public ForgotPasswordServiceImpl(UserRepository userRepository, ResetPasswordTokenRepository resetPasswordTokenRepository) {
        this.userRepository = userRepository;
        this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    }

    @Override
    public ResetPasswordTokenEntity createToken(UserEntity user) {

        ResetPasswordTokenEntity token = new ResetPasswordTokenEntity();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR, 1);
        token.setExpiryDate(cal.getTime());

        return token;
    }

    @Override
    @Transactional
    public ResetPasswordTokenEntity saveToken(ResetPasswordTokenEntity token) {
        return resetPasswordTokenRepository.save(token);
    }

    @Override
    public ResetPasswordTokenEntity findResetPasswordTokenByUserId(Long userId) {
        return resetPasswordTokenRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void deleteResetPasswordToken(ResetPasswordTokenEntity resetPasswordToken) {
        resetPasswordToken.setUser(null);
        resetPasswordTokenRepository.delete(resetPasswordToken);
    }

    @Override
    public ResetPasswordTokenEntity findResetPasswordTokenByToken(String tokenStr) {

        return resetPasswordTokenRepository.findByToken(tokenStr);
    }

    @Override
    public boolean validateToken(ResetPasswordTokenEntity token) {

        // 1. kiểm tra token có tồn tại không
        if (token == null) {
            return false;
        }

        // 2. kiểm tra còn hạn sd
        if (token.getExpiryDate().before(new Date())) {
            return false;
        }

        return true;
    }
}
