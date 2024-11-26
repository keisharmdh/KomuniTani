package com.example.model;

public class ChatUser {
    private int id;
    private String name;
    private String email;
    private String bio;
    private String profile_picture;
    private String lastMessage;
    private String lastMessageCreatedAt;

    // Getters and Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getProfilePicture() {
        return profile_picture;
    }
    public void setProfilePicture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
    public String getLastMessage() {
        return lastMessage;
    }
    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
    public String getLastMessageCreatedAt() {
        return lastMessageCreatedAt;
    }
    public void setLastMessageCreatedAt(String lastMessageCreatedAt) {
        this.lastMessageCreatedAt = lastMessageCreatedAt;
    }
}
