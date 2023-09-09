package com.vietphat.newswave.service;

import com.vietphat.newswave.dto.category.CategoryDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService extends UniqueFieldService {

    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO searchCategoriesWithPagination(Pageable pageable, String search);

    CategoryDTO save(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO);

    void delete(String[] ids);

}
