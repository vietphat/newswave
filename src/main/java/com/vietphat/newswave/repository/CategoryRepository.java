package com.vietphat.newswave.repository;

import com.vietphat.newswave.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    boolean existsByCode(String code);

    @Query("SELECT c FROM CategoryEntity c " +
            "WHERE :search IS NULL OR LOWER(c.name) LIKE %:search% OR LOWER(c.code) LIKE %:search%")
    Page<CategoryEntity> searchCategoriesWithPagination(Pageable pageable, @Param("search") String search);

}
