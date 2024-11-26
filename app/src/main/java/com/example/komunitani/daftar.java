package com.example.komunitani;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.api.ApiService;
import com.example.model.RegisterRequest;
import com.example.model.RegisterResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.example.api.RetrofitClient;


public class daftar extends AppCompatActivity {

    private EditText etUsername, etEmail, etPassword, etPasswordConfirmation;  // Tambah EditText untuk konfirmasi password
    private Button btnDaftar;
    private ApiService apiService;  // Tambahkan ApiService untuk Retrofit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        // Inisialisasi elemen UI
        etUsername = findViewById(R.id.etusername);
        etEmail = findViewById(R.id.etemail);
        etPassword = findViewById(R.id.etpassword);
        etPasswordConfirmation = findViewById(R.id.etpassword_confirmation); // Inisialisasi EditText untuk konfirmasi password
        btnDaftar = findViewById(R.id.btn_daftar);

        // Inisialisasi Retrofit
        apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String plainPassword = etPassword.getText().toString().trim();
                String passwordConfirmation = etPasswordConfirmation.getText().toString().trim();


                // Validasi input
                if (username.isEmpty() || email.isEmpty() || plainPassword.isEmpty() || passwordConfirmation.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
                } else if (!plainPassword.equals(passwordConfirmation)) {
                    // Cek apakah password dan konfirmasi password cocok
                    Toast.makeText(daftar.this, "Password dan Konfirmasi Password Tidak Cocok", Toast.LENGTH_SHORT).show();
                } else {
                    // Membuat objek RegisterRequest
                    RegisterRequest registerRequest = new RegisterRequest(username, email, plainPassword, plainPassword);

                    // Mengirim permintaan ke API
                    Call<RegisterResponse> call = apiService.registerUser(registerRequest);
                    call.enqueue(new Callback<RegisterResponse>() {
                        @Override
                        public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                RegisterResponse registerResponse = response.body();

                                // Ambil data dari respons
                                String name = registerResponse.getUser().getName();
                                String email = registerResponse.getUser().getEmail();
                                String token = registerResponse.getToken();
                                String status = registerResponse.getStatus();

                                Log.d("RegisterSuccess", "Name: " + name + ", Email: " + email + ", Token: " + token + ", Status: " + status);

                                Toast.makeText(daftar.this, status, Toast.LENGTH_SHORT).show();

                                // Pindah ke halaman login setelah berhasil
                                Intent loginIntent = new Intent(getApplicationContext(), login.class);
                                startActivity(loginIntent);
                                finish();
                            } else {
                                try {
                                    Log.e("RegisterError", "Error body: " + response.errorBody().string());
                                } catch (Exception e) {
                                    Log.e("RegisterError", "Error parsing error body", e);
                                }
                                Toast.makeText(daftar.this, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterResponse> call, Throwable t) {
                            Log.e("RegisterFailure", "Request failed", t);
                            Toast.makeText(daftar.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Text untuk pindah ke halaman login
        TextView clickableTextLogin = findViewById(R.id.text_click_login);
        clickableTextLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(daftar.this, login.class);
                startActivity(intent);
            }
        });
    }
}
