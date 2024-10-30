package com.example.komunitani;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
//
//    private List<Post> postList;
//
//    public PostAdapter(List<Post> postList) {
//        this.postList = postList;
//    }
//
//    @NonNull
//    @Override
//    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
//        return new PostViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
//        Post post = postList.get(position);
//        holder.postUser.setText(post.getUserName());
//        holder.postTitle.setText(post.getTitle());
//        holder.postImage.setImageResource(post.getImageRes());
//    }
//
//    @Override
//    public int getItemCount() {
//        return postList.size();
//    }
//
//    static class PostViewHolder extends RecyclerView.ViewHolder {
//        TextView postUser, postTitle;
//        ImageView postImage;
//
//        public PostViewHolder(@NonNull View itemView) {
//            super(itemView);
//            postUser = itemView.findViewById(R.id.postUser);
//            postTitle = itemView.findViewById(R.id.postTitle);
//            postImage = itemView.findViewById(R.id.postImage);
//        }
//    }
//}
