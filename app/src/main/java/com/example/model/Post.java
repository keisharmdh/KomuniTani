package com.example.model;

import com.google.gson.annotations.SerializedName;

public class Post {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id") // user_id yang akan digunakan untuk mengambil username
    private int userId;

    @SerializedName("user_location")
    private String userLocation;

    @SerializedName("content")
    private String content;

    @SerializedName("like_count")
    private int likeCount;

    @SerializedName("comment_count")
    private int commentCount;

    @SerializedName("share_count")
    private int shareCount;

    @SerializedName("image_url") // Gambar postingan
    private String imageUrl;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("timestamp") // Tanggal pembuatan postingan
    private String timestamp;

    @SerializedName("avatar_url") // URL avatar pengguna
    private String avatarUrl;

    // Constructor
    public Post(int id, int userId, String userLocation, String content, int likeCount, int commentCount, int shareCount, String imageUrl, String timestamp, String avatarUrl) {
        this.id = id;
        this.userId = userId;
        this.userLocation = userLocation;
        this.content = content;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.avatarUrl = avatarUrl;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", userId=" + userId +
                ", userLocation='" + userLocation + '\'' +
                ", content='" + content + '\'' +
                ", likeCount=" + likeCount +
                ", commentCount=" + commentCount +
                ", shareCount=" + shareCount +
                ", imageUrl='" + imageUrl + '\'' +
                ", postType='" + postType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                '}';
    }
}
