package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordTokenEntity, Long> {

    ResetPasswordTokenEntity findByUserId(Long id);

    ResetPasswordTokenEntity findByToken(String tokenStr);

}
