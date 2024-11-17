// EditProfileActivity.java
package com.example.komunitani;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class edit_profile extends AppCompatActivity {

    private EditText etUsername, etBio, etLocation;
    private Button btnSave;
    private ImageView ivProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        // Initialize views
        etUsername = findViewById(R.id.username_edit_text);
        etBio = findViewById(R.id.bio_edit_text);
        btnSave = findViewById(R.id.save_button);
        ivProfilePicture = findViewById(R.id.profile_image_view);

        // Save button action
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String bio = etBio.getText().toString().trim();
                String location = etLocation.getText().toString().trim();

                if (username.isEmpty() || bio.isEmpty() || location.isEmpty()) {
                    Toast.makeText(edit_profile.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                } else {
                    // Save profile logic here
                    Toast.makeText(edit_profile.this, "Profil berhasil disimpan!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        // Logic to display current user data can go here

        // Add logic to save the edited profile
    }

