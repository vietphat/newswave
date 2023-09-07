package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.ResetPasswordTokenEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordTokenEntity, Long> {

    ResetPasswordTokenEntity findByUserId(Long id);

    ResetPasswordTokenEntity findByToken(String tokenStr);

    @Query("SELECT rpt FROM ResetPasswordTokenEntity rpt " +
            "WHERE :search IS NULL OR LOWER(rpt.token) LIKE %:search% OR LOWER(rpt.user.username) LIKE %:search%")
    Page<ResetPasswordTokenEntity> searchResetPasswordTokensWithPagination(@Param("search") String search, Pageable pageable);

}
