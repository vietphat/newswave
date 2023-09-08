package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.tag.TagDTO;
import com.vietphat.newswave.entity.TagEntity;
import com.vietphat.newswave.repository.TagRepository;
import com.vietphat.newswave.service.TagService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private ModelMapper modelMapper;

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagDTO findById(Long id) {

        TagEntity tag = tagRepository.findById(id).orElse(null);

        return tag == null ? null : modelMapper.map(tag, TagDTO.class);
    }

    @Override
    public TagDTO searchTagsWithPagination(Pageable pageable, String search) {

        TagDTO tagDTO = new TagDTO();

        Page<TagEntity> page = tagRepository.searchTagsWithPagination(pageable, search);

        if (search != null) {
            tagDTO.setSearch(search);
        }

        tagDTO.setCurrentPage(page.getNumber() + 1);
        tagDTO.setTotalPages(page.getTotalPages());
        tagDTO.setListResult(
                page.getContent().stream()
                        .map(tag -> modelMapper.map(tag, TagDTO.class))
                        .collect(Collectors.toList())
        );

        return tagDTO;
    }

    @Override
    @Transactional
    public TagDTO save(TagDTO tagDTO) {

        TagEntity tag = modelMapper.map(tagDTO, TagEntity.class);

        TagEntity createdTag = tagRepository.save(tag);

        if (createdTag != null) {
            TagDTO createdTagDTO = modelMapper.map(createdTag, TagDTO.class);

            // calculate the last page
            int lastPage = (int) Math.ceil((double) tagRepository.count() / 5);
            createdTagDTO.setCurrentPage(lastPage);

            return createdTagDTO;
        }

        return null;
    }

    @Override
    @Transactional
    public TagDTO update(TagDTO tagDTO) {

        TagEntity tag = tagRepository.findById(tagDTO.getId()).orElse(null);

        // copy các thuộc tính được thay đổi
        BeanUtils.copyProperties(tagDTO, tag);

        // lưu thay đổi
        TagEntity updatedTag = tagRepository.save(tag);

        // chuyển về DTO để trả về controller
        TagDTO updatedTagDTO = modelMapper.map(updatedTag, TagDTO.class);

        return updatedTagDTO;
    }

    @Override
    public void delete(String[] ids) {

        try {
            for (String id : ids) {
                TagEntity tag = tagRepository.findById(Long.parseLong(id)).orElse(null);

                if (tag != null) {
                    // delete all the post_tag contains this tag
                    tag.setPosts(null);

                    // finally, delete this one
                    tagRepository.delete(tag);
                }
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

        // nếu có id -> đang sửa -> tìm trong db theo id
        TagEntity tag = null;
        if (id != null && !id.isEmpty()) {
            tag = tagRepository.findById(Long.parseLong(id)).orElse(null);
        }

        switch (fieldName) {
            case "code":
                // nếu giá trị cũ == giá trị đang gửi vào == nhau -> pass
                if (tag != null && tag.getCode().equals(value.toString())) {
                    return false;
                }
                return tagRepository.existsByCode(value.toString());
            default:
                return false;
        }
    }
}
