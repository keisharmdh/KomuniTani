package com.example.komunitani;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class daftar extends AppCompatActivity {

//    private EditText etUsername, etEmail, etPassword;
//    private Button btnDaftar;
//    private DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        etUsername = findViewById(R.id.etusername);
//        etEmail = findViewById(R.id.etemail);
//        etPassword = findViewById(R.id.etpassword);
//        btnDaftar = findViewById(R.id.btn_daftar);
//
//        database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://komunitani-id-default-rtdb.firebaseio.com/");




        Button btnDaftar = findViewById(R.id.btn_daftar);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
            }
        });


//        btnDaftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = etUsername.getText().toString();
//                String email = etEmail.getText().toString();
//                String password = etPassword.getText().toString();
//
//                if (username.isEmpty() || email.isEmpty() || password.isEmpty()){
//                    Toast.makeText(getApplicationContext(), "Ada Data Yang Masih Kosong", Toast.LENGTH_SHORT).show();
//                }else{
//                    database = FirebaseDatabase.getInstance().getReference("users");
//                    database.child(username).child("username").setValue(username);
//                    database.child(username).child("email").setValue(email);
//                    database.child(username).child("password").setValue(password);
//
//                    Toast.makeText(getApplicationContext(), "Berhasil",
//                            Toast.LENGTH_SHORT).show();
//                    Intent daftar = new Intent(getApplicationContext(), login.class);
//                    startActivity(daftar);
//                }
//            }
//        });

        TextView clickableText_login= findViewById(R.id.text_click_login);
        clickableText_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(daftar.this, login.class);
                startActivity(intent);
            }
        });
    }
}