package com.example.komunitani;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class settings extends AppCompatActivity {

    private RecyclerView settingsRecyclerView;
    private SettingsAdapter settingsAdapter;
    private List<String> settingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsRecyclerView = findViewById(R.id.settings_recycler_view);

        settingsList = new ArrayList<>();
        settingsList.add("Account Information");
        settingsList.add("Security & Privacy");
        settingsList.add("Help");

        settingsAdapter = new SettingsAdapter(settingsList, this);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        settingsRecyclerView.setAdapter(settingsAdapter);
    }
}
