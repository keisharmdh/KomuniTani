package com.example.komunitani;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private List<String> topics;
    private boolean showAll = false; // Variabel untuk mengontrol jumlah data yang ditampilkan

    public TopicAdapter(List<String> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        holder.bind(topics.get(position));
    }

    @Override
    public int getItemCount() {
        // Tampilkan semua item jika showAll = true, atau hanya 6 item jika false
        return showAll ? topics.size() : Math.min(topics.size(), 6);
    }

    // Metode untuk mengubah status showAll dan memperbarui RecyclerView
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
        notifyDataSetChanged(); // Memperbarui data di RecyclerView
    }

    class TopicViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_topic_name);
        }

        public void bind(String topic) {
            textView.setText(topic);
        }
    }
}
