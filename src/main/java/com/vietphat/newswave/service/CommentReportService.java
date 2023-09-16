package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.commentreport.CommentReportDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentReportService {

    List<CommentReportDTO> findAll();

    CommentReportDTO findById(Long id);

    CommentReportDTO findDetailsById(Long id);

    CommentReportDTO searchCommentReportsWithPagination(Pageable pageable, String search);

    void delete(String[] ids);

    CommentReportDTO approveReport(Long id, String action);

}
