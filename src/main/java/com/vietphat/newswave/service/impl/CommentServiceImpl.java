package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.comment.CommentDTO;
import com.vietphat.newswave.entity.CommentEntity;
import com.vietphat.newswave.enums.CommentStatus;
import com.vietphat.newswave.repository.CommentRepository;
import com.vietphat.newswave.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private ModelMapper modelMapper;

    private CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<CommentDTO> findAll() {

        List<CommentEntity> comments = commentRepository.findAll();

        List<CommentDTO> commentDTOs = comments.stream().map(comment -> modelMapper.map(comment, CommentDTO.class)).collect(Collectors.toList());

        return commentDTOs;
    }

    @Override
    public CommentDTO findById(Long id) {

        CommentEntity comment = commentRepository.findById(id).orElse(null);

        return comment == null ? null : modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO findDetailsById(Long id) {

        CommentEntity comment = commentRepository.findDetailsById(id);

        return comment == null ? null : modelMapper.map(comment, CommentDTO.class);
    }

    @Override
    public CommentDTO searchCommentsWithPagination(Pageable pageable, String search) {

        CommentDTO commentDTO = new CommentDTO();

        Page<CommentEntity> page = commentRepository.searchCommentsWithPagination(pageable, search);

        if (search != null) {
            commentDTO.setSearch(search);
        }

        commentDTO.setCurrentPage(page.getNumber() + 1);
        commentDTO.setTotalPages(page.getTotalPages());
        commentDTO.setListResult(
                page.getContent().stream()
                        .map(comment -> modelMapper.map(comment, CommentDTO.class))
                        .collect(Collectors.toList())
        );

        return commentDTO;
    }

    @Override
    @Transactional
    public void delete(String[] ids) {

        try {
            for (String id : ids) {
                CommentEntity comment = commentRepository.findById(Long.parseLong(id)).orElse(null);

                if (comment != null) {

                    comment.setParent(null);
                    comment.setUser(null);
                    comment.setPost(null);

                    commentRepository.delete(comment);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    @Transactional
    public CommentDTO changeStatus(Long id, CommentStatus status) {

        CommentEntity comment = commentRepository.findById(id).orElse(null);

        if (comment != null) {
            comment.setStatus(status);
            CommentEntity updatedComment = commentRepository.save(comment);

            return modelMapper.map(commentRepository.findDetailsById(updatedComment.getId()), CommentDTO.class);
        }

        return null;
    }
}
