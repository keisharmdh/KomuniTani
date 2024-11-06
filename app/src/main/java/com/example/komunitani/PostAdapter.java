package com.example.komunitani; // Pastikan ini sesuai dengan package Anda

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.example.model.Post; // Import model Post

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context; // Konteks untuk mengakses resource
    private List<Post> postList; // Daftar post yang akan ditampilkan

    // Constructor untuk PostAdapter
    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout item_post.xml
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        // Ambil data post berdasarkan posisi
        Post post = postList.get(position);

        // Set data ke views
        holder.userName.setText(post.getUserName());
        holder.userLocation.setText(post.getUserLocation());
        holder.postContent.setText(post.getContent());
        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
        holder.commentCount.setText(String.valueOf(post.getCommentCount()));
        holder.shareCount.setText(String.valueOf(post.getShareCount()));
        holder.postImage.setImageResource(post.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return postList.size(); // Mengembalikan jumlah item di daftar
    }

    // ViewHolder untuk item Post
    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userLocation, postContent, likeCount, commentCount, shareCount;
        ImageView postImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inisialisasi views
            userName = itemView.findViewById(R.id.user_name);
            userLocation = itemView.findViewById(R.id.user_location);
            postContent = itemView.findViewById(R.id.post_content);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            shareCount = itemView.findViewById(R.id.share_count);
            postImage = itemView.findViewById(R.id.post_image);
        }
    }
}
