package com.example.komunitani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.PostRequest;
import com.example.model.PostResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.example.api.ApiService; // Pastikan import ini ada

import retrofit2.converter.gson.GsonConverterFactory;

public class CreatePost extends AppCompatActivity {

    private ImageButton btnBack, btnBold, btnItalic, btnUnderline, btnUploadImage;
    private EditText inputJudul, inputDeskripsi;
    private Spinner spinnerTopik;
    private Spinner spinnerPostType;
    private Button btnSend;

    private boolean isPostTypeInformasi = false;  // Default is 'Informasi'

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Inisialisasi View
        btnBack = findViewById(R.id.btnBack);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        inputJudul = findViewById(R.id.inputJudul);
        inputDeskripsi = findViewById(R.id.inputDeskripsi);
        spinnerTopik = findViewById(R.id.spinnerTopik);
        spinnerPostType = findViewById(R.id.spinnerPostType);
        btnSend = findViewById(R.id.btnSend);


        // Tombol Back
        btnBack.setOnClickListener(view -> finish());

        // Tombol Kirim
        btnSend.setOnClickListener(view -> {
            String judul = inputJudul.getText().toString().trim();
            String deskripsi = inputDeskripsi.getText().toString().trim();
            String topik = spinnerTopik.getSelectedItem().toString();
            String postType = spinnerPostType.getSelectedItem().toString();

            if (judul.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(CreatePost.this, "Judul dan Deskripsi harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (topik.equals("Pilih Topik") || postType.equals("Pilih Tipe")) {
                Toast.makeText(CreatePost.this, "Pilih topik dan tipe posting!", Toast.LENGTH_SHORT).show();
            } else {
                sendPostToServer(judul, topik, deskripsi, postType);
            }
        });


//        // Inisialisasi Spinner Topik
//        ArrayList<String> topicList = new ArrayList<>();
//        topicList.add("Pertanian Umum");
//        topicList.add("Pertanian Organik");
//        topicList.add("Pertanian Modern");
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topicList);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerTopik.setAdapter(adapter);

//        // Tombol Pertanyaan dan Informasi
//        btnPertanyaan.setOnClickListener(view -> {
//            isPostTypeInformasi = false;
//            btnPertanyaan.setSelected(true);
//            btnInformasi.setSelected(false);
//        });
//
//        btnInformasi.setOnClickListener(view -> {
//            isPostTypeInformasi = true;
//            btnInformasi.setSelected(true);
//            btnPertanyaan.setSelected(false);
//        });

        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(
                this, R.array.topics, android.R.layout.simple_spinner_item);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopik.setAdapter(topicAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_tye, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPostType.setAdapter(typeAdapter);

    }

    private void sendPostToServer(String judul, String topik, String deskripsi, String postType) {
        String userId = "14"; // Replace with actual user ID
        String imagePath = null; // No image for now

        // Ambil token dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String yourToken = sharedPreferences.getString("token", null);


        if (yourToken == null) {
            Toast.makeText(CreatePost.this, "Token tidak ditemukan. Silakan login kembali.", Toast.LENGTH_SHORT).show();
            return;
        }

        PostRequest postRequest = new PostRequest(userId, judul, deskripsi, topik, postType, imagePath);

        // Buat OkHttpClient dengan header Authorization
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + yourToken)
                            .build();
                    return chain.proceed(request);
                })
                .build();

        // Gunakan Retrofit dengan client
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/") // Ganti dengan URL API Anda
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<PostResponse> call = apiService.createPost(postRequest);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CreatePost.this, "Post berhasil dikirim", Toast.LENGTH_SHORT).show();

                    // Berpindah ke MainActivity setelah post berhasil
                    Intent intent = new Intent(CreatePost.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Menutup activity CreatePost
                } else {
                    Toast.makeText(CreatePost.this, "Gagal mengirim post: " + response.code(), Toast.LENGTH_SHORT).show();
                    System.out.println("Response error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(CreatePost.this, "Koneksi gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }


}
