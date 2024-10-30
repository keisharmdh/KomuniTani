package com.example.api;

import com.example.model.User; // Pastikan path ini sesuai dengan lokasi kelas User

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("api.php")
    Call<List<User>> getUsers();

    @POST("api.php")
    Call<User> createUser(@Body User user);


}
