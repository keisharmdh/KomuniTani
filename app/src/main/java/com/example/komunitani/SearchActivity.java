package com.example.komunitani;




import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.example.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    // Deklarasi RecyclerView untuk menampilkan topik dan post populer
    private RecyclerView rvTopics, rvPopularPosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // Menetapkan layout untuk activity ini

        // Inisialisasi RecyclerView dari layout
        rvTopics = findViewById(R.id.rv_topics); // RecyclerView untuk menampilkan daftar topik
        rvPopularPosts = findViewById(R.id.rv_popular_posts); // RecyclerView untuk menampilkan daftar post populer

        // Memanggil metode untuk mengatur data dan layout masing-masing RecyclerView
        setupTopics(); // Menyiapkan RecyclerView untuk topik
        setupPopularPosts(); // Menyiapkan RecyclerView untuk post populer
    }

    // Metode untuk menyiapkan RecyclerView topik
    private void setupTopics() {
        // Data dummy untuk topik
        List<String> topics = Arrays.asList("Hama", "Pupuk", "Tanaman");

        // Membuat adapter untuk RecyclerView topik
        TopicAdapter adapter = new TopicAdapter(topics);

        // Mengatur RecyclerView dengan GridLayoutManager (2 kolom)
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2); // Grid dengan 2 kolom
        rvTopics.setLayoutManager(gridLayoutManager); // Menetapkan LayoutManager
        rvTopics.setAdapter(adapter); // Menetapkan adapter pada RecyclerView
    }

    private static List<Post> getPopularPosts() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post("Cara Mengatasi Hama", "Intan Permatasari", "Isi konten disini", 100, 50, 10, R.drawable.header_account, "informasi"));
        posts.add(new Post("Tips Merawat Tanaman", "Agus Santoso", "Isi konten disini", 200, 80, 15, R.drawable.header_account, "pertanyaan"));
        return posts;
    }

    private void setupPopularPosts() {
        // Mengambil data dari strings.xml
        String[] titles = getResources().getStringArray(R.array.popular_post_titles);
        String[] authors = getResources().getStringArray(R.array.popular_post_authors);

        // Membuat data dummy menggunakan loop
        List<Post> popularPosts = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            popularPosts.add(new Post(titles[i], authors[i], "Isi konten disini", 100, 50, 10, R.drawable.header_account, "informasi"));
        }

        // Membuat adapter untuk RecyclerView post populer
        PopularPostAdapter adapter = new PopularPostAdapter(popularPosts);

        // Mengatur RecyclerView dengan LinearLayoutManager (vertikal)
        rvPopularPosts.setLayoutManager(new LinearLayoutManager(this)); // Linear layout default (vertikal)
        rvPopularPosts.setAdapter(adapter); // Menetapkan adapter pada RecyclerView
    }


}
