package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsernameAndStatus(String username, Integer status);

}
