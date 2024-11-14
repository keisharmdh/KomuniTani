package com.example.komunitani;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinearLayout postBar, postOptions;
    private RecyclerView recyclerView;
    private EditText postTitle, postDescription;
    private RadioGroup postTypeGroup;
    private Button btnPost;
    private Spinner spinner;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI Components
        initializeUI();

        // Initialize RecyclerView
        setupRecyclerView();

        // Initialize Spinner
        setupSpinner();

        // Set listeners
        setListeners();
    }

    private void initializeUI() {
        recyclerView = findViewById(R.id.recyclerView);
        postBar = findViewById(R.id.post_bar);
        postOptions = findViewById(R.id.post_actions);
        postTitle = findViewById(R.id.post_title);
        postDescription = findViewById(R.id.post_description);
        postTypeGroup = findViewById(R.id.post_type_group);
        btnPost = findViewById(R.id.btn_post);
        spinner = findViewById(R.id.spinner_topic);
    }

    private void setupRecyclerView() {
        List<Post> postList = new ArrayList<>();
        postList.add(new Post("Cara Menanam Padi", "Intan Permatasari", "Isi konten di sini", 0, 0, 0, R.drawable.header_account, "informasi"));
        postList.add(new Post("Cara Berkebun", "Agus Santoso", "Isi konten di sini", 0, 0, 0, R.drawable.header_account, "pertanyaan"));

        PostAdapter adapter = new PostAdapter(this, postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Add post button listener
        btnPost.setOnClickListener(v -> addNewPost(postList, adapter));
    }

    private void setupSpinner() {
        // Membuat adapter untuk spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.topics,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Menambahkan adapter ke spinner
        spinner.setAdapter(adapter);

        // Menambahkan listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Hanya update jika posisi > 0 (bukan placeholder)
                if (position > 0) {
                    // Perilaku default spinner sudah otomatis mengupdate item yang dipilih
                    String selectedTopic = parent.getItemAtPosition(position).toString();
                    // Jika perlu, simpan topik yang dipilih ke variabel atau database
                    // selectedTopic bisa digunakan di logika aplikasi Anda
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Tidak perlu aksi
            }
        });
    }


    private void setListeners() {
        postBar.setOnClickListener(this::expandPostOptions);

        ImageButton uploadMedia = findViewById(R.id.icon_media);
        uploadMedia.setOnClickListener(v -> openMediaPicker());
    }

    private void addNewPost(List<Post> postList, PostAdapter adapter) {
        postOptions.setVisibility(View.GONE);
        postBar.setVisibility(View.VISIBLE);

        int selectedPostTypeId = postTypeGroup.getCheckedRadioButtonId();
        RadioButton selectedPostType = findViewById(selectedPostTypeId);
        String postType = selectedPostType != null ? selectedPostType.getText().toString() : "informasi";
        String title = postTitle.getText().toString();
        String description = postDescription.getText().toString();

        Post newPost = new Post(title, "User", description, 0, 0, 0, R.drawable.header_account, postType);
        postList.add(newPost);
        adapter.notifyItemInserted(postList.size() - 1);

        recyclerView.scrollToPosition(postList.size() - 1);
    }

    private void expandPostOptions(View view) {
        if (postOptions.getVisibility() == View.GONE) {
            postOptions.setVisibility(View.VISIBLE);
            postBar.setVisibility(View.GONE);
        } else {
            postOptions.setVisibility(View.GONE);
            postBar.setVisibility(View.VISIBLE);
        }
    }

    private void openMediaPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/* video/*");
        startActivityForResult(Intent.createChooser(intent, "Select Media"), 1);
    }
}
