package com.example.komunitani;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.model.ChatModel;
import com.example.model.MsgModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chatbot extends AppCompatActivity {

    private EditText userMsgEdit;
    private FloatingActionButton sendFAB;
    private final String BOT_KEY = "bot";
    private final String USER_KEY = "user";
    private ArrayList<ChatModel> chatModelArrayList;
    private ChatBotRVAdapter chatBotRVAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        RecyclerView chatRV = findViewById(R.id.RVChat);
        userMsgEdit = findViewById(R.id.EditMsg);
        sendFAB = findViewById(R.id.FABSend);
        chatModelArrayList = new ArrayList<>();
        chatBotRVAdapter = new ChatBotRVAdapter(chatModelArrayList, this);
        linearLayoutManager = new LinearLayoutManager(this);
        chatRV.setLayoutManager(linearLayoutManager);
        chatRV.setAdapter(chatBotRVAdapter);

        sendFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userMsgEdit.getText().toString().isEmpty()) {
                    Toast.makeText(chatbot.this, "Please enter your message", Toast.LENGTH_SHORT).show();
                    return;
                }
                getResponse(userMsgEdit.getText().toString());
                userMsgEdit.setText(" ");
            }
        });
    }

    private void getResponse(String message) {
        chatModelArrayList.add(new ChatModel(message, USER_KEY));
        chatBotRVAdapter.notifyDataSetChanged();

        String url = "http://api.brainshop.ai/get?bid=183655&key=RvmNmhWA9ytAceRb&uid=user123&msg=" + message;
        String BASE_URL = "https://api.brainshop.ai/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService retrofitAPI = retrofit.create(ApiService.class);
        Call<MsgModel> call = retrofitAPI.getMessage("183655", "RvmNmhWA9ytAceRb", "user123", message);
        call.enqueue(new Callback<MsgModel>() {
            @Override
            public void onResponse(Call<MsgModel> call, Response<MsgModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MsgModel model = response.body();
                    // Log response for debugging
                    Log.d("ChatbotResponse", "Response: " + model.getCnt());
                    chatModelArrayList.add(new ChatModel(model.getCnt(), BOT_KEY));
                    chatBotRVAdapter.notifyDataSetChanged();
                } else {
                    // Handle failure response
                    Log.e("ChatbotResponse", "API call failed: " + response.code());
                    chatModelArrayList.add(new ChatModel("Please revert your question 2", BOT_KEY));
                    chatBotRVAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MsgModel> call, Throwable throwable) {
                // Handle network failure
                Log.e("ChatbotResponse", "API call failed: " + throwable.getMessage());
                chatModelArrayList.add(new ChatModel("Please revert your question 3", BOT_KEY));
                chatBotRVAdapter.notifyDataSetChanged();
            }
        });
    }
}