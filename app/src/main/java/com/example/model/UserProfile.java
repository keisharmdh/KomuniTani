package com.example.model;

import java.util.List;

public class UserProfile {
    private User user; // Objek user
    private List<Post> posts; // Daftar postingan
    private List<Post> likedPosts; // Daftar postingan yang disukai
    private int followersCount; // Jumlah pengikut
    private int followingCount; // Jumlah yang diikuti
    private List<User> followers; // Daftar pengikut
    private List<User> follows; // Daftar yang diikuti

    // Getter dan Setter untuk user
    public User getUser () {
        return user;
    }

    public void setUser (User user) {
        this.user = user;
    }

    // Getter dan Setter untuk daftar postingan
    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    // Getter dan Setter untuk daftar postingan yang disukai
    public List<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(List<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }

    // Getter dan Setter untuk jumlah pengikut
    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    // Getter dan Setter untuk jumlah yang diikuti
    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    // Getter dan Setter untuk daftar pengikut
    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    // Getter dan Setter untuk daftar yang diikuti
    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }
}