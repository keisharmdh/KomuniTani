package com.example.komunitani;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import com.example.model.Post;


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
    }

    private void loadPosts() {
        // Tambahkan contoh data ke dalam postList
        // Menambahkan contoh data ke dalam postList
        postList.add(new Post("Cara Menanam Padi", "Intan Permatasari", "Isi konten disini", 0, 0, 0, R.drawable.header_account));
        postList.add(new Post("Cara Berkebun", "Agus Santoso", "Isi konten disini", 0, 0, 0, R.drawable.header_account));


        // Notifikasi adapter untuk update tampilan
        postAdapter.notifyDataSetChanged();
    }
}
