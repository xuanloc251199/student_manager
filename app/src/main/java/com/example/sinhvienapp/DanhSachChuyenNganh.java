package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sinhvienapp.adapter.ChuyenNganhAdapter;
import com.example.sinhvienapp.dao.ChuyenNganhDao;
import com.example.sinhvienapp.dao.SinhVienDao;
import com.example.sinhvienapp.loginandregisteractivity.LoginActivity;
import com.example.sinhvienapp.model.ChuyenNganh;
import com.example.sinhvienapp.model.SinhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DanhSachChuyenNganh extends AppCompatActivity {

    FloatingActionButton fbadd;
    FloatingActionButton fab;
    FloatingActionButton fbHome;
    FloatingActionButton fabDangXuat;
    TextView tvanhien;
    EditText edtSearch;
    ImageView btnBack;

    ArrayList<ChuyenNganh> dschuyenn = new ArrayList<>();
    ArrayList<ChuyenNganh> timKiem = new ArrayList<>();

    ArrayList<SinhVien> svlist;
    static ArrayList<SinhVien> svlistDuocLoc;
    public static boolean xetList = true;

    ListView listView;
    ChuyenNganhAdapter chuyenNganhAdapter;

    ChuyenNganhDao chuyenNganhDao;
    SinhVienDao sinhVienDao;

    Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_chuyen_nganh);
        listView = findViewById(R.id.listviewLop);
        fbadd = findViewById(R.id.fbThemLop);
        tvanhien = findViewById(R.id.tvAnHien);
        fbHome = findViewById(R.id.fbHomeLop);
        fab = findViewById(R.id.fab1);
        fabDangXuat = findViewById(R.id.fbDangXuatLop);
        edtSearch = findViewById(R.id.edttennganh);
        fbAction();
        chuyenNganhDao = new ChuyenNganhDao(DanhSachChuyenNganh.this);

        dschuyenn = chuyenNganhDao.getAll();
        timKiem = chuyenNganhDao.getAll();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        fbadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhSachChuyenNganh.this, ThemNganh.class));
            }
        });

        chuyenNganhAdapter = new ChuyenNganhAdapter(DanhSachChuyenNganh.this, R.layout.dong_chuyennganh, dschuyenn);
        listView.setAdapter(chuyenNganhAdapter);

        if (dschuyenn.size() == 0) {
            listView.setVisibility(View.INVISIBLE);
            tvanhien.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            tvanhien.setVisibility(View.INVISIBLE);
        }



        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Search or Filter

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    chuyenNganhAdapter.resetData();

                } else {
                    chuyenNganhAdapter.getFilter().filter(s);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void fbAction() {
        fabDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhSachChuyenNganh.this, LoginActivity.class));

            }
        });
        fbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DanhSachChuyenNganh.this, ManagerActivity.class));
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    openMenu();
                } else {
                    closeMenu();
                }
            }
        });
    }

    private void openMenu() {
        isOpen = true;
        fbHome.animate().translationY(-getResources().getDimension(R.dimen.stan_60));
        fbadd.animate().translationY(-getResources().getDimension(R.dimen.stan_105));
        fabDangXuat.animate().translationY(-getResources().getDimension(R.dimen.stan_155));
    }

    private void closeMenu() {
        isOpen = false;
        fbHome.animate().translationY(0);
        fbadd.animate().translationY(0);
        fabDangXuat.animate().translationY(0);
    }
}