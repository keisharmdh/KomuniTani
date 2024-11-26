package com.example.model;

public class UserResponse {
    private boolean success; // Status keberhasilan
    private UserProfile data; // Data user profile

    // Getter dan Setter untuk success
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter dan Setter untuk data
    public UserProfile getData() {
        return data;
    }

    public void setData(UserProfile data) {
        this.data = data;
    }
}