package com.example.model;

import java.util.List;

public class ProfileData {
    private User user;
    private List<Post> posts;
    private List<Post> likedPosts;
    private int followers_count;
    private int following_count;

    // Getter dan Setter untuk user
    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    // Getter dan Setter untuk posts
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Getter dan Setter untuk likedPosts
    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    // Getter dan Setter untuk followers_count
    public int getFollowersCount() {
        return followers_count;
    }

    public void setFollowersCount(int followers_count) {
        this.followers_count = followers_count;
    }

    // Getter dan Setter untuk following_count
    public int getFollowingCount() {
        return following_count;
    }

    public void setFollowingCount(int following_count) {
        this.following_count = following_count;
    }
}