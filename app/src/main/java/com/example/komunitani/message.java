package com.example.komunitani;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Message; // Pastikan ini sesuai dengan nama package Anda

public class message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Atur layout dan padding insets
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inisialisasi RecyclerView dan data
        RecyclerView chatRecyclerView = findViewById(R.id.chat_recycler_view);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("Asep Garut", "Stand up for what you believe in", 9));
        messageList.add(new Message("Bu Alma", "One day you're seventeen and planning...", 2));
        // Tambahkan data lain sesuai kebutuhan

        MessageAdapter adapter = new MessageAdapter(messageList);
        chatRecyclerView.setAdapter(adapter);
    }
}
