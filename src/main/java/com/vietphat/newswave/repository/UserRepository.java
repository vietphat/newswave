package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndStatus(String username, UserStatus status);

    UserEntity findByEmailAndStatus(String email, UserStatus status);

    UserEntity findByIdAndStatus(Long id, UserStatus status);

    boolean existsByEmail(String value);

    boolean existsByUsername(String value);

}
