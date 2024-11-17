package com.example.komunitani;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.komunitani.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Atur layout sekali
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        // Atur padding untuk sistem insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0); // Hanya atur padding atas dan samping
            return insets;
        });




        // Inisialisasi RecyclerView dan data
        RecyclerView chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<com.example.model.Message> messageList = new ArrayList<>();
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        messageList.add(new com.example.model.Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new com.example.model.Message("Bu Alma", "One day you're seventeen and planning...", 2));
        // Tambahkan data lain sesuai kebutuhan

        MessageAdapter adapter = new MessageAdapter(messageList);
        chatRecyclerView.setAdapter(adapter);

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.message); // Set active item to "Message"

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Intent intent = null;

            if (item.getItemId() == R.id.home) {
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == R.id.message) {
                // Tetap di halaman ini, tidak ada perubahan aktivitas
                return true;
            } else if (item.getItemId() == R.id.search) {
                intent = new Intent(this, SearchActivity.class);
            } else if (item.getItemId() == R.id.profile) {
                intent = new Intent(this, account.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish(); // Tutup aktivitas saat ini
            }

            return true;
        });
    }
}
