package com.vietphat.newswave.service.impl;

import com.vietphat.newswave.dto.post.PostDTO;
import com.vietphat.newswave.entity.CategoryEntity;
import com.vietphat.newswave.entity.PostEntity;
import com.vietphat.newswave.entity.UserEntity;
import com.vietphat.newswave.repository.CategoryRepository;
import com.vietphat.newswave.repository.PostRepository;
import com.vietphat.newswave.repository.UserRepository;
import com.vietphat.newswave.service.PostService;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper modelMapper;

    private PostRepository postRepository;

    private CategoryRepository categoryRepository;

    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public PostDTO searchPostsWithPagination(Pageable pageable, String search) {

        PostDTO postDTO = new PostDTO();

        Page<PostEntity> page = postRepository.searchPostsWithPagination(pageable, search);

        if (search != null) {
            postDTO.setSearch(search);
        }

        postDTO.setCurrentPage(page.getNumber() + 1);
        postDTO.setTotalPages(page.getTotalPages());
        postDTO.setListResult(
                page.getContent().stream()
                        .map(post -> modelMapper.map(post, PostDTO.class))
                        .collect(Collectors.toList())
        );

        return postDTO;
    }

    @Override
    public PostDTO findById(Long id) {

        PostEntity post = postRepository.findById(id).orElse(null);

        return post != null ? modelMapper.map(post, PostDTO.class) : null;
    }

    @Override
    public PostDTO save(PostDTO postDTO) {

        // set thumbnail
        if (postDTO.getThumbnail() == null || postDTO.getThumbnail().isEmpty()) {
            postDTO.setThumbnail("default.jpg");
        }

        // convert category code -> category
        CategoryEntity category = categoryRepository.findByCode(postDTO.getCategoryCode());

        // convert postedUserId (Long) -> postedUser (UserEntity)
        UserEntity postedUser = userRepository.findById(postDTO.getPostedUserId()).orElse(null);

        // map DTO -> entity
        PostEntity post = modelMapper.map(postDTO, PostEntity.class);

        // set category, posted user
        post.setCategory(category);
        post.setPostedUser(postedUser);

        // save
        PostEntity createdPost = postRepository.save(post);

        if (post != null) {
            PostDTO createdPostDTO = modelMapper.map(createdPost, PostDTO.class);

            // calculate the last page (including the new one)
            int lastPage = (int) Math.ceil((double) postRepository.count() / 5);
            createdPostDTO.setCurrentPage(lastPage);

            return createdPostDTO;
        }

        return null;
    }

    @Override
    public PostDTO update(PostDTO postDTO) {

        PostEntity post = postRepository.findById(postDTO.getId()).orElse(null);

        // nếu không cập nhật ảnh bìa thì trả về ảnh cũ
        if (postDTO.getThumbnail() == null || postDTO.getThumbnail().isEmpty()) {
            postDTO.setThumbnail(post.getThumbnail());
        }

        BeanUtils.copyProperties(postDTO, post);

        // nếu có cập nhật danh mục
        if (!postDTO.getCategoryCode().equals(post.getCategory())) {
            CategoryEntity category = categoryRepository.findByCode(postDTO.getCategoryCode());

            post.setCategory(category);
        }

        // lưu thay đổi
        PostEntity updatedPost = postRepository.save(post);

        // chuyển về DTO để trả về controller
        PostDTO updatedPostDTO = modelMapper.map(updatedPost, PostDTO.class);

        return updatedPostDTO;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName, String id) throws UnsupportedOperationException {

        Assert.notNull(fieldName);

        if (!fieldName.equals("slug")) {
            throw new UnsupportedOperationException("Trường này không được hỗ trợ xác thực duy nhất");
        }

        // nếu có id -> đang sửa -> tìm trong db theo id
        PostEntity post = null;
        if (id != null && !id.isEmpty()) {
            post = postRepository.findById(Long.parseLong(id)).orElse(null);
        }

        switch (fieldName) {
            case "slug":
                // nếu giá trị cũ == giá trị đang gửi vào-> pass
                if (post != null && post.getSlug().equals(value.toString())) {
                    return false;
                }
                return postRepository.existsBySlug(value.toString());
            default:
                return false;
        }
    }
}
