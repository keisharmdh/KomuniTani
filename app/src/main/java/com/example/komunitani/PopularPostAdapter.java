package com.example.komunitani;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Post;
import java.util.List;

public class PopularPostAdapter extends RecyclerView.Adapter<PopularPostAdapter.PopularPostViewHolder> {

    private List<Post> posts;

    public PopularPostAdapter(List<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public PopularPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PopularPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularPostViewHolder holder, int position) {
        Post post = posts.get(position);

        // Set data dari objek Post ke View
        holder.tvUserName.setText(post.getUserName());
        holder.tvUserLocation.setText(post.getUserLocation());
        holder.tvContent.setText(post.getContent());
        holder.tvLikes.setText("Likes: " + post.getLikeCount());
        holder.tvComments.setText("Comments: " + post.getCommentCount());
        holder.tvShares.setText("Shares: " + post.getShareCount());
        holder.tvPostType.setText(post.getPostType().equals("informasi") ? "Informasi" : "Pertanyaan");

        // Jika menggunakan gambar dari drawable, set image resource
        holder.ivPostImage.setImageResource(post.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PopularPostViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvUserLocation, tvContent, tvLikes, tvComments, tvShares, tvPostType;
        ImageView ivPostImage;

        public PopularPostViewHolder(@NonNull View itemView) {
            super(itemView);
            // Hubungkan dengan elemen UI di item_post.xml
            tvUserName = itemView.findViewById(R.id.user_name);
            tvUserLocation = itemView.findViewById(R.id.user_location);
            tvContent = itemView.findViewById(R.id.post_content);
            tvLikes = itemView.findViewById(R.id.like_count);
            tvComments = itemView.findViewById(R.id.comment_count);
            tvShares = itemView.findViewById(R.id.share_count);
            tvPostType = itemView.findViewById(R.id.post_type); // Menampilkan jenis postingan
            ivPostImage = itemView.findViewById(R.id.post_image); // Menampilkan gambar
        }
    }
}
