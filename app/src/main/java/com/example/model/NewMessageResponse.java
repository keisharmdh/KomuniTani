package com.example.model;

public class NewMessageResponse {
    private MessageDetail message;
    private String status;

    public MessageDetail getMessage() {
        return message;
    }

    public void setMessage(MessageDetail message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
