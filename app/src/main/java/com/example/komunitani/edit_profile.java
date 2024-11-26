package com.example.komunitani;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.api.ApiService;
import com.example.model.PasswordChangeResponse;
import com.example.model.ProfileData;
import com.example.model.ProfileResponse;
import com.example.model.ProfileUpdateResponse;
import com.example.model.User;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class edit_profile extends AppCompatActivity {

    private ImageView headerImageView, changeHeaderIcon, profileImageView;
    private EditText usernameEditText, bioEditText, emailEditText;
    private EditText currentPasswordEditText, newPasswordEditText, confirmPasswordEditText;
    private Button saveProfileButton, savePasswordButton, deleteAccountButton;

    private static final int PICK_IMAGE_HEADER = 1;
    private static final int PICK_IMAGE_PROFILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Inisialisasi elemen-elemen UI
        headerImageView = findViewById(R.id.header_image_view);
        changeHeaderIcon = findViewById(R.id.change_header_icon);
        profileImageView = findViewById(R.id.profile_image_view);
        usernameEditText = findViewById(R.id.username_edit_text);
        bioEditText = findViewById(R.id.bio_edit_text);
        emailEditText = findViewById(R.id.email_edit_text);
        currentPasswordEditText = findViewById(R.id.current_password_edit_text);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        saveProfileButton = findViewById(R.id.save_profile_button);
        savePasswordButton = findViewById(R.id.save_password_button);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        // Logika untuk mengganti Header Image
        changeHeaderIcon.setOnClickListener(v -> openImagePicker(PICK_IMAGE_HEADER));

        // Logika untuk mengganti Foto Profil
        profileImageView.setOnClickListener(v -> openImagePicker(PICK_IMAGE_PROFILE));

        // Logika untuk menyimpan perubahan profil
        saveProfileButton.setOnClickListener(v -> saveProfileChanges());

        // Logika untuk menyimpan kata sandi baru
        savePasswordButton.setOnClickListener(v -> savePassword());

        // Logika untuk menghapus akun
        deleteAccountButton.setOnClickListener(v -> showDeleteAccountConfirmation());

        // Ambil data pengguna dari API
        getUserProfile();
    }

    private void showCustomToast(String message, boolean isSuccess) {
        // Inflate custom toast layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container));

        // Set text and icon
        TextView toastText = layout.findViewById(R.id.toast_text);
        ImageView toastIcon = layout.findViewById(R.id.toast_icon);

        toastText.setText(message);
        toastIcon.setImageResource(isSuccess ? R.drawable.thumb : R.drawable.add); // Sesuaikan ikon

        // Buat Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG); // SHORT or LONG
        toast.setView(layout);
        toast.show();
    }


    private void openImagePicker(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    private void getUserProfile() {
        // Ambil token dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "No token found. Please log in again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
            return;
        }

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Panggil API untuk mengambil data profil
        Call<ProfileResponse> call = apiService.getProfile("Bearer " + token);
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileResponse profileResponse = response.body();

                    if (profileResponse.isSuccess()) {
                        // Ambil objek ProfileData dari respons
                        ProfileData profileData = profileResponse.getData();
                        User user = profileData.getUser();

                        // Isi data profil pengguna ke dalam UI
                        usernameEditText.setText(user.getName());
                        bioEditText.setText(user.getBio());
                        emailEditText.setText(user.getEmail());

                        // Set gambar header dan profil (gunakan Picasso atau Glide)
                        // Picasso.get().load(user.getHeaderImage()).into(headerImageView);
                        // Picasso.get().load(user.getProfileImage()).into(profileImageView);

                        // Opsional: Jika ingin menampilkan jumlah followers dan following
                        int followersCount = profileData.getFollowersCount();
                        int followingCount = profileData.getFollowingCount();
                        Toast.makeText(edit_profile.this,
                                "Followers: " + followersCount + ", Following: " + followingCount,
                                Toast.LENGTH_SHORT).show();
                    } else {
                        showCustomToast("Failed to load profile. Please try again.", false);

                    }
                } else {
                    showCustomToast("Failed to load profile: " + response.message(), false);

                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Toast.makeText(edit_profile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null && data.getData() != null) {
            switch (requestCode) {
                case PICK_IMAGE_HEADER:
                    headerImageView.setImageURI(data.getData());
                    Toast.makeText(this, "Header image updated!", Toast.LENGTH_SHORT).show();
                    break;
                case PICK_IMAGE_PROFILE:
                    profileImageView.setImageURI(data.getData());
                    Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else if (resultCode != RESULT_OK) {
            Toast.makeText(this, "Image selection canceled.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveProfileChanges() {
        // Ambil data dari EditText
        String username = usernameEditText.getText().toString().trim();
        String bio = bioEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        // Validasi input
        if (username.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Username and Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil token dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        if (token.isEmpty()) {
            Toast.makeText(this, "No token found. Please log in again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
            return;
        }

        // Siapkan data untuk request
        Map<String, String> profileData = new HashMap<>();
        profileData.put("name", username);
        profileData.put("email", email);
        profileData.put("bio", bio);

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Panggil API untuk memperbarui profil
        Call<ProfileUpdateResponse> call = apiService.updateProfile("Bearer " + token, profileData);
        call.enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(Call<ProfileUpdateResponse> call, Response<ProfileUpdateResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProfileUpdateResponse updateResponse = response.body();
                    showCustomToast(updateResponse.getMessage(), true);

                    // Perbarui UI dengan data baru
                    User updatedUser = updateResponse.getUser();
                    usernameEditText.setText(updatedUser.getName());
                    bioEditText.setText(updatedUser.getBio());
                    emailEditText.setText(updatedUser.getEmail());

                    // Kembali ke halaman profil
                    Intent intent = new Intent(edit_profile.this, account.class);
                    startActivity(intent);
                    finish();

                } else {
                    showCustomToast("Failed to update profile: " + response.message(), false);

                }
            }

            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                Toast.makeText(edit_profile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void savePassword() {

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        Log.d("TOKEN", "Token sent: " + token);



        if (token.isEmpty()) {
            Toast.makeText(this, "No token found. Please log in again.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, login.class);
            startActivity(intent);
            finish();
            return;
        }


        // Ambil data dari EditText
        String currentPassword = currentPasswordEditText.getText().toString().trim();
        String newPassword = newPasswordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validasi input
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showCustomToast("All password fields are required!", false);

            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showCustomToast("New Password and Confirm Password do not match!", false);

            return;
        }


        // Siapkan data untuk request
        Map<String, String> passwordData = new HashMap<>();
        passwordData.put("current_password", currentPassword);
        passwordData.put("password", newPassword);
        passwordData.put("password_confirmation", confirmPassword);

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://komunitani-v2.vercel.app/api/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Panggil API untuk memperbarui password
        Call<PasswordChangeResponse> call = apiService.updatePassword("Bearer " + token, passwordData);
        call.enqueue(new Callback<PasswordChangeResponse>() {
            @Override
            public void onResponse(Call<PasswordChangeResponse> call, Response<PasswordChangeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API_RESPONSE", "Response: " + response.code() + ", Body: " + response.body());

                    PasswordChangeResponse updateResponse = response.body();
                    showCustomToast(updateResponse.getStatus(), true);

                    // Clear password fields after successful update
                    currentPasswordEditText.setText("");
                    newPasswordEditText.setText("");
                    confirmPasswordEditText.setText("");
                    Log.d("API_RESPONSE", "Response: " + response.code() + ", Body: " + response.body());

                } else {
                    Log.d("API_REQUEST", "Current Password: " + currentPassword);
                    Log.d("API_REQUEST", "New Password: " + newPassword);
                    Log.d("API_REQUEST", "Confirm Password: " + confirmPassword);

                    Log.d("API_REQUEST", "Data sent: " + passwordData.toString());
                    Log.d("API_RESPONSE", "Response Code: " + response.code() + ", Body: " + response.errorBody());


                    Log.e("API_ERROR", "Response Code: " + response.code() + ", Message: " + response.message());
                    showCustomToast("Failed to update password: " + response.message(), false);

                }
            }

            @Override
            public void onFailure(Call<PasswordChangeResponse> call, Throwable t) {
                Toast.makeText(edit_profile.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDeleteAccountConfirmation() {
        // Tampilkan dialog konfirmasi untuk menghapus akun
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? This action cannot be undone.")
                .setPositiveButton("Yes", (dialog, which) -> deleteAccount())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void deleteAccount() {
        // Logika untuk menghapus akun
        Toast.makeText(this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
        // Tambahkan logika untuk logout dan kembali ke halaman login
        Intent intent = new Intent(edit_profile.this, login.class);
        startActivity(intent);
        finish();
    }
}
