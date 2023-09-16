package com.vietphat.newswave.dto.commentreport;

import com.vietphat.newswave.dto.BaseDTO;
import com.vietphat.newswave.dto.comment.CommentDTO;
import com.vietphat.newswave.dto.user.UserDTO;

public class CommentReportDTO extends BaseDTO<CommentReportDTO> {

    private CommentDTO comment;

    private String reason;

    private UserDTO user;

    private boolean approved;

    public CommentReportDTO() {
    }

    public CommentDTO getComment() {
        return comment;
    }

    public void setComment(CommentDTO comment) {
        this.comment = comment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
