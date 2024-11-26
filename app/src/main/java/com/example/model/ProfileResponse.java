package com.example.model;

public class ProfileResponse {
    private boolean success;
    private ProfileData data;

    // Getter untuk success
    public boolean isSuccess() {
        return success;
    }

    // Setter untuk success
    public void setSuccess(boolean success) {
        this.success = success;
    }

    // Getter untuk data
    public ProfileData getData() {
        return data;
    }

    // Setter untuk data
    public void setData(ProfileData data) {
        this.data = data;
    }
}