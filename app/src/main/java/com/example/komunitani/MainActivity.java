package com.example.komunitani;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.ApiService;
import com.example.api.RetrofitClient;
import com.example.komunitani.databinding.ActivityMainBinding;
import com.example.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private LinearLayout postBar, postOptions;
    private RecyclerView recyclerView;
    private EditText postTitle, postDescription;
    private RadioGroup postTypeGroup;
    private Button btnPost;
    private Spinner spinner;
    private TextView textView; // Tambahkan TextView untuk menampilkan data postingan

    ActivityMainBinding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize binding and set content view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Menginisialisasi FloatingActionButton
        FloatingActionButton fab = findViewById(R.id.fab_create_post);

        // Initialize UI Components
        initializeUI();

        // Initialize RecyclerView
        setupRecyclerView();

        // Initialize Spinner
        setupSpinner();

        // Set listeners
        setListeners();

        // Mengecek status login dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        boolean isGuest = getIntent().getBooleanExtra("isGuest", false);
        String userToken = sharedPreferences.getString("userToken", null);

        if (!isGuest && (!isLoggedIn || userToken == null || userToken.isEmpty())) {
            Intent intent = new Intent(MainActivity.this, login.class);
            startActivity(intent);
            finish();
        }

        // Mengambil data postingan dari API
        ApiService apiService = RetrofitClient.getClient("https://api-komunitani-production.up.railway.app/")
                .create(ApiService.class);

        apiService.getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Post> posts = response.body(); // Data postingan dari API
                    PostAdapter adapter = new PostAdapter(MainActivity.this, posts);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Menambahkan click listener untuk FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Misalnya, membuka aktivitas baru untuk membuat postingan
                Intent intent = new Intent(MainActivity.this, CreatePost.class);
                startActivity(intent);
            }
        });

        // BottomNavigationView Listener
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == R.id.message) {
                intent = new Intent(this, Message.class);
            } else if (item.getItemId() == R.id.search) {
                intent = new Intent(this, SearchActivity.class);
            } else if (item.getItemId() == R.id.profil) {
                intent = new Intent(this, account.class);
            }

            if (intent != null) {
                startActivity(intent);
            }
            return true;
        });
    }



    private void initializeUI() {
        recyclerView = findViewById(R.id.recyclerView);
        postBar = findViewById(R.id.post_bar);
        postOptions = findViewById(R.id.post_actions);
        postTitle = findViewById(R.id.post_title);
        postDescription = findViewById(R.id.post_description);
        postTypeGroup = findViewById(R.id.post_type_group);
        btnPost = findViewById(R.id.btn_post);
        spinner = findViewById(R.id.spinner_topic);
        textView = findViewById(R.id.textView);  // Menginisialisasi TextView
    }

    private void setupRecyclerView() {
        List<Post> postList = new ArrayList<>();
        // Post awal kosong atau menggunakan API untuk mengambil data
        PostAdapter adapter = new PostAdapter(this, postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnPost.setOnClickListener(v -> addNewPost(postList, adapter));
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.topics,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setListeners() {
        postBar.setOnClickListener(this::expandPostOptions);
        ImageButton uploadMedia = findViewById(R.id.icon_media);
        uploadMedia.setOnClickListener(v -> openMediaPicker());
    }

    private void addNewPost(List<Post> postList, PostAdapter adapter) {
        int selectedPostTypeId = postTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedPostType = findViewById(selectedPostTypeId);
        String postType = selectedPostType != null ? selectedPostType.getText().toString() : "informasi";

        String title = postTitle.getText().toString();
        String description = postDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Judul dan Deskripsi tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Post newPost = new Post(
                1, // id
                101, // userId (misalnya, 101 adalah ID pengguna)
                "Jakarta", // userLocation (misalnya, lokasi pengguna)
                "Cara Menanam Padi", // content (judul postingan)
                0, // likeCount
                0, // commentCount
                0, // shareCount
                "https://example.com/image1.jpg", // imageUrl (URL gambar)
                "2024-11-16 10:00:00", // timestamp (waktu postingan)
                "https://example.com/avatar1.jpg" // avatarUrl (URL avatar pengguna)
        );
        postList.add(newPost);
        adapter.notifyItemInserted(postList.size() - 1);
        recyclerView.scrollToPosition(postList.size() - 1);
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
