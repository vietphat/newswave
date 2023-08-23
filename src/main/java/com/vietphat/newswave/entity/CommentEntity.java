package com.vietphat.newswave.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "comment")
public class CommentEntity extends BaseEntity {

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentEntity> replies;

    @ManyToMany(mappedBy = "comments")
    private List<UserEntity> users;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public CommentEntity() {
    }

    public CommentEntity(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentEntity getParent() {
        return parent;
    }

    public void setParent(CommentEntity parent) {
        this.parent = parent;
    }

    public List<CommentEntity> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentEntity> replies) {
        this.replies = replies;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
