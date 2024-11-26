package com.example.model;

import com.google.gson.annotations.SerializedName;

public class FollowResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    // Getter dan Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
