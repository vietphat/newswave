package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    boolean existsBySlug(String slug);

    @Query("SELECT p FROM PostEntity p " +
            "JOIN FETCH p.postedUser " +
            "WHERE p.id = :id")
    PostEntity findDetailsById(@Param("id") Long id);

    @Query("SELECT p FROM PostEntity p " +
            "WHERE :search IS NULL OR LOWER(p.title) LIKE %:search% " +
            "OR LOWER(p.shortDescription) LIKE %:search% OR LOWER(p.content) LIKE %:search% ")
    Page<PostEntity> searchPostsWithPagination(Pageable pageable, @Param("search") String search);

}
