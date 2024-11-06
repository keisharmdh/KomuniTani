package com.example.komunitani;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;
import com.example.model.Post;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Data Post
        List<Post> postList = new ArrayList<>();
        // Menambahkan contoh data ke dalam postList
        postList.add(new Post("Cara Menanam Padi", "Intan Permatasari", "Isi konten disini", 0, 0, 0, R.drawable.header_account));
        postList.add(new Post("Cara Berkebun", "Agus Santoso", "Isi konten disini", 0, 0, 0, R.drawable.header_account));

        // Set Adapter
        PostAdapter adapter = new PostAdapter(this, postList); // Pastikan PostAdapter menerima Context dan List<Post>
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Inisialisasi Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

//        // Set listener untuk item yang dipilih di bottom navigation
//        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.home:
//                    Toast.makeText(MainActivity.this, "Home Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.message:
//                    Toast.makeText(MainActivity.this, "Message Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.search:
//                    Toast.makeText(MainActivity.this, "Search Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.chatbot:
//                    Toast.makeText(MainActivity.this, "ChatBot Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.profile:
//                    Toast.makeText(MainActivity.this, "Profile Selected", Toast.LENGTH_SHORT).show();
//                    return true;
//            }
//            return false;
//        });
    }
}
