package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.tag.TagDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService extends UniqueFieldService {

    List<TagDTO> findAll();

    TagDTO findById(Long id);

    TagDTO searchTagsWithPagination(Pageable pageable, String search);

    TagDTO save(TagDTO tagDTO);

    TagDTO update(TagDTO tagDTO);

    void delete(String[] ids);

}
