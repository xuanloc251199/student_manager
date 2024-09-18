package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sinhvienapp.dao.LopDao;
import com.example.sinhvienapp.model.Lop;

import java.util.ArrayList;

public class ThemLopActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    Animation animation;
    EditText edtMalop, edtTenLop;
    Button btnLuu, btnXemLop;
    ImageView btnBack;
    LopDao lopDao;
    ArrayList<Lop> dsLop = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lop);
        linearLayout = findViewById(R.id.linearLayoutThemLop);
        btnLuu = findViewById(R.id.btnAddClass);
        edtMalop = findViewById(R.id.edtMaLop);
        edtTenLop = findViewById(R.id.edtTenLop);
        btnXemLop = findViewById(R.id.btnXemlop);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        linearLayout.setAnimation(animation);
        lopDao = new LopDao(ThemLopActivity.this);
        btnXemLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemLopActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String malop = edtMalop.getText().toString();
                String tenlop = edtTenLop.getText().toString();
                if (malop.equals("")) {
                    Toast.makeText(ThemLopActivity.this, "Mã lớp không được để trống", Toast.LENGTH_SHORT).show();
                }else if(tenlop.equals("")){
                    Toast.makeText(ThemLopActivity.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    Lop lop = new Lop(malop, tenlop);
                    if (lopDao.insert(lop)) {
                        Toast.makeText(ThemLopActivity.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemLopActivity.this, DanhSachLopActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ThemLopActivity.this, "Them that bai", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}
