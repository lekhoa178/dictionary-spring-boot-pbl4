package com.pbl4.monolingo.utility.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequest {

    private String model;
    private List<Message> messages;
    private float temperature = 0.7f;

    public ChatGPTRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();

        this.messages.add(new Message("user", prompt));
    }
}
