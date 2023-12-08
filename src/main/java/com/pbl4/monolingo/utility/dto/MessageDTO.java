package com.pbl4.monolingo.utility.dto;

public class MessageDTO {
    private String receiver;
    private String content;

    public MessageDTO(String receiver, String content) {
        this.receiver = receiver;
        this.content = content;
    }

    public MessageDTO() {
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
