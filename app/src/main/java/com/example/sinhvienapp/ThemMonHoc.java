package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sinhvienapp.dao.MonHocDao;
import com.example.sinhvienapp.model.MonHoc;

public class ThemMonHoc extends AppCompatActivity {

    LinearLayout linearLayout;
    Animation animation;
    EditText edtMNganh, etTenNganh;
    Button btnLuu, btnXemNganh;
    MonHocDao monHocDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_hoc);
        linearLayout = findViewById(R.id.linearLayoutThemLop);
        btnLuu = findViewById(R.id.btnthemnganh);
        edtMNganh = findViewById(R.id.edtMaLop);
        etTenNganh = findViewById(R.id.edtTenLop);
        btnXemNganh = findViewById(R.id.btnXemNganh);
        animation = AnimationUtils.loadAnimation(this, R.anim.uptodowndiagonal);
        linearLayout.setAnimation(animation);
        monHocDao = new MonHocDao(ThemMonHoc.this);
        btnXemNganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemMonHoc.this, DanhSachMonHoc.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ma = edtMNganh.getText().toString();
                String ten = etTenNganh.getText().toString();
                if (ma.equals("")) {
                    Toast.makeText(ThemMonHoc.this, "Mã ngành không được để trống", Toast.LENGTH_SHORT).show();
                }else if(ten.equals("")){
                    Toast.makeText(ThemMonHoc.this, "Tên không được để trống", Toast.LENGTH_SHORT).show();
                }else {
                    MonHoc monHoc = new MonHoc(ma, ten);
                    if (monHocDao.insert(monHoc)) {
                        Toast.makeText(ThemMonHoc.this, "Them thanh cong", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ThemMonHoc.this, DanhSachMonHoc.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ThemMonHoc.this, "Them that bai", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}