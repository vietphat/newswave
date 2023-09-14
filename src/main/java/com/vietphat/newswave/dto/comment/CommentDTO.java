package com.vietphat.newswave.dto.comment;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.dto.post.PostDTO;
import com.vietphat.newswave.dto.user.UserDTO;
import com.vietphat.newswave.enums.CommentStatus;

import java.util.ArrayList;
import java.util.List;

public class CommentDTO extends BaseDTO<CommentDTO> {

    private String content;

    private CommentDTO parent;

    private List<CommentDTO> replies = new ArrayList<>();;

    private UserDTO user;

    private PostDTO post;

    private CommentStatus status;

    public CommentDTO() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommentDTO getParent() {
        return parent;
    }

    public void setParent(CommentDTO parent) {
        this.parent = parent;
    }

    public List<CommentDTO> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentDTO> replies) {
        this.replies = replies;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    public CommentStatus getStatus() {
        return status;
    }

    public void setStatus(CommentStatus status) {
        this.status = status;
    }
}
