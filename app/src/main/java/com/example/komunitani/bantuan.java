//package com.example.komunitani;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//public class bantuan extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_bantuan);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//
//        // Tombol untuk menghubungi tim bantuan
//        findViewById(R.id.btn_hubungi_kami).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Membuka aplikasi email atau WhatsApp
//                Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                emailIntent.setType("message/rfc822");
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@komunitani.com"});
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Permintaan Bantuan KomuniTani");
//
//                try {
//                    startActivity(Intent.createChooser(emailIntent, "Kirim Email"));
//                } catch (android.content.ActivityNotFoundException ex) {
//                    // Jika tidak ada aplikasi email
//                    ex.printStackTrace();
//                }
//            }
//        });
//    }
//    }
//}