package com.example.model;

import java.util.List;

public class SearchResponse {
    private boolean success;
    private Data data;

    public static class Data {
        private List<Post> posts;
        // Tambahkan field lain jika diperlukan

        // Getter dan Setter
        public List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }
    }

    // Getter dan Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}