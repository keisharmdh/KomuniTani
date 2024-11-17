package com.example.komunitani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;
import com.bumptech.glide.Glide;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private Context context;
    private List<Post> postList;

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
        Post post = postList.get(position);

        // Ambil userName menggunakan userId dan tampilkan
        String userName = getUserNameFromId(post.getUserId());  // Anda bisa membuat fungsi untuk mengambil nama pengguna
        holder.userName.setText(userName);

        holder.userLocation.setText(post.getUserLocation());
        holder.textTimestamp.setText(post.getTimestamp());
        holder.postContent.setText(post.getContent());
        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
        holder.commentCount.setText(String.valueOf(post.getCommentCount()));
        holder.shareCount.setText(String.valueOf(post.getShareCount()));

        Glide.with(context)
                .load(post.getAvatarUrl()) // URL avatar
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.avatarImage);

        Glide.with(context)
                .load(post.getImageUrl()) // URL gambar
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.error_image)
                .into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    private String getUserNameFromId(int userId) {
        // Implementasikan mekanisme untuk mengambil userName berdasarkan userId (misalnya API atau Database)
        // Contoh: lakukan panggilan API atau query database untuk mendapatkan nama pengguna
        return "Nama Pengguna"; // Ganti dengan data yang diterima
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userLocation, textTimestamp, postContent, likeCount, commentCount, shareCount;
        ImageView postImage, avatarImage;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            userLocation = itemView.findViewById(R.id.user_location);
            textTimestamp = itemView.findViewById(R.id.post_timestamp);
            postContent = itemView.findViewById(R.id.post_content);
            likeCount = itemView.findViewById(R.id.like_count);
            commentCount = itemView.findViewById(R.id.comment_count);
            shareCount = itemView.findViewById(R.id.share_count);
            postImage = itemView.findViewById(R.id.post_image);
            avatarImage = itemView.findViewById(R.id.avatar_image);
        }
    }
}
