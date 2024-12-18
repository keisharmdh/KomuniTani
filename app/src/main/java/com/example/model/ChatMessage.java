package com.example.model;

public class ChatMessage {
    private int id;
    private int sender_id;
    private int receiver_id;
    private String message;
    private String created_at;
    private String updated_at;

    public ChatMessage(int id, String message, int senderId, String createdAt, String updatedAt) {
        this.id = id;
        this.message = message;
        this.sender_id = senderId;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }


    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return sender_id;
    }

    public void setSenderId(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiverId() {
        return receiver_id;
    }

    public void setReceiverId(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
        this.updated_at = updated_at;
    }
}
