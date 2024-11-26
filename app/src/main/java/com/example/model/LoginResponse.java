package com.example.model;

public class LoginResponse {
    private String status;
    private String token;  // Token yang dikirimkan dari API
    private User user; // Nested object yang merupakan objek User

    // Getter dan Setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // Nested class User
    public static class User {
        private int id;
        private String name;
        private String email;
        private String bio;
        private String email_verified_at;
        private String created_at;
        private String updated_at;
        private String profile_picture;
        private String cover_photo;

        // Getter dan Setter
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
            return email_verified_at;
        }

        public void setEmailVerifiedAt(String email_verified_at) {
            this.email_verified_at = email_verified_at;
        }

        public String getCreatedAt() {
            return created_at;
        }

        public void setCreatedAt(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdatedAt() {
            return updated_at;
        }

        public void setUpdatedAt(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getProfilePicture() {
            return profile_picture;
        }

        public void setProfilePicture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getCoverPhoto() {
            return cover_photo;
        }

        public void setCoverPhoto(String cover_photo) {
            this.cover_photo = cover_photo;
        }
    }
}
