package com.example.komunitani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.model.Post;
import com.example.model.SearchResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rvTopics, rvRecentSearch, rvSearchResults;
    private ImageView ivFilter;
    private EditText etSearch;
    private ApiService apiService;
    private SearchResultsAdapter searchResultsAdapter; // Changed to SearchResultsAdapter
    private List<Post> posts;
    private Spinner spinnerTopic, spinnerPostType, spinnerTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initViews();
        setupRecyclerViews();
        initBottomNavigation();
        initRetrofit();

        // Initialize Spinners
        spinnerTopic = findViewById(R.id.spinner_topic);
        spinnerPostType = findViewById(R.id.spinner_post_type);
        spinnerTime = findViewById(R.id.spinner_time);

        // Populate Spinner Data
        setupSpinners();

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                String query = etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    performSearch(query);
                } else {
                    Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        etSearch.setOnClickListener(v -> {
            // Aktifkan kembali search bar jika sebelumnya dinonaktifkan
            etSearch.setEnabled(true);

            // Aktifkan kembali keyboard
            etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            etSearch.requestFocus();
        });


//        ivFilter.setOnClickListener(v -> showFilterDialog());
    }

    private void initViews() {
        rvTopics = findViewById(R.id.rv_topics);
        rvRecentSearch = findViewById(R.id.rv_recent_search);
        rvSearchResults = findViewById(R.id.rv_search_results);
        etSearch = findViewById(R.id.et_search);
        ivFilter = findViewById(R.id.iv_filter);
    }

    private void setupRecyclerViews() {
        setupTopics();
        setupRecentSearches();
    }

    private void initBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.search);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == R.id.message) {
                intent = new Intent(this, MessageActivity.class);
            } else if (item.getItemId() == R.id.search) {
                return true;
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(this, account.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish();
            }

            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (rvSearchResults.getVisibility() == View.VISIBLE) {
            // Jika hasil pencarian muncul, sembunyikan dan tampilkan kembali tampilan awal
            rvSearchResults.setVisibility(View.GONE);
            rvRecentSearch.setVisibility(View.VISIBLE);
            rvTopics.setVisibility(View.VISIBLE);
            findViewById(R.id.tv_recent_search_label).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_topic_label).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_see_all_topics).setVisibility(View.VISIBLE);

            // Pastikan RecyclerView untuk recent search di-update
            setupRecentSearches();
        } else {
            super.onBackPressed();
        }
    }

    private void setupSpinners() {
        // Data for Spinners
        String[] topics = {"Pertanian", "Teknologi", "Ekonomi"};
        String[] postTypes = {"Pertanyaan", "Informasi"};
        String[] times = {"Week", "Month", "Year"};

        // Topic Spinner
        ArrayAdapter<String> topicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, topics);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTopic.setAdapter(topicAdapter);

        // Post Type Spinner
        ArrayAdapter<String> postTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, postTypes);
        postTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPostType.setAdapter(postTypeAdapter);

        // Time Spinner
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, times);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);
    }




    private void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/") // Your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void performSearch(String query) {
        Log.d("SearchActivity", "Searching for: " + query);

        hideRecentAndTopicViews();

        // Sembunyikan keyboard sementara
        etSearch.setInputType(0); // Disable input type (keyboard won't show up)

        // Simpan query pencarian ke SharedPreferences
        saveRecentSearch(query);

        apiService.searchPosts(query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, retrofit2.Response<SearchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SearchResponse searchResponse = response.body();
                    List<Post> posts = searchResponse.getData().getPosts();
                    if (posts != null && !posts.isEmpty()) {
                        showSearchResults(posts);
                    } else {
                        hideSearchResultsView();
                        showToast("Tidak ada hasil ditemukan");
                    }
                } else {
                    hideSearchResultsView();
                    showToast("Tidak ada hasil ditemukan");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                hideSearchResultsView();
                showToast("Kesalahan: " + t.getMessage());
            }
        });


    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void saveRecentSearch(String query) {
        // Ambil riwayat pencarian sebelumnya
        List<String> recentSearches = getRecentSearches();

        // Menambahkan pencarian baru ke list
        recentSearches.add(0, query);

        // Batasi jumlah riwayat pencarian, misalnya hanya 6 yang disimpan
        if (recentSearches.size() > 6) {
            recentSearches.remove(recentSearches.size() - 1);
        }

        // Simpan kembali riwayat pencarian ke SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("recent_search", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(recentSearches);
        editor.putString("search_history", json);
        editor.apply();
    }


    private void showSearchResults(List<Post> posts) {
        if (searchResultsAdapter == null) {
            searchResultsAdapter = new SearchResultsAdapter(this, posts); // Use SearchResultsAdapter
            rvSearchResults.setAdapter(searchResultsAdapter);
            rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        } else {
            searchResultsAdapter.updatePosts(posts); // If adapter already exists, update posts
        }

        rvSearchResults.setVisibility(View.VISIBLE);
    }

    private void hideRecentAndTopicViews() {
        rvRecentSearch.setVisibility(View.GONE);
        rvTopics.setVisibility(View.GONE);
        findViewById(R.id.tv_recent_search_label).setVisibility(View.GONE);
        findViewById(R.id.tv_topic_label).setVisibility(View.GONE);
        findViewById(R.id.btn_see_all_topics).setVisibility(View.GONE);
    }

    private void hideSearchResultsView() {
        rvSearchResults.setVisibility(View.GONE);
    }

    private void setupTopics() {
        List<String> topics = Arrays.asList("Hama", "Pupuk", "Tanaman", "Hama", "Pupuk", "Tanaman", "Hama", "Pupuk", "Tanaman");
        TopicAdapter adapter = new TopicAdapter(topics);
        rvTopics.setLayoutManager(new GridLayoutManager(this, 3));
        rvTopics.setAdapter(adapter);
    }

    private void setupRecentSearches() {
        List<String> recentSearches = getRecentSearches();
        RecentSearchAdapter adapter = new RecentSearchAdapter(recentSearches);
        rvRecentSearch.setAdapter(adapter);
        rvRecentSearch.setLayoutManager(new LinearLayoutManager(this));
    }

    private List<String> getRecentSearches() {
        SharedPreferences sharedPreferences = getSharedPreferences("recent_search", MODE_PRIVATE);
        String json = sharedPreferences.getString("search_history", null);
        if (json != null) {
            Type type = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

//    private void showFilterDialog() {
//        Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_filter);
//        dialog.setCancelable(true);
//
//        dialog.show();
//    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
