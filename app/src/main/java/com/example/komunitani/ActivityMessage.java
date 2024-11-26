//package com.example.komunitani;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.model.ChatMessage;
//import com.example.komunitani.ChatAdapter;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class ActivityMessage extends AppCompatActivity {
//    private RecyclerView chatRecyclerView;
//    private ChatAdapter chatAdapter;
//    private List<ChatMessage> chatMessages = new ArrayList<>();
//    private EditText searchBar;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message);
//
//        chatRecyclerView = findViewById(R.id.chat_recycler_view);
//        searchBar = findViewById(R.id.search_bar);
//
//        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        chatAdapter = new ChatAdapter(chatMessages);
//        chatRecyclerView.setAdapter(chatAdapter);
//
//        // Load chat data
//        fetchChatData();
//
////        // Search feature (optional)
////        searchBar.setOnEditorActionListener((v, actionId, event) -> {
////            String query = searchBar.getText().toString();
////            filterChats(query);
////            return true;
////        });
//    }
//
//    private void fetchChatData() {
//        String url = "https://komunitani-v2.vercel.app/api/api/"; // Ganti dengan URL API Anda
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                runOnUiThread(() -> Toast.makeText(ActivityMessage.this, "Failed to load data", Toast.LENGTH_SHORT).show());
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) {
//                try {
//                    if (response.isSuccessful() && response.body() != null) {
//                        String responseData = response.body().string();
//                        JSONArray usersArray = new JSONObject(responseData).getJSONArray("users");
//
//                        chatMessages.clear();
//                        for (int i = 0; i < usersArray.length(); i++) {
//                            JSONObject userObject = usersArray.getJSONObject(i);
//                            String name = userObject.getString("name");
//                            String lastMessage = userObject.optString("lastMessage", "No message");
//                            String lastMessageTime = userObject.optString("lastMessageCreatedAt", "");
//
//                            ChatMessage chatMessage = new ChatMessage(name, lastMessage, lastMessageTime, false);
//                            chatMessages.add(chatMessage);
//                        }
//
//                        runOnUiThread(() -> chatAdapter.notifyDataSetChanged());
//                    }
//                } catch (Exception e) {
//                    runOnUiThread(() -> Toast.makeText(ActivityMessage.this, "Error parsing data", Toast.LENGTH_SHORT).show());
//                }
//            }
//        });
//    }
//
////    private void filterChats(String query) {
////        List<ChatMessage> filteredList = new ArrayList<>();
////        for (ChatMessage chat : chatMessages) {
////            if (chat.getName().toLowerCase().contains(query.toLowerCase())) {
////                filteredList.add(chat);
////            }
////        }
////        chatAdapter.updateList(filteredList);
////    }
//}
