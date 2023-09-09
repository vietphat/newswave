package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Query(
            value = "SELECT u.* FROM user u " +
                    "JOIN user_role ur ON u.id = ur.user_id " +
                    "JOIN role r ON r.id = ur.role_id " +
                    "WHERE :roleCode IS NULL OR r.code = :roleCode " +
                    "AND (:search IS NULL OR LOWER(u.username) LIKE %:search% OR LOWER(u.full_name) LIKE %:search%)"
            , nativeQuery = true,
            countQuery = "SELECT count(*) FROM user u " +
                    "JOIN user_role ur ON u.id = ur.user_id " +
                    "JOIN role r ON r.id = ur.role_id " +
                    "WHERE :roleCode IS NULL OR r.code = :roleCode " +
                    "AND (:search IS NULL OR LOWER(u.username) LIKE %:search% OR LOWER(u.full_name) LIKE %:search%)"
    )
    Page<UserEntity> searchUsersWithPagination(Pageable pageable, @Param("search") String search, @Param("roleCode") String roleCode);

}
