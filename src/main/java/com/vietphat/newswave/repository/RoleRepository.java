package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.RoleEntity;
import com.vietphat.newswave.enums.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    List<RoleEntity> findAll();

    RoleEntity findByCode(UserRole userRole);

    @Query(value = "SELECT * FROM role r WHERE r.code = :code", nativeQuery = true)
    RoleEntity findByCodeString(@Param("code") String codeString);

    @Query("SELECT r FROM RoleEntity r WHERE LOWER(r.name) LIKE %:search% OR LOWER(r.code) LIKE %:search%")
    Page<RoleEntity> searchRolesWithPagination(@Param("search") String search, Pageable pageable);

}
