package com.example.komunitani;


public class Post {
    private String userName;
    private String title;
    private int imageRes;

    public Post(String userName, String title, int imageRes) {
        this.userName = userName;
        this.title = title;
        this.imageRes = imageRes;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public int getImageRes() {
        return imageRes;
    }
}
