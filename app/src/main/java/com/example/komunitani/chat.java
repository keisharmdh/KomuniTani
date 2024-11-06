package com.example.komunitani;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.model.ChatMessage;
import java.util.ArrayList;
import java.util.List;

public class chat extends AppCompatActivity { // Ubah nama kelas dari ChatActivity menjadi Chat
    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;
    private EditText messageInput;
    private ImageView sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Pastikan layout yang digunakan benar

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageInput.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    messages.add(new ChatMessage(messageText, true));
                    messageInput.setText("");
                    chatAdapter.notifyItemInserted(messages.size() - 1);
                    recyclerViewChat.scrollToPosition(messages.size() - 1);
                }
            }
        });
    }
}
