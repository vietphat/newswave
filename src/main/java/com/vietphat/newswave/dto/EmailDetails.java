package com.vietphat.newswave.dto;

public class EmailDetails {

    private String recipient;
    private String messageBody;
    private String subject;
    private String attachment;

    public EmailDetails() {
    }

    public EmailDetails(String recipient, String messageBody, String subject) {
        this.recipient = recipient;
        this.messageBody = messageBody;
        this.subject = subject;
    }

    public EmailDetails(String recipient, String messageBody, String subject, String attachment) {
        this.recipient = recipient;
        this.messageBody = messageBody;
        this.subject = subject;
        this.attachment = attachment;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
}
