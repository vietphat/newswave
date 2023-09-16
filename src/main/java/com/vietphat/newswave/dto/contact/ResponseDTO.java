package com.vietphat.newswave.dto.contact;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ResponseDTO {

    @NotNull(message = "Mã liên hệ không được để trống")
    private Long contactId;

    @NotNull(message = "Tiêu đề không được để trống")
    private String title;

    @NotNull(message = "Nội dung không được để trống")
    private String content;

    public ResponseDTO() {
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
