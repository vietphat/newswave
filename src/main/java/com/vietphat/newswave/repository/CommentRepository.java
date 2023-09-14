package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Query("SELECT c FROM CommentEntity c " +
            "WHERE :search IS NULL OR LOWER(c.content) LIKE %:search% " +
            "OR LOWER(c.post.title) LIKE %:search% OR LOWER(c.user.fullName) LIKE %:search%")
    Page<CommentEntity> searchCommentsWithPagination(Pageable pageable, @Param("search") String search);

    @Query("SELECT c FROM CommentEntity c " +
            "JOIN FETCH c.user " +
            "JOIN FETCH c.post " +
            "WHERE c.id = :id")
    CommentEntity findDetailsById(@Param("id") Long id);

}
