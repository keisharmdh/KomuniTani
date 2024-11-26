package com.example.model;

public class PostRequest {
    private String user_id;
    private String title;
    private String content;
    private String image_path;
    private String topic;
    private String post_type;

    public PostRequest(String user_id, String title, String content, String topic, String post_type, String image_path) {
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.post_type = post_type;
        this.image_path = image_path;
    }

    // Getters and Setters
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPost_type() {
        return post_type;
    }

    public void setPost_type(String post_type) {
        this.post_type = post_type;
    }
}
