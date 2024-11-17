package com.example.api;

import com.example.model.LoginRequest;
import com.example.model.LoginResponse;
import com.example.model.Post;
import com.example.model.RegisterRequest;
import com.example.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {
    // Ambil post dengan endpoint yang tepat
    @GET("posts") // Contoh endpoint yang benar
    Call<List<Post>> getPosts();

    // Endpoint untuk registrasi pengguna
    @POST("api/v/register") // Sesuaikan dengan endpoint yang sesuai
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @POST("/users") // Ganti dengan endpoint login API Anda
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}

