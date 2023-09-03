package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.username = :username and u.status = :status")
    UserEntity findUserWithRolesByUsernameAndStatus(@Param("username") String username, @Param("status") UserStatus status);

    UserEntity findByUsernameAndStatus(String username, UserStatus status);

    UserEntity findByEmailAndStatus(String email, UserStatus status);

    UserEntity findByIdAndStatus(Long id, UserStatus status);

    boolean existsByEmail(String value);

    boolean existsByUsername(String value);

    boolean existsByPhoneNumber(String value);

    @Query("SELECT u FROM UserEntity u JOIN FETCH u.roles WHERE u.id = :userId")
    UserEntity findUserWithRolesById(@Param("userId") Long id);

    // Tìm kiếm user theo tên đăng nhập hoặc tên đầy đủ và phân trang
    @Query("SELECT u FROM UserEntity u WHERE LOWER(u.username) LIKE %:search% OR LOWER(u.fullName) LIKE %:search%")
    Page<UserEntity> searchUsersWithPagination(@Param("search") String search, Pageable pageable);

}
