package com.example.model;


public class ProfileUpdateResponse {
    private String message;
    private User user;

    // Getter untuk message
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // Getter untuk user
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
