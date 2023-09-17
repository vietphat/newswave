package com.vietphat.newswave.entity;

import com.vietphat.newswave.enums.PostStatus;
import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "short_description", columnDefinition = "TEXT", nullable = false)
    private String shortDescription;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "published")
    private PostStatus published;

    @Column(name = "published_date")
    private Date publishedDate;

    @Column(name = "views")
    private Integer views;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity postedUser;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<SavedPostEntity> savedPosts = new ArrayList<>();

    public PostEntity() {
    }

    public PostEntity(String title, String slug, String thumbnail, String shortDescription, String content, PostStatus published, Date publishedDate) {
        this.title = title;
        this.slug = slug;
        this.thumbnail = thumbnail;
        this.shortDescription = shortDescription;
        this.content = content;
        this.published = published;
        this.publishedDate = publishedDate;
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

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<TagEntity> getTags() {
        return tags;
    }

    public void setTags(List<TagEntity> tags) {
        this.tags = tags;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<CommentEntity> getComments() {
        return comments;
    }

    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }

    public List<SavedPostEntity> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(List<SavedPostEntity> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public UserEntity getPostedUser() {
        return postedUser;
    }

    public void setPostedUser(UserEntity postedUser) {
        this.postedUser = postedUser;
    }
}
