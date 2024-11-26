package com.example.model;

public class Comment {
    private int id;
    private int post_id;
    private int user_id;
    private String content;
    private String created_at;
    private User user; // Relasi dengan User

    // Constructor baru untuk membuat komentar dengan content
    public Comment(String content) {
        this.content = content;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPost_id() { return post_id; }
    public void setPost_id(int post_id) { this.post_id = post_id; }

    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
