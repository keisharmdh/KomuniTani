package com.example.model;

import com.google.gson.annotations.SerializedName;

public class CommentResponse {
    private boolean success;

    @SerializedName("data")
    private CommentData data;

    public boolean isSuccess() {
        return success;
    }

    public CommentData getData() {
        return data;
    }

    public static class CommentData {
        private int id;

        @SerializedName("post_id")
        private String postId; // Ubah dari int ke String

        private int user_id; // Tetap sebagai int
        private String content;
        private String created_at; // Tanggal dan waktu saat komentar dibuat
        private String updated_at; // Tanggal dan waktu saat komentar diperbarui

        private User user;

        // Getters dan Setters untuk setiap field
        public int getId() {
            return id;
        }

        public String getPostId() {
            return postId;
        }

        public int getUser_id() {
            return user_id;
        }

        public String getContent() {
            return content;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public String getUpdatedAt() {
            return updated_at;
        }

        public User getUser () {
            return user;
        }

        public static class User {
            private int id;
            private String name;
            private String email; // Tambahkan field email
            private String bio; // Tambahkan field bio

            @SerializedName("email_verified_at")
            private String emailVerifiedAt; // Tambahkan field email_verified_at

            private String created_at; // Tanggal dan waktu saat akun dibuat
            private String updated_at; // Tanggal dan waktu saat akun diperbarui
            private String profile_picture; // Tambahkan field profile_picture
            private String cover_photo; // Tambahkan field cover_photo

            // Getters untuk setiap field
            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getEmail() {
                return email;
            }

            public String getBio() {
                return bio;
            }

            public String getEmailVerifiedAt() {
                return emailVerifiedAt;
            }

            public String getCreatedAt() {
                return created_at;
            }

            public String getUpdatedAt() {
                return updated_at;
            }

            public String getProfilePicture() {
                return profile_picture;
            }

            public String getCoverPhoto() {
                return cover_photo;
            }
        }
    }
}