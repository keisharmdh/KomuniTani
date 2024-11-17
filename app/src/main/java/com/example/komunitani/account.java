package com.example.komunitani;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.model.Post;
import java.util.ArrayList;
import java.util.List;

public class account extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Inisialisasi data dan adapter
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        // Load data
        loadPosts();

        // Inisialisasi menu button
        ImageButton menuButton = findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> showPopupMenu(v));

        Button editProfileButton = findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(v -> openEditProfile());


    }

    private void openEditProfile() {
        Intent intent = new Intent(account.this, edit_profile.class);
        startActivity(intent);
    }


    private void showPopupMenu(View view) {
        // Membuat pop-up menu
        PopupMenu popupMenu = new PopupMenu(this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_account, popupMenu.getMenu());

        // Menangani klik pada item menu
        popupMenu.setOnMenuItemClickListener(item -> {
            int selectedId = item.getItemId();
            if (selectedId == R.id.action_profile) {
                Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show();
                // Tambahkan logika ke halaman Profile
            } else if (selectedId == R.id.action_settings) {
                Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
                // Tambahkan logika ke halaman Settings
            } else if (selectedId == R.id.action_help) {
                Toast.makeText(this, "Bantuan Clicked", Toast.LENGTH_SHORT).show();
                // Tambahkan logika untuk Bantuan
            } else if (selectedId == R.id.action_logout) {
                // Panggil dialog konfirmasi logout
                showLogoutConfirmationDialog();
            } else {
                return false;
            }
            return true;
        });

        popupMenu.show();
    }


    private void loadPosts() {
        // Tambahkan contoh data ke dalam postList
        postList.add(new Post(
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
        ));

        postList.add(new Post(
                2, // id
                102, // userId (misalnya, 102 adalah ID pengguna)
                "Surabaya", // userLocation (misalnya, lokasi pengguna)
                "Cara Berkebun", // content (judul postingan)
                0, // likeCount
                0, // commentCount
                0, // shareCount
                "https://example.com/image2.jpg", // imageUrl (URL gambar)
                "2024-11-16 10:05:00", // timestamp (waktu postingan)
                "https://example.com/avatar2.jpg" // avatarUrl (URL avatar pengguna)
        ));

        // Notifikasi adapter untuk update tampilan
        postAdapter.notifyDataSetChanged();
    }

    // Method untuk menampilkan dialog logout langsung
    private void showLogoutConfirmationDialog() {
        // Buat dialog
        Dialog dialog = new Dialog(this);

        // Set layout dari logout_confirm
        dialog.setContentView(R.layout.logout_confirm);

        // Atur agar dialog memiliki ukuran dinamis
        dialog.getWindow().setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        // Inisialisasi tombol
        AppCompatButton btnBatal = dialog.findViewById(R.id.btn_batal);
        AppCompatButton btnLogout = dialog.findViewById(R.id.btn_logout);

        // Aksi untuk tombol batal
        btnBatal.setOnClickListener(v -> dialog.dismiss());

        // Aksi untuk tombol logout
        btnLogout.setOnClickListener(v -> {
            // Logika untuk logout
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false); // Set status login menjadi false
            editor.remove("userToken"); // Hapus token
            editor.apply();

            // Pindah ke LoginActivity
            Intent intent = new Intent(account.this, login.class);
            startActivity(intent);
            finish(); // Tutup aktivitas saat ini
        });

        // Tampilkan dialog
        dialog.show();
    }



}
