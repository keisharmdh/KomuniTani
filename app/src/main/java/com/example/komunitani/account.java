package com.example.komunitani;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.model.Post;
import com.example.model.ProfileData;
import com.example.model.ProfileResponse;
import com.example.model.User;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class account extends AppCompatActivity {

    private static final String TAG = "AccountActivity";
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private PostAdapter likedPostAdapter;
    private List<Post> postList;
    private List<Post> likedPostList;
    private TabLayout tabLayout;
    private static final String SHARED_PREFS = "UserPrefs";

    public static String getSharedPrefsName() {
        return SHARED_PREFS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi data dan adapter
        postList = new ArrayList<>();
        likedPostList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        likedPostAdapter = new PostAdapter(this, likedPostList);
        recyclerView.setAdapter(postAdapter);

        // Inisialisasi TabLayout
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("POST"));
        tabLayout.addTab(tabLayout.newTab().setText("LIKE"));

        // Listener perubahan tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    recyclerView.setAdapter(postAdapter);
                } else if (tab.getPosition() == 1) {
                    recyclerView.setAdapter(likedPostAdapter);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Muat data dari SharedPreferences
        loadSharedPreferencesData();

        // Inisialisasi menu button
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this::showPopupMenu);

        // Inisialisasi tombol Edit Profil
        Button editProfileButton = findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v -> openEditProfile());
    }

    private void loadSharedPreferencesData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        int userId = sharedPreferences.getInt("userId", -1);

        Log.d(TAG, "Token: " + token); // Log token untuk memeriksa apakah valid
        Log.d(TAG, "User  ID: " + userId);

        if (!token.isEmpty()) {
            fetchProfileData(token);
        } else {
            Toast.makeText(this, "Token tidak ditemukan. Silakan login.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchProfileData(String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        apiService.getProfile("Bearer " + token).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profileResponse = response.body();
                    Log.d(TAG, "Profile data received: " + profileResponse);

                    // Log data yang diterima
                    Log.d(TAG, "User  data: " + profileResponse.getData().getUser ());
                    Log.d(TAG, "Followers count: " + profileResponse.getData().getFollowersCount());
                    Log.d(TAG, "Bio: " + profileResponse.getData().getUser ().getBio());

                    // Ambil data user
                    ProfileData profileData = profileResponse.getData();
                    User user = profileData.getUser ();


                    // Simpan userId di SharedPreferences
                    if (user != null) {
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userId", user.getId()); // Menyimpan userId
                        editor.apply();
                    }

                    Log.d(TAG, "User  ID saved: " + user.getId());



                    // Perbarui username
                    if (user != null) {
                        TextView usernameTextView = findViewById(R.id.username);
                        usernameTextView.setText(user.getName() != null ? user.getName() : "Unknown username");
                    }

                    // Perbarui bio
                    TextView bioTextView = findViewById(R.id.bio); // Pastikan ada TextView untuk bio
                    if (user.getBio() != null) {
                        bioTextView.setText(user.getBio());
                    } else {
                        bioTextView.setText("No bio available");
                    }

                    TextView followersCountTextView = findViewById(R.id.followers_count); // Pastikan ada TextView untuk followers count
                    int followersCount = profileData.getFollowersCount();
                    String followersText = String.format("%d follower%s", followersCount, followersCount == 1 ? "" : "s");
                    followersCountTextView.setText(followersText);

                    // Perbarui following count
                    TextView followingCountTextView = findViewById(R.id.following_count); // Pastikan ada TextView untuk following count
                    int followingCount = profileData.getFollowingCount();
                    String followingText = String.format("%d following", followingCount);
                    followingCountTextView.setText(followingText);

                    // Memberitahukan adapter tentang perubahan data
                    postAdapter.notifyDataSetChanged();
                    likedPostAdapter.notifyDataSetChanged();

                    // Perbarui postList dan likedPostList
                    postList.clear();
                    likedPostList.clear();

                    if (profileData.getPosts() != null) {
                        postList.addAll(profileData.getPosts());
                    }

                    if (profileData.getLikedPosts() != null) {
                        likedPostList.addAll(profileData.getLikedPosts());
                    }

                    // Memberitahukan adapter tentang perubahan data
                    postAdapter.notifyDataSetChanged();
                    likedPostAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(account.this, "Gagal memuat profil.", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error code: " + response.code());
                    Log.e(TAG, "Error message: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(account.this, "Gagal memuat data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Fetch profile failed: " + t.getMessage());
            }
        });
    }


    private void handleTokenExpired() {
        Toast.makeText(this, "Sesi Anda telah berakhir. Silakan login kembali.", Toast.LENGTH_LONG).show();
        performLogout();
    }

    private void openEditProfile() {
        Intent intent = new Intent(account.this, edit_profile.class);
        startActivity(intent);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_account, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int selectedId = item.getItemId();

            if (selectedId == R.id.action_help) {
                openHelpPage();
            } else if (selectedId == R.id.action_logout) {
                showLogoutConfirmationDialog();
            } else {
                return false;
            }

            return true;
        });

        popupMenu.show();
    }

    private void openHelpPage() {
        Intent intent = new Intent(account.this, Help.class);
        startActivity(intent);
    }

    private void showLogoutConfirmationDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.logout_confirm);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        AppCompatButton btnBatal = dialog.findViewById(R.id.btn_batal);
        AppCompatButton btnLogout = dialog.findViewById(R.id.btn_logout);

        btnBatal.setOnClickListener(v -> dialog.dismiss());

        btnLogout.setOnClickListener(v -> {
            dialog.dismiss();
            performLogout();
        });

        dialog.show();
    }

    private void performLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("token");
        editor.apply();

        Intent intent = new Intent(account.this, login.class);
        startActivity(intent);
        finish();
    }
}
