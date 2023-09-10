package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<TagEntity, Long> {

    TagEntity findByCode(String code);

    boolean existsByCode(String code);

    @Query("SELECT t FROM TagEntity t" +
            " WHERE :search IS NULL OR LOWER(t.name) LIKE %:search% OR LOWER(t.code) LIKE %:search%")
    Page<TagEntity> searchTagsWithPagination(Pageable pageable, @Param("search") String search);

}
