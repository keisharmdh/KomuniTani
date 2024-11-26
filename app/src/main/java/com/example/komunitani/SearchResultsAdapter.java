package com.example.komunitani;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.PostViewHolder> {

    private Context context;
    private List<Post> originalPostList;  // Data asli
    private List<Post> filteredPostList; // Hasil pencarian
    private static final String TAG = "SearchResultsAdapter";

    public SearchResultsAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.originalPostList = new ArrayList<>(postList); // Simpan data asli
        this.filteredPostList = postList; // Awalnya tampilkan semua data
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: Inflating layout for search result item");
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = filteredPostList.get(position);

        // Set data ke view holder
        holder.userName.setText(post.getUser() != null ? post.getUser().getName() : "Unknown User");
        holder.timestamp.setText(post.getCreatedAt() != null ? post.getCreatedAt() : "Unknown Date");
        holder.postTitle.setText(post.getTitle() != null ? post.getTitle() : "Untitled");
        holder.postContent.setText(post.getContent() != null ? post.getContent() : "No Content");

        // Set jumlah likes dan komentar
        int likeCount = (post.getLikes() != null && post.getLikes().size() > 0) ? post.getLikes().size() : 0;
        holder.likeCount.setText(String.valueOf(likeCount));

        int commentCount = (post.getComments() != null && post.getComments().size() > 0) ? post.getComments().size() : 0;
        holder.commentCount.setText(String.valueOf(commentCount));

        // Klik nama atau avatar untuk membuka profil
        holder.userName.setOnClickListener(v -> openProfile(post));
        holder.avatar_image.setOnClickListener(v -> openProfile(post));

        // Klik tombol share
        holder.share_icon.setOnClickListener(v -> sharePost(post));
    }

    @Override
    public int getItemCount() {
        return filteredPostList.size();
    }

    // Metode untuk filter hasil pencarian
    public void filterPosts(String query) {
        if (query == null || query.isEmpty()) {
            filteredPostList = new ArrayList<>(originalPostList); // Tampilkan semua data
        } else {
            filteredPostList = new ArrayList<>();
            for (Post post : originalPostList) {
                if (post.getTitle() != null && post.getTitle().toLowerCase().contains(query.toLowerCase())) {
                    filteredPostList.add(post);
                }
            }
        }
        notifyDataSetChanged(); // Refresh tampilan RecyclerView
    }

    // Method to update the posts in the adapter
    public void updatePosts(List<Post> newPosts) {
        this.filteredPostList = new ArrayList<>(newPosts); // Update the filtered list with new posts
        notifyDataSetChanged(); // Refresh the RecyclerView
    }

    private void sharePost(Post post) {
        String shareText = "Check out this post:\n" + post.getTitle() + "\n" + post.getContent();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        context.startActivity(Intent.createChooser(shareIntent, "Share post via"));
    }

    private void openProfile(Post post) {
        if (post.getUser () != null && post.getUser ().getId() != 0) { // Menggunakan 0 sebagai nilai default untuk ID
            Intent intent = new Intent(context, account_other.class);
            intent.putExtra("userId", post.getUser ().getId());
            context.startActivity(intent);
        }
    }

    // View Holder
    public static class PostViewHolder extends RecyclerView.ViewHolder {

        TextView userName, timestamp, postTitle, postContent, likeCount, commentCount;
        ImageView share_icon, avatar_image;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.user_name);
            timestamp = itemView.findViewById(R.id.post_timestamp);
            postTitle = itemView.findViewById(R.id.judul);
            postContent = itemView.findViewById(R.id.post_content);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            share_icon = itemView.findViewById(R.id.share_icon);
            avatar_image = itemView.findViewById(R.id.avatar_image);
        }
    }
}
