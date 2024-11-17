package com.example.komunitani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish(); // Menutup LoginActivity
        }

        // Inisialisasi tombol login dan listener
        Button btn_Login = findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        // Menambahkan fungsi untuk lupa password
        TextView clickableText_lupa_password = findViewById(R.id.lupa_pw);
        clickableText_lupa_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, reset_password.class);
                startActivity(intent);
            }
        });

        // Menambahkan fungsi untuk daftar
        TextView clickableText_daftar = findViewById(R.id.text_click_daftar);
        clickableText_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, daftar.class);
                startActivity(intent);
            }
        });
    }

    // Fungsi untuk login dan mendapatkan token dari API
    private void loginUser() {
        EditText etEmail = findViewById(R.id.username);
        EditText etPassword = findViewById(R.id.password);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Validasi input
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan Password harus diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        // Membuat request login
        LoginRequest loginRequest = new LoginRequest(email, password);

        // Retrofit setup tetap sama
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/v/login")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginResponse> call = apiService.login(loginRequest);

        // Proses callback tetap sama
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        String token = loginResponse.getToken();
                        if (token != null && !token.isEmpty()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userToken", token);
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();

                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(login.this, "Token kosong", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
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
            }
        });
    }


}
