package com.example.komunitani;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private List<String> settingsList;
    private Context context;

    public SettingsAdapter(List<String> settingsList, Context context) {
        this.settingsList = settingsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_setting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.settingName.setText(settingsList.get(position));

        holder.itemView.setOnClickListener(v -> {
            switch (position) {
                case 0:
                    // Navigate to Account Information
                    context.startActivity(new Intent(context, AccountInformation.class));
                    break;
                case 1:
                    // Navigate to Security & Privacy Settings
                    context.startActivity(new Intent(context, SecurityPrivacy.class));
                    break;
                case 2:
                    // Navigate to Help
                    context.startActivity(new Intent(context, Help.class));
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return settingsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView settingName;

        public ViewHolder(View itemView) {
            super(itemView);
            settingName = itemView.findViewById(R.id.setting_name);
        }
    }
}

