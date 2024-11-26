package com.example.model;


import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PaginatedData<T> {
    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("data")
    private List<T> data;

    public int getCurrentPage() {
        return currentPage;
    }

    public List<T> getData() {
        return data;
    }
}
