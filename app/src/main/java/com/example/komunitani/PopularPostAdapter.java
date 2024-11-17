//package com.example.komunitani;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.example.model.Post;
//
//import java.util.List;
//
//public class PopularPostAdapter extends RecyclerView.Adapter<PopularPostAdapter.PopularPostViewHolder> {
//
//    private Context context;
//    private List<Post> postList;
//
//    // Constructor untuk PopularPostAdapter
//    public PopularPostAdapter(Context context, List<Post> postList) {
//        this.context = context;
//        this.postList = postList;
//    }
//
//    @NonNull
//    @Override
//    public PopularPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        // Inflate layout item_post.xml
//        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
//        return new PopularPostViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PopularPostViewHolder holder, int position) {
//        Post post = postList.get(position);
//
//        // Ambil userName menggunakan userId dan tampilkan
//        String userName = getUserNameFromId(post.getUserId());  // Fungsi untuk mengambil nama pengguna
//        holder.userName.setText(userName);
//
//        holder.userLocation.setText(post.getUserLocation());
//        holder.textTimestamp.setText(post.getTimestamp());
//        holder.postContent.setText(post.getContent());
//        holder.likeCount.setText(String.valueOf(post.getLikeCount()));
//        holder.commentCount.setText(String.valueOf(post.getCommentCount()));
//        holder.shareCount.setText(String.valueOf(post.getShareCount()));
//
//        // Memuat gambar menggunakan Glide
//        Glide.with(context)
//                .load(post.getAvatarUrl()) // URL avatar
//                .placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.error_image)
//                .into(holder.avatarImage);
//
//        Glide.with(context)
//                .load(post.getImageUrl()) // URL gambar postingan
//                .placeholder(R.drawable.placeholder_image)
//                .error(R.drawable.error_image)
//                .into(holder.postImage);
//    }
//
//    @Override
//    public int getItemCount() {
//        return postList.size();
//    }
//
//    private String getUserNameFromId(int userId) {
//        // Implementasikan mekanisme untuk mendapatkan nama pengguna berdasarkan userId
//        // Misalnya dengan query database atau API
//        return "Nama Pengguna"; // Ganti dengan data yang diterima
//    }
//
//    public static class PopularPostViewHolder extends RecyclerView.ViewHolder {
//        TextView userName, userLocation, textTimestamp, postContent, likeCount, commentCount, shareCount;
//        ImageView postImage, avatarImage;
//
//        public PopularPostViewHolder(@NonNull View itemView) {
//            super(itemView);
//            userName = itemView.findViewById(R.id.user_name);
//            userLocation = itemView.findViewById(R.id.user_location);
//            textTimestamp = itemView.findViewById(R.id.post_timestamp);
//            postContent = itemView.findViewById(R.id.post_content);
//            likeCount = itemView.findViewById(R.id.like_count);
//            commentCount = itemView.findViewById(R.id.comment_count);
//            shareCount = itemView.findViewById(R.id.share_count);
//            postImage = itemView.findViewById(R.id.post_image);
//            avatarImage = itemView.findViewById(R.id.avatar_image);
//        }
//    }
//}
