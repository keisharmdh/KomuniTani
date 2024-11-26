package com.example.model;

import com.google.gson.annotations.SerializedName;

public class LikeResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("liked")
    private boolean liked;

    @SerializedName("like_count")
    private int likeCount;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public boolean isLiked() {
        return liked;
    }

    public int getLikeCount() {
        return likeCount;
    }
}
