package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.constant.SystemConstant;
import com.vietphat.newswave.dto.commentreport.CommentReportDTO;
import com.vietphat.newswave.entity.CommentEntity;
import com.vietphat.newswave.entity.CommentReportEntity;
import com.vietphat.newswave.enums.CommentStatus;
import com.vietphat.newswave.repository.CommentReportRepository;
import com.vietphat.newswave.repository.CommentRepository;
import com.vietphat.newswave.service.CommentReportService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentReportServiceImpl implements CommentReportService {

    private ModelMapper modelMapper;

    private CommentReportRepository commentReportRepository;

    private CommentRepository commentRepository;

    @Autowired
    public CommentReportServiceImpl(ModelMapper modelMapper, CommentReportRepository commentReportRepository, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentReportRepository = commentReportRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentReportDTO> findAll() {

        List<CommentReportEntity> commentReports = commentReportRepository.findAll();

        List<CommentReportDTO> commentReportDTOs = commentReports.stream()
                                                    .map(commentReport -> modelMapper.map(commentReport, CommentReportDTO.class))
                                                    .collect(Collectors.toList());

        return commentReportDTOs;
    }

    @Override
    public CommentReportDTO findById(Long id) {

        CommentReportEntity commentReport = commentReportRepository.findById(id).orElse(null);

        return commentReport == null ? null : modelMapper.map(commentReport, CommentReportDTO.class);
    }

    @Override
    public CommentReportDTO findDetailsById(Long id) {

        CommentReportEntity commentReport = commentReportRepository.findDetailsById(id);

        return commentReport == null ? null : modelMapper.map(commentReport, CommentReportDTO.class);
    }

    @Override
    public CommentReportDTO searchCommentReportsWithPagination(Pageable pageable, String search) {

        CommentReportDTO commentReportDTO = new CommentReportDTO();

        Page<CommentReportEntity> page = commentReportRepository.searchCommentReportsWithPagination(pageable, search);

        if (search != null) {
            commentReportDTO.setSearch(search);
        }

        commentReportDTO.setCurrentPage(page.getNumber() + 1);
        commentReportDTO.setTotalPages(page.getTotalPages());
        commentReportDTO.setListResult(
                page.getContent().stream()
                        .map(commentReport -> modelMapper.map(commentReport, CommentReportDTO.class))
                        .collect(Collectors.toList())
        );

        return commentReportDTO;
    }

    @Override
    @Transactional
    public void delete(String[] ids) {

        try {
            for (String id : ids) {
                CommentReportEntity commentReport = commentReportRepository.findById(Long.parseLong(id)).orElse(null);

                if (commentReport != null) {

                    commentReport.setUser(null);
                    commentReport.setComment(null);

                    commentReportRepository.delete(commentReport);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public CommentReportDTO approveReport(Long id, String action) {

        CommentReportEntity commentReport = commentReportRepository.findByIdWithsComment(id);
        CommentEntity comment = commentRepository.findById(commentReport.getComment().getId()).orElse(null);

        if (commentReport == null || comment == null) {
            return null;
        }

        if (action.equals(SystemConstant.CONFIRM_COMMENT_REPORT)) {
            comment.setStatus(CommentStatus.HIDDEN);
            commentRepository.save(comment);
        }

        commentReport.setApproved(true);
        CommentReportEntity approvedCommentReport = commentReportRepository.save(commentReport);

        return modelMapper.map(approvedCommentReport, CommentReportDTO.class);
    }
}
