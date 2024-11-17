//package com.example.komunitani;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//public class profile_dashboard extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_profile_dashboard);
//
//        LinearLayout linearProfil = findViewById(R.id.linear_profil);
//        LinearLayout linearBantuan = findViewById(R.id.linear_bantuan);
//        LinearLayout linearLogout = findViewById(R.id.linear_logout);
//
//        // Set onClickListener untuk linearProfil
//        linearProfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(profile_dashboard.this, profil.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set onClickListener untuk linearBantuan
//        linearBantuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(profile_dashboard.this, bantuan.class);
//                startActivity(intent);
//            }
//        });
//
//        // Set onClickListener untuk linearLogout
//        linearLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showLogoutConfirmationDialog();
//            }
//        });
//
//        // Mengatur BottomNavigationView
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.profile); // Set item aktif ke "Search"
//
//        bottomNavigationView.setOnItemSelectedListener(item -> {
//            Intent intent = null;
//
//            if (item.getItemId() == R.id.home) {
//                intent = new Intent(this, MainActivity.class);
//            } else if (item.getItemId() == R.id.message) {
//                intent = new Intent(this, Message.class);
//            } else if (item.getItemId() == R.id.search) {
//                intent = new Intent(this, SearchActivity.class);
//
//            } else if (item.getItemId() == R.id.profile) {
//                // Tetap di halaman ini, tidak ada perubahan aktivitas
//                return true;
//
//            }
//
//            if (intent != null) {
//                startActivity(intent);
//                finish(); // Tutup aktivitas saat ini
//            }
//
//            return true;
//        });
//    }
//
//    // Method untuk menampilkan dialog logout
//    private void showLogoutConfirmationDialog() {
//        // Membuat dialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Inflating layout custom dialog
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.logout_confirm, null);
//        builder.setView(dialogView);
//
//        // Inisialisasi tombol di dalam dialog
//        Button btnBatal = dialogView.findViewById(R.id.btn_batal);
//        Button btnLogout = dialogView.findViewById(R.id.btn_logout);
//
//        // Membuat AlertDialog dan menampilkan
//        AlertDialog dialog = builder.create();
//        dialog.show();
//
//        // Aksi untuk tombol Batal
//        btnBatal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Tutup dialog
//                dialog.dismiss();
//            }
//        });
//
//        // Aksi untuk tombol Logout
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Menghapus status login dan token dari SharedPreferences
//                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("isLoggedIn", false);  // Set status login menjadi false
//                editor.remove("userToken");  // Hapus token
//                editor.apply();
//
//                //  Arahkan pengguna ke LoginActivity
//                Intent intent = new Intent(profile_dashboard.this, login.class);
//                startActivity(intent);
//                finish();  // Menutup ProfileDashboardActivity setelah logout
//            }
//        });
//    }
//
//}
