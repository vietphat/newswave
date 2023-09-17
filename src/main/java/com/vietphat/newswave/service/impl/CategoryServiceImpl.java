package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.category.CategoryDTO;
import com.vietphat.newswave.entity.CategoryEntity;
import com.vietphat.newswave.entity.PostEntity;
import com.vietphat.newswave.repository.CategoryRepository;
import com.vietphat.newswave.repository.PostRepository;
import com.vietphat.newswave.repository.SavedPostRepository;
import com.vietphat.newswave.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private ModelMapper modelMapper;

    private CategoryRepository categoryRepository;

    private PostRepository postRepository;

    private SavedPostRepository savedPostRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository, PostRepository postRepository, SavedPostRepository savedPostRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.savedPostRepository = savedPostRepository;
    }

    @Override
    public List<CategoryDTO> findAll() {

        List<CategoryEntity> categories = categoryRepository.findAll();

        List<CategoryDTO> categoryDTOs = categories.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());

        return categoryDTOs;
    }

    @Override
    public CategoryDTO findById(Long id) {

        CategoryEntity category = categoryRepository.findById(id).orElse(null);

        return category == null ? null : modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO searchCategoriesWithPagination(Pageable pageable, String search) {

        CategoryDTO categoryDTO = new CategoryDTO();

        Page<CategoryEntity> page = categoryRepository.searchCategoriesWithPagination(pageable, search);

        if (search != null) {
            categoryDTO.setSearch(search);
        }

        categoryDTO.setCurrentPage(page.getNumber() + 1);
        categoryDTO.setTotalPages(page.getTotalPages());
        categoryDTO.setListResult(
                page.getContent().stream()
                        .map(category -> modelMapper.map(category, CategoryDTO.class))
                        .collect(Collectors.toList())
        );

        return categoryDTO;
    }

    @Override
    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO) {

        CategoryEntity category = modelMapper.map(categoryDTO, CategoryEntity.class);

        CategoryEntity createdCategory = categoryRepository.save(category);

        if (createdCategory != null) {
            CategoryDTO createdCategoryDTO = modelMapper.map(createdCategory, CategoryDTO.class);

            // calculate the last page
            int lastPage = (int) Math.ceil((double) categoryRepository.count() / 5);
            createdCategoryDTO.setCurrentPage(lastPage);

            return createdCategoryDTO;
        }

        return null;
    }

    @Override
    @Transactional
    public CategoryDTO update(CategoryDTO categoryDTO) {

        // tìm category đang được cập nhật
        CategoryEntity category = categoryRepository.findById(categoryDTO.getId()).orElse(null);

        // copy các thuộc tính được thay đổi từ categoryDTO
        BeanUtils.copyProperties(categoryDTO, category);

        // lưu thay đổi
        CategoryEntity updatedCategory = categoryRepository.save(category);

        // chuyển về DTO để trả về controller
        CategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);

        return updatedCategoryDTO;
    }

    @Override
    @Transactional
    public void delete(String[] ids) {

        try {
            for (String id : ids) {
                CategoryEntity category = categoryRepository.findById(Long.parseLong(id)).orElse(null);

                categoryRepository.delete(category);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName, String id) throws UnsupportedOperationException {

        Assert.notNull(fieldName);

        if (!fieldName.equals("code")) {
            throw new UnsupportedOperationException("Trường này không được hỗ trợ xác thực duy nhất");
        }

        // nếu có id -> đang sửa -> tìm category trong db theo id
        CategoryEntity category = null;
        if (id != null && !id.isEmpty()) {
            category = categoryRepository.findById(Long.parseLong(id)).orElse(null);
        }

        switch (fieldName) {
            case "code":
                // nếu giá trị cũ == giá trị đang gửi vào == nhau -> pass
                if (category != null && category.getCode().equals(value.toString())) {
                    return false;
                }
                return categoryRepository.existsByCode(value.toString());
            default:
                return false;
        }
    }
}
