package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.post.PostDTO;
import org.springframework.data.domain.Pageable;

public interface PostService extends UniqueFieldService {

    PostDTO searchPostsWithPagination(Pageable pageable, String search);

    PostDTO findById(Long id);

    PostDTO findDetailsById(Long id);

    PostDTO save(PostDTO postDTO);

    PostDTO update(PostDTO postDTO);

    void delete(String[] ids);

}
