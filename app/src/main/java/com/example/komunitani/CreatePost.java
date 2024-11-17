package com.example.komunitani;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CreatePost extends AppCompatActivity {

    private ImageButton btnBack, btnBold, btnItalic, btnUnderline, btnUploadImage;
    private EditText inputJudul, inputDeskripsi;
    private Spinner spinnerTopik;
    private Button btnSend, btnPertanyaan, btnInformasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Inisialisasi View
        btnBack = findViewById(R.id.btnBack);
        btnBold = findViewById(R.id.btnBold);
        btnItalic = findViewById(R.id.btnItalic);
        btnUnderline = findViewById(R.id.btnUnderline);
        btnUploadImage = findViewById(R.id.btnUploadImage);
        inputJudul = findViewById(R.id.inputJudul);
        inputDeskripsi = findViewById(R.id.inputDeskripsi);
        spinnerTopik = findViewById(R.id.spinnerTopik);
        btnSend = findViewById(R.id.btnSend);
        btnPertanyaan = findViewById(R.id.btnPertanyaan); // Inisialisasi btnPertanyaan
        btnInformasi = findViewById(R.id.btnInformasi);   // Inisialisasi btnInformasi

        // Tombol Back
        btnBack.setOnClickListener(view -> finish());

        // Tombol Kirim
        btnSend.setOnClickListener(view -> {
            String judul = inputJudul.getText().toString().trim();
            String deskripsi = inputDeskripsi.getText().toString().trim();
            String topik = spinnerTopik.getSelectedItem().toString();

            if (judul.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(CreatePost.this, "Judul dan Deskripsi harus diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Kirim data ke server
                sendPostToServer(judul, topik, deskripsi);
            }
        });

        // Tombol Bold
        btnBold.setOnClickListener(view -> {
            // Logika untuk menambahkan efek bold
            Toast.makeText(this, "Bold Button Clicked", Toast.LENGTH_SHORT).show();
        });

        // Tombol Italic
        btnItalic.setOnClickListener(view -> {
            // Logika untuk menambahkan efek italic
            Toast.makeText(this, "Italic Button Clicked", Toast.LENGTH_SHORT).show();
        });

        // Tombol Underline
        btnUnderline.setOnClickListener(view -> {
            // Logika untuk menambahkan efek underline
            Toast.makeText(this, "Underline Button Clicked", Toast.LENGTH_SHORT).show();
        });

        // Tombol Upload Gambar
        btnUploadImage.setOnClickListener(view -> {
            // Logika untuk membuka galeri atau kamera
            Toast.makeText(this, "Upload Image Clicked", Toast.LENGTH_SHORT).show();
        });

        // Logika Tombol Pertanyaan & Informasi
        btnPertanyaan.setOnClickListener(view -> {
            btnPertanyaan.setSelected(true);
            btnInformasi.setSelected(false);
        });

        btnInformasi.setOnClickListener(view -> {
            btnInformasi.setSelected(true);
            btnPertanyaan.setSelected(false);
        });
    }

    private void sendPostToServer(String judul, String topik, String deskripsi) {
        // Logika untuk mengirim data ke server
        // Bisa menggunakan Retrofit, Volley, atau library HTTP lainnya
        Toast.makeText(this, "Post berhasil dikirim:\nJudul: " + judul + "\nTopik: " + topik + "\nDeskripsi: " + deskripsi, Toast.LENGTH_SHORT).show();
    }
}
