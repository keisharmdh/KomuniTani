package com.example.komunitani;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.ApiService;
import com.example.api.RetrofitClient;
import com.example.model.FollowResponse;
import com.example.model.Post;
import com.example.model.User;
import com.example.model.UserResponse;
import com.example.model.UserProfile;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class account_other extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView userBioTextView;
    private ImageView userProfilePictureImageView;
    private RecyclerView recyclerView;
    private Button followButton;
    private boolean isFollowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_other);

        // Initialize UI elements
        userNameTextView = findViewById(R.id.username);
        userBioTextView = findViewById(R.id.bio);
        userProfilePictureImageView = findViewById(R.id.profile_image);
        recyclerView = findViewById(R.id.recyclerView);
        followButton = findViewById(R.id.follow_button);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get userId from intent
        int userId = getIntent().getIntExtra("userId", -1);

        if (userId != -1) {
            fetchUserProfile(String.valueOf(userId), tabLayout); // Tambahkan tabLayout
        } else {
            Log.e("account_other", "Invalid userId received");
        }

        followButton.setOnClickListener(v -> toggleFollowStatus(String.valueOf(userId)));
    }

    private void fetchUserProfile(String userId, TabLayout tabLayout) {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/")
                .create(ApiService.class);

        apiService.getUserProfile(userId).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("account_other", "Response body: " + response.body().toString());

                    UserResponse userResponse = response.body();

                    // Update UI dengan data pengguna
                    updateUIWithUserProfile(userResponse);

                    // Ambil postingan dan postingan yang disukai
                    List<Post> userPosts = userResponse.getData().getPosts();
                    List<Post> likedPosts = userResponse.getData().getLikedPosts();

                    // Setup TabLayout untuk beralih antara postingan dan postingan yang disukai
                    setupTabLayout(tabLayout, userPosts, likedPosts);
                } else {
                    Log.e("account_other", "Error fetching user profile: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("account_other", "Error: " + t.getMessage());
            }
        });
    }

    private void toggleFollowStatus(String userId) {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/")
                .create(ApiService.class);

        Call<FollowResponse> call = isFollowing
                ? apiService.unfollowUser(userId) // Call `unfollowUser` if currently following
                : apiService.followUser(userId); // Call `followUser` if not currently following

        call.enqueue(new Callback<FollowResponse>() {
            @Override
            public void onResponse(Call<FollowResponse> call, Response<FollowResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FollowResponse followResponse = response.body();

                    if (followResponse.isSuccess()) {
                        isFollowing = !isFollowing; // Toggle follow status
                        updateFollowButton(); // Update button text and style
                        Log.d("FollowStatus", followResponse.getMessage());
                    } else {
                        Log.e("FollowStatus", "Error: " + followResponse.getMessage());
                    }
                } else {
                    Log.e("FollowError", "Response failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FollowResponse> call, Throwable t) {
                Log.e("FollowError", "Error: " + t.getMessage());
            }
        });
    }

    private void setupTabLayout(TabLayout tabLayout, List<Post> userPosts, List<Post> likedPosts) {
        // Tambahkan tab untuk "Post" dan "Like"
        tabLayout.addTab(tabLayout.newTab().setText("Post"));
        tabLayout.addTab(tabLayout.newTab().setText("Like"));

        // Tampilkan data Post secara default
        setupRecyclerView(userPosts);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    // Tab "Post" aktif
                    setupRecyclerView(userPosts);
                } else if (tab.getPosition() == 1) {
                    // Tab "Like" aktif
                    setupRecyclerView(likedPosts);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void setupRecyclerView(List<Post> postList) {
        PostAdapter adapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(adapter);
    }

    private void updateUIWithUserProfile(UserResponse userResponse) {
        UserProfile userProfile = userResponse.getData(); // Pastikan mengambil data dari UserResponse

        if (userProfile != null) {
            // Update data pengguna
            if (userProfile.getUser() != null) {
                User user = userProfile.getUser();

                // Set nama pengguna
                userNameTextView.setText(user.getName() != null ? user.getName() : "Unknown User");

                // Set bio pengguna
                userBioTextView.setText(user.getBio() != null ? user.getBio() : "No bio available");


                // Load foto profil pengguna
                if (user.getProfilePicture() != null && !user.getProfilePicture().isEmpty()) {
                    Glide.with(account_other.this)
                            .load(user.getProfilePicture())
                            .placeholder(R.drawable.foto_profile) // Placeholder jika gambar belum tersedia
                            .into(userProfilePictureImageView);
                } else {
                    userProfilePictureImageView.setImageResource(R.drawable.icon_profile);
                }

                // Tentukan status tombol follow
                isFollowing = user.isFollowing(); // Asumsikan atribut `isFollowing` ada dalam model User
                updateFollowButton();
            }

            // Update jumlah followers dan following
            TextView followersCountTextView = findViewById(R.id.followers_count);
            TextView followingCountTextView = findViewById(R.id.following_count);

            int followersCount = userProfile.getFollowersCount();
            int followingCount = userProfile.getFollowingCount();

            followersCountTextView.setText(String.format("%d follower%s", followersCount, followersCount == 1 ? "" : "s"));
            followingCountTextView.setText(String.format("%d following", followingCount));
        } else {
            Log.e("updateUIWithUserProfile", "UserProfile data is null");
        }
    }


    private void updateFollowButton() {
        if (isFollowing) {
            followButton.setText("Unfollow");
            followButton.setBackgroundColor(getResources().getColor(R.color.unfollow_color));
        } else {
            followButton.setText("Follow");
            followButton.setBackgroundColor(getResources().getColor(R.color.follow_color));
        }
    }
}
