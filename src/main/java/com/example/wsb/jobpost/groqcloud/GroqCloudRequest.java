package com.example.wsb.jobpost.groqcloud;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class GroqCloudRequest {

    private String model;
    private List<Message> messages;

    public GroqCloudRequest(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user",prompt));
    }
}
