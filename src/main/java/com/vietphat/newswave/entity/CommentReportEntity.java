package com.vietphat.newswave.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comment_report")
public class CommentReportEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private CommentEntity comment;

    @Column(name = "reason")
    private String reason;

    public CommentReportEntity() {
    }

    public CommentReportEntity(String reason) {
        this.reason = reason;
    }

    public CommentEntity getComment() {
        return comment;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
