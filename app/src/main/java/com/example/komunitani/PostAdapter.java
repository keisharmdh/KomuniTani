package com.example.komunitani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.ApiService;
import com.example.api.RetrofitClient;
import com.example.model.CommentRequest;
import com.example.model.CommentResponse;
import com.example.model.DeleteResponse;
import com.example.model.LikeResponse;
import com.example.model.Post;
import com.example.model.Comment;


import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;
    private int userId;
    private static final String TAG = "PostAdapter";  // Tag untuk log

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;

        // Ambil userId dari SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(account.getSharedPrefsName(), Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("userId", -1); // Ambil userId

        // Tambahkan log untuk memeriksa nilai userId
        Log.d(TAG, "User  ID retrieved from SharedPreferences: " + this.userId);

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Inflating layout for post item");
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);
        Log.d(TAG, "onBindViewHolder: Binding data for post " + post.getId());

        // Set User Name
        if (post.getUser() != null && post.getUser().getName() != null) {
            holder.userName.setText(post.getUser().getName());
        } else {
            holder.userName.setText("Unknown User");
        }

        // Set Post Timestamp
        if (post.getCreatedAt() != null) {
            holder.timestamp.setText(post.getCreatedAt());
        } else {
            holder.timestamp.setText("Unknown Date");
        }

        // Set Post Title and Content
        holder.postTitle.setText(post.getTitle() != null ? post.getTitle() : "Untitled");
        holder.postContent.setText(post.getContent() != null ? post.getContent() : "No Content");

        // Set up click listeners
        holder.userName.setOnClickListener(v -> {
            // Open profile activity
            Intent intent = new Intent(context, account_other.class);
            intent.putExtra("userId", post.getUser().getId());
            context.startActivity(intent);
        });

        holder.avatar_image.setOnClickListener(v -> {
            // Open profile activity
            Intent intent = new Intent(context, account_other.class);
            intent.putExtra("userId", post.getUser().getId());
            context.startActivity(intent);
        });

        // Share Post
        holder.share_icon.setOnClickListener(v -> {
            sharePost(post);
        });

        // Set Like Count
        int likeCount = post.getLikes() != null ? post.getLikes().size() : 0;
        holder.likeCount.setText(String.valueOf(likeCount));

        // Set initial state of like icon
        boolean isLiked = post.isLiked(); // Replace with actual logic to check like status
        holder.likeIcon.setSelected(isLiked);

        // Handle like button click
        holder.likeIcon.setOnClickListener(v -> handleLikeClick(holder, post.getId()));

        // Set Comments Count
        int commentCount = post.getComments() != null ? post.getComments().size() : 0;
        holder.commentCount.setText(String.valueOf(commentCount));

//        // Set komentar
//        holder.commentIcon.setOnClickListener(v -> {
//            if (holder.commentSection.getVisibility() == View.GONE) {
//                holder.commentSection.setVisibility(View.VISIBLE);
//                loadComments(post.getId(), holder.commentRecyclerView);
//            } else {
//                holder.commentSection.setVisibility(View.GONE);
//            }
//        });

//        // Mengirim komentar
//        holder.sendButton.setOnClickListener(v -> {
//            String commentText = holder.commentInput.getText().toString().trim();
//            if (!commentText.isEmpty()) {
//                postComment(post.getId(), commentText, holder.commentInput, holder.commentRecyclerView);
//            } else {
//                Toast.makeText(context, "Komentar tidak boleh kosong!", Toast.LENGTH_SHORT).show();
//            }
//        });







//        // Handle comment click
//        holder.commentCount.setOnClickListener(v -> {
//            showCommentDialog(post.getId());
//        });

        // Ambil userId dari SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(account.getSharedPrefsName(), Context.MODE_PRIVATE);
        this.userId = sharedPreferences.getInt("userId", -1); // Ambil userId


        // Menampilkan menu jika pengguna adalah pemilik postingan
        if (post.getUser () != null && post.getUser ().getId() == userId) {
            holder.menu_icon.setVisibility(View.VISIBLE);
        } else {
            holder.menu_icon.setVisibility(View.GONE);
        }
        // Konfigurasi menu popup untuk menuIcon
        holder.menu_icon.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(context, holder.menu_icon); // Menggunakan holder.menu_icon
            popup.inflate(R.menu.post_menu); // Buat file menu di res/menu/post_menu.xml
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    // Handle edit action
                    // Panggil dialog edit
                    showEditDialog(post.getId(), post.getTitle(), post.getContent(), post.getTopic(), post.getPostType());
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    // Handle delete action
                    showDeleteConfirmationDialog(post.getId());
                    return true;
                }
                return false;
            });
            popup.show();
        });



    }

    private void showDeleteConfirmationDialog(int postId) {
        new android.app.AlertDialog.Builder(context)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus postingan ini?")
                .setPositiveButton("Hapus", (dialog, which) -> deletePost(postId))
                .setNegativeButton("Batal", null)
                .show();
    }

    private void deletePost(int postId) {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);

        // Panggil endpoint DELETE
        apiService.deletePost(postId).enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    // Hapus post dari daftar dan beri tahu adapter
                    postList.removeIf(post -> post.getId() == postId);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Gagal menghapus postingan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleLikeClick(PostViewHolder holder, int postId) {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);

        // Create request body
        HashMap<String, Integer> requestBody = new HashMap<>();
        requestBody.put("post_id", postId);

        // Call API
        apiService.likePost(postId, requestBody).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    // Update UI
                    boolean isLiked = response.body().isLiked();
                    holder.likeIcon.setSelected(isLiked); // Update state of like icon
                    holder.likeCount.setText(String.valueOf(response.body().getLikeCount()));
                    Toast.makeText(context, isLiked ? "Liked!" : "Unliked!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Failed to like post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void loadComments(int postId, RecyclerView commentRecyclerView) {
//        // Set up RecyclerView for comments
//        commentRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//
//        // Fetch comments from the API (implementasi ini tergantung pada API Anda)
//        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);
//
//        // Ganti dengan metode yang sesuai untuk mendapatkan komentar
//        apiService.getComments(postId).enqueue(new Callback<List<Comment>>() {
//            @Override
//            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<Comment> comments = response.body();
//                    CommentAdapter commentAdapter = new CommentAdapter(context, comments);
//                    commentRecyclerView.setAdapter(commentAdapter);
//                } else {
//                    Toast.makeText(context, "Failed to load comments", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Comment>> call, Throwable t) {
//                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void postComment(int postId, String commentText, EditText commentInput, RecyclerView commentRecyclerView) {
//        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);
//        CommentRequest request = new CommentRequest(commentText);
//
//        // Ambil token dari SharedPreferences atau tempat lain sesuai implementasi Anda
//        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");
//
//        apiService.postComment(postId, request, "Bearer " + token).enqueue(new Callback<CommentResponse>() {
//            @Override
//            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
//                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
//                    Toast.makeText(context, "Comment posted!", Toast.LENGTH_SHORT).show();
//                    commentInput.setText(""); // Clear input
//                    loadComments(postId, commentRecyclerView); // Reload comments
//                } else {
//                    Toast.makeText(context, "Failed to post comment", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CommentResponse> call, Throwable t) {
//                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }








    @Override
    public int getItemCount() {
        return postList.size();
    }

    private void sharePost(Post post) {
        String shareText = "Check out this post:\n" + post.getTitle() + "\n" + post.getContent();
        if (post.getImagePath() != null && !post.getImagePath().isEmpty()) {
            shareText += "\nImage: " + post.getImagePath();
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, "Share post via"));
    }

//    private void showCommentDialog(int postId) {
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
//        View bottomSheetView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_comment, null);
//        bottomSheetDialog.setContentView(bottomSheetView);
//
//        EditText commentInput = bottomSheetView.findViewById(R.id.comment_input);
//        Button postButton = bottomSheetView.findViewById(R.id.comment_post_button);
//
//        postButton.setOnClickListener(v -> {
//            String comment = commentInput.getText().toString();
//            if (!comment.isEmpty()) {
//                postComment(postId, comment);
//                bottomSheetDialog.dismiss();
//            } else {
//                Toast.makeText(context, "Please write a comment!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        bottomSheetDialog.show();
//    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView userName, timestamp, postTitle, postContent, likeCount, commentCount;
        ImageView share_icon, avatar_image, likeIcon, commentIcon, menu_icon ;
        View commentSection;
        EditText commentInput;
        Button sendButton;
        RecyclerView commentRecyclerView;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            timestamp = itemView.findViewById(R.id.post_timestamp);
            postTitle = itemView.findViewById(R.id.judul);
            postContent = itemView.findViewById(R.id.post_content);
            likeCount = itemView.findViewById(R.id.like_count);
            likeIcon = itemView.findViewById(R.id.like_icon);
            commentIcon = itemView.findViewById(R.id.comment_icon);
            commentRecyclerView = itemView.findViewById(R.id.comment_recycler_view);
//            commentSection = itemView.findViewById(R.id.comment_section);
//            sendButton = itemView.findViewById(R.id.send_button);
            commentInput = itemView.findViewById(R.id.comment_input);

            commentCount = itemView.findViewById(R.id.comment_count);
            share_icon = itemView.findViewById(R.id.share_icon);
            avatar_image = itemView.findViewById(R.id.avatar_image);
            menu_icon = itemView.findViewById(R.id.menu_icon);
        }
    }

    private void showEditDialog(int postId, String currentTitle, String currentContent, String currentTopic, String currentPostType) {
        // Inflate custom layout untuk dialog edit
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_post, null);

        // Set the background of the dialog to rounded corners
        dialogView.setBackgroundResource(R.drawable.rounded_dialog_background);

        // Inisialisasi field input pada dialog
        EditText editTitle = dialogView.findViewById(R.id.edit_title);
        EditText editContent = dialogView.findViewById(R.id.edit_content);
        Spinner editTopic = dialogView.findViewById(R.id.edit_topic);
        Spinner editPostType = dialogView.findViewById(R.id.edit_post_type);
        Button btnSubmit = dialogView.findViewById(R.id.btn_submit);
        Button btnCancel = dialogView.findViewById(R.id.btn_cancel);

        // Isi field dengan data saat ini
        editTitle.setText(currentTitle);
        editContent.setText(currentContent);

        // Inisialisasi adapter untuk Spinner
        ArrayAdapter<CharSequence> topicAdapter = ArrayAdapter.createFromResource(context,
                R.array.topics, android.R.layout.simple_spinner_item);
        topicAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTopic.setAdapter(topicAdapter);

        // Set selected topic based on currentTopic
        int topicPosition = topicAdapter.getPosition(currentTopic);
        editTopic.setSelection(topicPosition);

        ArrayAdapter<CharSequence> postTypeAdapter = ArrayAdapter.createFromResource(context,
                R.array.post_tye, android.R.layout.simple_spinner_item);
        postTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editPostType.setAdapter(postTypeAdapter);

        // Set selected post type based on currentPostType
        int postTypePosition = postTypeAdapter.getPosition(currentPostType);
        editPostType.setSelection(postTypePosition);

        // Membuat dialog dengan builder
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(dialogView)
                .setCancelable(false); // Untuk membuat dialog tidak bisa ditutup dengan menekan luar

        // Membuat dialog
        android.app.AlertDialog dialog = builder.create();

        // Menangani tombol update
        btnSubmit.setOnClickListener(v -> {
            // Ambil data baru dari input
            String newTitle = editTitle.getText().toString().trim();
            String newContent = editContent.getText().toString().trim();
            String newTopic = editTopic.getSelectedItem().toString();
            String postType = editPostType.getSelectedItem().toString();

            // Validasi input
            if (!newTitle.isEmpty() && !newContent.isEmpty() && !newTopic.isEmpty() && !postType.isEmpty()) {
                // Panggil updatePost
                updatePost(postId, newTitle, newContent, newTopic, postType);
                dialog.dismiss(); // Tutup dialog setelah update
            } else {
                Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            }
        });

        // Menangani tombol cancel
        btnCancel.setOnClickListener(v -> dialog.dismiss()); // Tutup dialog jika cancel

        dialog.show();
    }
    private void updatePost(int postId, String newTitle, String newContent, String newTopic, String postType) {
        ApiService apiService = RetrofitClient.getClient("https://komunitani-v2.vercel.app/api/api/").create(ApiService.class);

        // Buat request body
        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", newTitle);
        requestBody.put("content", newContent);
        requestBody.put("image", null); // Jika ada gambar, ubah sesuai kebutuhan
        requestBody.put("topic", newTopic);
        requestBody.put("post_type", postType);

        // Panggil endpoint PUT
        apiService.updatePost(postId, requestBody).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Post updatedPost = response.body();
                    Toast.makeText(context, "Post updated successfully!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Updated Post: " + updatedPost.getTitle());
                    // Lakukan sesuatu, misalnya refresh data
                } else {
                    Toast.makeText(context, "Failed to update post", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
