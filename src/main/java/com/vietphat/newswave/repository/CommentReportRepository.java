package com.vietphat.newswave.repository;

import com.vietphat.newswave.dto.commentreport.CommentReportDTO;
import com.vietphat.newswave.entity.CommentReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentReportRepository extends JpaRepository<CommentReportEntity, Long> {

    @Query("SELECT cr FROM CommentReportEntity cr " +
            "JOIN FETCH cr.user " +
            "JOIN FETCH cr.comment " +
            "WHERE cr.id = :id")
    CommentReportEntity findDetailsById(@Param("id") Long id);

    @Query("SELECT cr FROM CommentReportEntity cr JOIN FETCH cr.comment WHERE cr.id = :id")
    CommentReportEntity findByIdWithsComment(@Param("id") Long id);

    @Query("SELECT cr FROM CommentReportEntity cr " +
            "WHERE :search IS NULL OR LOWER(cr.reason) LIKE %:search%")
    Page<CommentReportEntity> searchCommentReportsWithPagination(Pageable pageable, @Param("search") String search);

}
