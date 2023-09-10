package com.vietphat.newswave.dto.post;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.dto.tag.TagDTO;
import com.vietphat.newswave.entity.CategoryEntity;
import com.vietphat.newswave.enums.PostStatus;
import com.vietphat.newswave.service.PostService;
import com.vietphat.newswave.validation.uniquefield.UniqueField;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDTO extends BaseDTO<PostDTO> {

    @NotNull(message = "Tiêu đề là bắt buộc")
    private String title;

    @NotNull(message = "Slug là bắt buộc")
    @UniqueField(fieldName = "slug", service = PostService.class, message = "Slug đã tồn tại")
    private String slug;

    private String thumbnail;

    private String thumbnailPath;

    @NotNull(message = "Mô tả ngắn là bắt buộc")
    private String shortDescription;

    @NotNull(message = "Nội dung là bắt buộc")
    private String content;

    private boolean alreadyPublished;

    @NotNull(message = "Ngày đăng là bắt buộc")
    private PostStatus published;

    @NotNull(message = "Ngày đăng là bắt buộc")
    private Date publishedDate;

    private Integer views;

    @NotNull(message = "Danh mục là bắt buộc")
    private String categoryCode;

    private CategoryEntity category;

    @NotNull(message = "Người đăng là bắt buộc")
    public Long postedUserId;

    private PostDTO postedUser;

    private List<String> tagCodes = new ArrayList<>();

    private List<TagDTO> tags = new ArrayList<>();

//    private List<CommentDTO> comments = new ArrayList<>();

//    private List<SavedPostDTO> savingUsers = new ArrayList<>();

    public PostDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAlreadyPublished() {
        return alreadyPublished;
    }

    public void setAlreadyPublished(boolean alreadyPublished) {
        this.alreadyPublished = alreadyPublished;
    }

    public PostStatus getPublished() {
        return published;
    }

    public void setPublished(PostStatus published) {
        this.published = published;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public PostDTO getPostedUser() {
        return postedUser;
    }

    public void setPostedUser(PostDTO postedUser) {
        this.postedUser = postedUser;
    }

    public List<TagDTO> getTags() {
        return tags;
    }

    public void setTags(List<TagDTO> tags) {
        this.tags = tags;
    }

    public Long getPostedUserId() {
        return postedUserId;
    }

    public void setPostedUserId(Long postedUserId) {
        this.postedUserId = postedUserId;
    }

    public List<String> getTagCodes() {
        return tagCodes;
    }

    public void setTagCodes(List<String> tagCodes) {
        this.tagCodes = tagCodes;
    }

    public String getThumbnailPath() {

        if (id == null || thumbnail == null) {
            return null;
        }

        return "/images/post-thumbnails/" + id + "/" + thumbnail;
    }

    public boolean containsTag(String tagCode) {

        if (tagCode == null || tagCode.isEmpty()) {
            return false;
        }

        for (TagDTO tag : tags) {
            if (tag.getCode().equals(tagCode)) {
                return true;
            }
        }

        return false;
    }
}
