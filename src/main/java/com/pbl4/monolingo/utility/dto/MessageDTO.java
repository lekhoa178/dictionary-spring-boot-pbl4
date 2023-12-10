package com.pbl4.monolingo.utility.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String content;
    private Integer senderUserId;
    private Integer receiverUserId;
    private LocalDateTime timestamp;

    public MessageDTO() {
    }

    public MessageDTO(String content, Integer senderUserId, Integer receiverUserId, LocalDateTime timestamp) {
        this.content = content;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.timestamp = timestamp;
    }

    public MessageDTO(String content, Integer senderUserId, Integer receiverUserId) {
        this.content = content;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
