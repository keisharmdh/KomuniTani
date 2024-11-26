package com.example.model;

public class MessageDetail {
    private int sender_id;
    private int receiver_id;
    private String message;
    private String updated_at;
    private String created_at;
    private int id;


    public MessageDetail(int id, String message, int senderId, String createdAt, String updatedAt) {
        this.id = id;
        this.message = message;
        this.sender_id = senderId;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }


    public int getSender_id() {
        return sender_id;
    }

    public void setSender_id(int sender_id) {
        this.sender_id = sender_id;
    }

    public int getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(int receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
