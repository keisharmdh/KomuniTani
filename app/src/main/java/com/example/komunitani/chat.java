package com.example.komunitani;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.model.ChatMessage;
import com.example.model.ChatResponse;
import com.example.model.MessageResponse;
import com.example.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chat extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private List<ChatMessage> messages;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // Terima data receiver_id dari intent
        int receiverId = getIntent().getIntExtra("receiver_id", -1);
        String receiverName = getIntent().getStringExtra("receiver_name");

        if (receiverId != -1) {
            android.util.Log.d("ChatActivity", "Receiver ID: " + receiverId);
        }


//        Log.d(TAG, "ChatAdapter initialized with senderId: " + userId + ", receiverId: " + receiverId);

        // Tampilkan nama pengguna di topBar
        TextView topBar = findViewById(R.id.top_bar);
        if (receiverName != null) {
            topBar.setText(receiverName);
        } else {
            topBar.setText("Unknown User");
        }

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));

        // Ambil userId dan token dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(account.getSharedPrefsName(), MODE_PRIVATE);

        // Ambil userId dan token yang disimpan sebelumnya di SharedPreferences
        int userId = sharedPreferences.getInt("userId", -1); // Ambil userId, default -1 jika tidak ditemukan
        String token = sharedPreferences.getString("token", null); // Ambil token, default null jika tidak ditemukan



        // Log nilai userId dan token
        Log.d("ChatActivity", "userId: " + userId);
        Log.d("ChatActivity", "token: " + token);


        // Inisialisasi daftar pesan dan adapter untuk RecyclerView
        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages, userId, receiverId);
        recyclerViewChat.setAdapter(chatAdapter);


        // Pastikan userId dan token valid sebelum melanjutkan
        if (userId != -1 && token != null) {
            // Retrofit setup
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://komunitani-v2.vercel.app/api/api/") // Ganti dengan URL base API Anda
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);

            // Ambil data pesan
            fetchMessages(receiverId, token);

        } else {
            // Menampilkan pesan jika userId atau token tidak ditemukan
            Toast.makeText(this, "User ID or Token not found", Toast.LENGTH_SHORT).show();
            Log.e("ChatActivity", "User ID or Token not found.");
        }
    }

    private void fetchMessages(int receiverId, String token) {
        Log.d("ChatActivity", "Fetching messages for receiverId: " + receiverId);

        apiService.getMessages(receiverId, "Bearer " + token).enqueue(new Callback<ChatResponse>() {
            @Override
            public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ChatResponse chatResponse = response.body();

                    // Ambil data penerima menggunakan model User
                    User receiverUser = chatResponse.getReceiverUser();
                    if (receiverUser != null) {
                        TextView topBar = findViewById(R.id.top_bar);
                        topBar.setText(receiverUser.getName()); // Tampilkan nama penerima di top bar
                        Log.d("ChatActivity", "Receiver Name: " + receiverUser.getName());
                    }

                    // Update daftar pesan
                    List<ChatMessage> receivedMessages = chatResponse.getMessages();
                    Log.d("ChatActivity", "Received " + receivedMessages.size() + " messages.");

                    messages.clear();
                    messages.addAll(receivedMessages);
                    chatAdapter.notifyDataSetChanged(); // Perbarui RecyclerView
                    recyclerViewChat.scrollToPosition(messages.size() - 1); // Scroll ke pesan terakhir
                } else {
                    Log.e("ChatActivity", "Error: " + response.code() + " - " + response.message());
                    Toast.makeText(chat.this, "Failed to load messages", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ChatResponse> call, Throwable t) {
                Log.e("ChatActivity", "Error fetching messages: " + t.getMessage());
                Toast.makeText(chat.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
