package com.example.model;


public class Post {
    private String userName;
    private String userLocation;
    private String content;
    private int likeCount;
    private int commentCount;
    private int shareCount;
    private int imageResourceId; // Menggunakan ID sumber gambar jika menggunakan drawable

    // Constructor
    public Post(String userName, String userLocation, String content, int likeCount, int commentCount, int shareCount, int imageResourceId) {
        this.userName = userName;
        this.userLocation = userLocation;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.imageResourceId = imageResourceId;
    }

    // Getter methods
    public String getUserName() {
        return userName;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public String getContent() {
        return content;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
