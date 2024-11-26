package com.example.komunitani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api.ApiService;
import com.example.model.LoginRequest;
import com.example.model.LoginResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.api.RetrofitClient;


public class login extends AppCompatActivity {

    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Mengecek status login menggunakan SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            // Jika sudah login, langsung ke MainActivity
            Log.d(TAG, "User sudah login, langsung ke MainActivity");
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        // Inisialisasi tombol login
        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(view -> loginUser());

        // Menambahkan fungsi untuk lupa password
        TextView lupaPassword = findViewById(R.id.lupa_pw);
        lupaPassword.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, reset_password.class);
            startActivity(intent);
        });

        // Menambahkan fungsi untuk daftar
        TextView daftar = findViewById(R.id.text_click_daftar);
        daftar.setOnClickListener(view -> {
            Intent intent = new Intent(login.this, daftar.class);
            startActivity(intent);
        });
    }

    // Fungsi untuk login
    private void loginUser() {
        EditText etEmail = findViewById(R.id.username);
        EditText etPassword = findViewById(R.id.password);

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validasi input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat request login
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Retrofit setup
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginResponse> call = apiService.login(loginRequest);

        // Proses callback
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    String status = loginResponse.getStatus();
                    String token = loginResponse.getToken();
                    LoginResponse.User user = loginResponse.getUser();

                    // Menyimpan token jika perlu
                    RetrofitClient.token = response.body().getToken();

                    Log.d(TAG, "Login Response Status: " + status);
                    Log.d(TAG, "Token: " + token);
                    Log.d(TAG, "User Name: " + user.getName());

                    // Mengecek apakah token ada sebagai tanda login berhasil
                    if (token != null && !token.isEmpty()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true); // Menyimpan status login
                        editor.putString("token", token); // Menyimpan token
                        editor.putString("username", user.getName()); // Simpan username
                        editor.putString("email", user.getEmail()); // Simpan email jika dibutuhkan
                        editor.apply();

                        String savedUsername = sharedPreferences.getString("username", "Not Saved");
                        Log.d(TAG, "Username yang disimpan di SharedPreferences: " + savedUsername);



                        // Beralih ke MainActivity
                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Login gagal: Tidak ada token", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                        Log.e(TAG, "Error Body: " + errorBody);
                    } catch (IOException e) {
                        Log.e(TAG, "Error reading error body: " + e.getMessage());
                    }
                    Toast.makeText(login.this, "Login gagal: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(login.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Failure: " + t.getMessage());
            }
        });
    }
}
