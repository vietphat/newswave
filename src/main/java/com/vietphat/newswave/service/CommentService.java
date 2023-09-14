package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.comment.CommentDTO;
import com.vietphat.newswave.enums.CommentStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findAll();

    CommentDTO findById(Long id);

    CommentDTO findDetailsById(Long id);

    CommentDTO searchCommentsWithPagination(Pageable pageable, String search);

    void delete(String[] ids);

    CommentDTO changeStatus(Long id, CommentStatus status);

}
