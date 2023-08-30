package com.vietphat.newswave.entity;

import com.vietphat.newswave.entity.key.SavedPostId;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
    name = "saved_post",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"})
)
public class SavedPostEntity {

    @EmbeddedId
    private SavedPostId savedPostId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @Column(name = "saved_date")
    private Date savedDate;

    public SavedPostId getSavedPostId() {
        return savedPostId;
    }

    public void setSavedPostId(SavedPostId savedPostId) {
        this.savedPostId = savedPostId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public Date getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(Date savedDate) {
        this.savedDate = savedDate;
    }
}
