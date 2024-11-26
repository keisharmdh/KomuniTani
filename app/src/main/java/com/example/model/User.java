package com.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {
    private int id;        // ID pengguna
    private String name;   // Nama pengguna
    private String email;  // Email pengguna

    @SerializedName("bio")
    private String bio;    // Biografi pengguna (opsional)

    @SerializedName("email_verified_at")
    private String emailVerifiedAt;  // Waktu verifikasi email (opsional)

    @SerializedName("created_at")
    private String createdAt;  // Tanggal pembuatan akun

    @SerializedName("updated_at")
    private String updatedAt;  // Tanggal pembaruan terakhir

    @SerializedName("profile_picture")
    private String profilePicture;  // URL gambar profil (opsional)

    @SerializedName("cover_photo")
    private String coverPhoto;

    private boolean isFollowing; // URL foto sampul (opsional)

    // Menambahkan properti followers dan follows
    @SerializedName("followers")
    private List<User> followers;  // Daftar followers

    @SerializedName("follows")
    private List<User> follows;    // Daftar following

    // Constructor
    public User() {
    }

    public User(int id, String name, String email, String bio, String emailVerifiedAt, String createdAt, String updatedAt, String profilePicture, String coverPhoto, List<User> followers, List<User> follows) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.emailVerifiedAt = emailVerifiedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.profilePicture = profilePicture;
        this.coverPhoto = coverPhoto;
        this.followers = followers;
        this.follows = follows;
    }

    // Getter dan Setter untuk followers dan follows
    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollows() {
        return follows;
    }

    public void setFollows(List<User> follows) {
        this.follows = follows;
    }

    // Getter dan Setter lainnya
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

    public String getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(String emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public boolean isFollowing() {
        return isFollowing;
    }

    public void setFollowing(boolean following) {
        isFollowing = following;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", emailVerifiedAt='" + emailVerifiedAt + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", coverPhoto='" + coverPhoto + '\'' +
                ", followers=" + followers +
                ", follows=" + follows +
                '}';
    }
}
