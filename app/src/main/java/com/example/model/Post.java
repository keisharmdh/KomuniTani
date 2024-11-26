package com.example.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Post {
    @SerializedName("id")
    private int id;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("topic")
    private String topic;

    @SerializedName("post_type")
    private String postType;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("user")
    private User user;

    @SerializedName("likes")
    private List<Like> likes;

    private boolean isLiked;

    @SerializedName("comments")
    private List<Comment> comments;

    // Getter methods
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTopic() {
        return topic;
    }

    public String getPostType() {
        return postType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public User getUser() {
        return user;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
