package com.example.komunitani;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rvTopics, rvRecentSearch;
    private ImageView ivFilter;
    private EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Inisialisasi RecyclerView
        rvTopics = findViewById(R.id.rv_topics);
        rvRecentSearch = findViewById(R.id.rv_recent_search);
        etSearch = findViewById(R.id.et_search);

        // Memanggil metode untuk mengatur data RecyclerView
        setupTopics();
        setupRecentSearches();

        // Inisialisasi ikon filter
        ivFilter = findViewById(R.id.iv_filter);
        ivFilter.setOnClickListener(v -> showFilterDialog());

        // Tangkap event "Enter" di EditText
        etSearch.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = etSearch.getText().toString().trim();
                if (!query.isEmpty()) {
                    saveSearch(query); // Simpan pencarian ke recent search
                    etSearch.setText(""); // Kosongkan input
                    setupRecentSearches(); // Refresh RecyclerView
                    Toast.makeText(this, "Pencarian: " + query, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Input tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        // Mengatur BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.search); // Set item aktif ke "Search"

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == R.id.message) {
                intent = new Intent(this, Message.class);
            } else if (item.getItemId() == R.id.search) {
                // Tetap di halaman ini, tidak ada perubahan aktivitas
                return true;
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(this, profil.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish(); // Tutup aktivitas saat ini
            }

            return true;
        });
    }

    // Metode untuk mengatur RecyclerView topik
    private void setupTopics() {
        List<String> topics = Arrays.asList("Hama", "Pupuk", "Tanaman", "Hama", "Pupuk", "Tanaman", "Hama", "Pupuk", "Tanaman");
        TopicAdapter adapter = new TopicAdapter(topics);
        rvTopics.setLayoutManager(new GridLayoutManager(this, 3)); // Grid dengan 3 kolom
        rvTopics.setAdapter(adapter);

        Button lessButton = findViewById(R.id.lessButton);
        Button btnSeeAll = findViewById(R.id.btn_see_all_topics);
        btnSeeAll.setOnClickListener(v -> {
            adapter.setShowAll(true); // Tampilkan semua topik
            btnSeeAll.setVisibility(View.GONE); // Sembunyikan tombol "See All"
            lessButton.setVisibility(View.VISIBLE);
        });

        // Kembali ke posisi awal (hanya sebagian topik)

        lessButton.setOnClickListener(v -> {
            adapter.setShowAll(false); // Tampilkan hanya sebagian data
            btnSeeAll.setVisibility(View.VISIBLE); // Tampilkan tombol "See All"
            lessButton.setVisibility(View.GONE); // Sembunyikan tombol "Lebih Sedikit"
        });
    }

    // Metode untuk mengatur RecyclerView recent search
    private void setupRecentSearches() {
        List<String> recentSearches = getRecentSearches();

        // Debugging
        System.out.println("Recent Searches for Adapter: " + recentSearches);

        // Menambahkan data ke adapter
        RecentSearchAdapter adapter = new RecentSearchAdapter(recentSearches);
        rvRecentSearch.setAdapter(adapter);
        rvRecentSearch.setLayoutManager(new LinearLayoutManager(this));
    }

    // Metode untuk menyimpan pencarian terbaru
    private void saveSearch(String newSearchTerm) {
        List<String> recentSearches = getRecentSearches();
        if (!recentSearches.contains(newSearchTerm)) {
            recentSearches.add(newSearchTerm); // Tambahkan pencarian baru
            if (recentSearches.size() > 10) {
                recentSearches.remove(0); // Batasi maksimal 10 pencarian
            }
            SharedPreferences sharedPreferences = getSharedPreferences("recent_search", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(recentSearches); // Convert ke JSON
            editor.putString("search_history", json);
            editor.apply();

            // Refresh RecyclerView dengan data terbaru
            setupRecentSearches(); // Memanggil metode untuk memperbarui data di RecyclerView
        }
    }


    // Metode untuk mengambil pencarian terbaru
    private List<String> getRecentSearches() {
        SharedPreferences sharedPreferences = getSharedPreferences("recent_search", MODE_PRIVATE);
        String json = sharedPreferences.getString("search_history", null);

        // Debugging
        System.out.println("Retrieved JSON: " + json);

        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {}.getType();
            return gson.fromJson(json, type); // Konversi JSON ke List
        } else {
            return new ArrayList<>(); // Kembalikan list kosong jika tidak ada data
        }
    }

    // Menampilkan dialog filter
    private void showFilterDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_filter);
        dialog.setCancelable(true);

        // Inisialisasi elemen dialog
        RadioGroup rgPostType = dialog.findViewById(R.id.rg_post_type);
        EditText etDateFrom = dialog.findViewById(R.id.et_date_from);
        EditText etDateTo = dialog.findViewById(R.id.et_date_to);
        Button btnApplyFilter = dialog.findViewById(R.id.btn_apply_filter);

        // Set up DatePicker untuk Tanggal Dari
        etDateFrom.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SearchActivity.this,
                    (view, year, month, dayOfMonth) -> etDateFrom.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Set up DatePicker untuk Tanggal Hingga
        etDateTo.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    SearchActivity.this,
                    (view, year, month, dayOfMonth) -> etDateTo.setText(dayOfMonth + "/" + (month + 1) + "/" + year),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        // Set up tombol Terapkan
        btnApplyFilter.setOnClickListener(v -> {
            int selectedPostTypeId = rgPostType.getCheckedRadioButtonId();
            RadioButton selectedPostType = dialog.findViewById(selectedPostTypeId);
            String dateFrom = etDateFrom.getText().toString();
            String dateTo = etDateTo.getText().toString();

            // Logika filter berdasarkan pilihan
            Toast.makeText(SearchActivity.this, "Filter diterapkan: " + selectedPostType.getText() + ", Tanggal: " + dateFrom + " sampai " + dateTo, Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        dialog.show();
    }
}
