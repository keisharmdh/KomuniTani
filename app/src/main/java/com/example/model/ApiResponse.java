package com.example.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiResponse<T> {

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private List<T> data;  // Tetap menggunakan List<T> agar fleksibel

    public boolean isSuccess() {
        return success;
    }

    public List<T> getData() {
        return data;
    }

    // Fungsi untuk mendapatkan data tunggal jika respons berisi satu objek
    public T getSingleData() {
        if (data != null && !data.isEmpty()) {
            return data.get(0);  // Mengembalikan data pertama jika ada
        }
        return null;
    }
}
