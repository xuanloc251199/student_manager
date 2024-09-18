package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class InFoAppActivity extends AppCompatActivity {

    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_app);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
