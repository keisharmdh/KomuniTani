package com.example.model;

import java.util.List;

public class ChatResponse {
    private User receiverUser;
    private List<ChatMessage> messages;

    // Getter dan Setter

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }

    public User getReceiverUser() {
        return receiverUser;
    }

}