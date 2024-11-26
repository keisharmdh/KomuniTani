package com.example.komunitani;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.api.RetrofitClient;
import com.example.model.ApiResponse;
import com.example.model.Post;
import com.example.model.PostRequest;
import com.example.model.PostResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private LinearLayout postBar, postOptions;
    private RecyclerView recyclerView;
    private EditText postTitle, postDescription;
    private Button btnPost;
    private Spinner spinner;
    private Spinner spinnerPostType;
    private TextView textView;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Components
        initializeUI();

        // Setup Spinner
        setupSpinner();

        // Set listeners
        setListeners();



        // Check login status
        checkLoginStatus();

        // Fetch posts from API
        fetchPosts();



        // FloatingActionButton listener
        FloatingActionButton fab = findViewById(R.id.fab_create_post);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CreatePost.class);
            startActivity(intent);
            Log.d("CreatePost", "FAB clicked, starting CreatePost activity");

        });



        // BottomNavigationView listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == R.id.message) {
                intent = new Intent(this, MessageActivity.class);
            } else if (item.getItemId() == R.id.search) {
                intent = new Intent(this, SearchActivity.class);
            } else if (item.getItemId() == R.id.chatbot) {
                intent = new Intent(this, chatbot.class);
            } else if (item.getItemId() == R.id.profil) {
                intent = new Intent(this, account.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        RetrofitClient.token = sharedPreferences.getString("token", null);

        if (RetrofitClient.token == null || RetrofitClient.token.isEmpty()) {
            Toast.makeText(this, "Token is missing! Please login again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
            return;
        }

        Log.d("Retrofit Token", "Token: " + RetrofitClient.token);

        // Panggil fetchPosts untuk memuat data
        fetchPosts();

        postAdapter = new PostAdapter(this, postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(postAdapter);

        // Tombol Kirim
        btnPost.setOnClickListener(view -> {

            // Sembunyikan keyboard
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }


            String judul = postTitle.getText().toString().trim();
            String deskripsi = postDescription.getText().toString().trim();
            String topik = spinner.getSelectedItem().toString();
            String postType = spinnerPostType.getSelectedItem().toString();

            if (judul.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(MainActivity.this, "Judul dan Deskripsi harus diisi!", Toast.LENGTH_SHORT).show();
            } else if (topik.equals("Pilih Topik") || postType.equals("Pilih Tipe")) {
                Toast.makeText(MainActivity.this, "Pilih topik dan tipe posting!", Toast.LENGTH_SHORT).show();
            } else {
                sendPostToServer(judul, topik, deskripsi, postType);
            }
        });




    }






    private void initializeUI() {
        recyclerView = findViewById(R.id.recyclerView);
        postBar = findViewById(R.id.post_bar);
        postOptions = findViewById(R.id.post_actions);
        postTitle = findViewById(R.id.post_title);
        postDescription = findViewById(R.id.post_description);
        spinnerPostType = findViewById(R.id.post_type);
        btnPost = findViewById(R.id.btn_post);
        spinner = findViewById(R.id.spinner_topic);
        textView = findViewById(R.id.textView);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);

    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.topics,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.post_tye, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPostType.setAdapter(typeAdapter);
    }

    private void setListeners() {
        postBar.setOnClickListener(this::expandPostOptions);
    }

    private void checkLoginStatus() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        boolean isGuest = getIntent().getBooleanExtra("isGuest", false);
        String userToken = sharedPreferences.getString("token", null);

        if (!isGuest && (!isLoggedIn || userToken == null || userToken.isEmpty())) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }
    }




    private void fetchPosts() {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/")
                .create(ApiService.class);

        apiService.getPosts().enqueue(new Callback<ApiResponse<Post>>() {
            @Override
            public void onResponse(Call<ApiResponse<Post>> call, Response<ApiResponse<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Response", "Success: " + response.body());
                    List<Post> posts = response.body().getData();
                    Log.d("API Response", "Posts: " + posts.toString());
                    setupRecyclerView(posts);
                } else {
                    Log.e("API Response", "Error Code: " + response.code());
                    try {
                        if (response.errorBody() != null) {
                            Log.e("API Response", "Error Body: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("API Response", "Error Body: " + response.errorBody());
                    Log.e("API Response", "Failed: " + response.code());
                    Toast.makeText(MainActivity.this, "Failed to load posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API Failure", "Error: " + t.getMessage());
            }
        });
    }




    private void setupRecyclerView(List<Post> postList) {
        PostAdapter adapter = new PostAdapter(this, postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void sendPostToServer(String judul, String topik, String deskripsi, String postType) {
        String userId = "14"; // Replace with actual user ID
        String imagePath = null; // No image for now

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String yourToken = sharedPreferences.getString("token", null);

        if (yourToken == null) {
            Toast.makeText(MainActivity.this, "Token tidak ditemukan. Silakan login kembali.", Toast.LENGTH_SHORT).show();
            return;
        }

        PostRequest postRequest = new PostRequest(userId, judul, deskripsi, topik, postType, imagePath);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    okhttp3.Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " + yourToken)
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<PostResponse> call = apiService.createPost(postRequest);

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(MainActivity.this, "Post berhasil dikirim", Toast.LENGTH_SHORT).show();

                    // Sembunyikan tampilan expanded setelah berhasil kirim post
                    expandPostOptions(null);

                    // Refresh daftar post setelah post berhasil
                    fetchPosts();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mengirim post: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("Post Error", "Error Body: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Koneksi gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }



    private void expandPostOptions(View view) {
        if (postOptions.getVisibility() == View.GONE) {
            postOptions.setVisibility(View.VISIBLE);
            postBar.setVisibility(View.GONE);
        } else {
            postOptions.setVisibility(View.GONE);
            postBar.setVisibility(View.VISIBLE);
        }
    }


    private void openMediaPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/* video/*");
        startActivityForResult(Intent.createChooser(intent, "Select Media"), 1);
    }


}
