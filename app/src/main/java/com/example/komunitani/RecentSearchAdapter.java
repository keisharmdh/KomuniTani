package com.example.komunitani;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> {
    private List<String> searchList;

    public RecentSearchAdapter(List<String> searchList) {
        this.searchList = searchList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String searchItem = searchList.get(position);
        holder.tvSearchItem.setText(searchItem);

        // Click listener for recent search item
        holder.itemView.setOnClickListener(v -> {
            // Handle item click (e.g., perform a search with this term)
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSearchItem = itemView.findViewById(R.id.tv_search_item);
        }
    }
}
