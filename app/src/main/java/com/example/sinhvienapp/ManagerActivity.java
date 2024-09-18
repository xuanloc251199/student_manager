package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.sinhvienapp.dao.DaoTaiKhoan;
import com.example.sinhvienapp.loginandregisteractivity.ChangePasswordActivity;
import com.example.sinhvienapp.loginandregisteractivity.LoginActivity;
import com.example.sinhvienapp.model.TaikhoanMatKhau;

import java.util.ArrayList;

public class ManagerActivity extends AppCompatActivity  {
    ImageView btnLop , btnSinhVien, btnInFo, btnDangXuat, btnWebste,btnEvent;
    Animation animation;
    TextView txtWelcome,tv1,tv2,tv3,tv4,tv5,tv6;
    GridLayout gridLayout;
    Toolbar toolbar;
    DaoTaiKhoan tkDao;
    ImageView imageViewMenu;
    Intent intent,it2;
    ArrayList<TaikhoanMatKhau> listTK = new ArrayList<>();

    CardView cardView,cardMon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        btnLop = findViewById(R.id.btnLop);
        ArrayList<TaikhoanMatKhau> listTK = new ArrayList<>();
        gridLayout = findViewById(R.id.girdviewManager);
        btnSinhVien = findViewById(R.id.btnsinhvien);
        txtWelcome = findViewById(R.id.myDashboard);
        cardView= findViewById(R.id.quanlinganh);

        cardMon = findViewById(R.id.monhoc);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagerActivity.this.startActivity(new Intent(ManagerActivity.this, DanhSachChuyenNganh.class));
                ManagerActivity.this.overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });

        cardMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManagerActivity.this.startActivity(new Intent(ManagerActivity.this, DanhSachMonHoc.class));
                ManagerActivity.this.overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });

        textWelcome();

        tv1 = findViewById(R.id.tvclass);
        tv2 = findViewById(R.id.tvstudent);
        tv3 = findViewById(R.id.tvwebsite);
        tv4= findViewById(R.id.tvevent);
        tv5 = findViewById(R.id.tvinfo);
        tv6 = findViewById(R.id.tvdangxuat);

        btnInFo = findViewById(R.id.btninfo);
        btnWebste = findViewById(R.id.btnwebsite);
        btnEvent =findViewById(R.id.btnevent);

        btnDangXuat = findViewById(R.id.btnlogout);
        imageViewMenu=findViewById(R.id.imageViewMenu);





       imageViewMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ImageView view=(ImageView)v;
               final PopupMenu popupMenu=new PopupMenu(ManagerActivity.this,view);
               popupMenu.getMenuInflater().inflate(R.menu.drawer_menu,popupMenu.getMenu());
               popupMenu.show();
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                  @Override
                  public boolean onMenuItemClick(MenuItem item) {

                      switch (item.getItemId()){
                          case R.id.menuDoimatkhau:
                              intent=new Intent(ManagerActivity.this, ChangePasswordActivity.class);
                              startActivity(intent);
                              overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
                              break;
                          case R.id.menuDangXuat:
                               intent=new Intent(ManagerActivity.this,LoginActivity.class);
                              startActivity(intent);
                              overridePendingTransition(R.anim.scale, R.anim.ani_intenexit);
                              break;
                      }
                      return true;
                  }
              });

           }

       });


        animation = AnimationUtils.loadAnimation(ManagerActivity.this, R.anim.bottom_top);
        gridLayout.setAnimation(animation);
        btnInFo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, InFoAppActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, InFoAppActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, LoginActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnLop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, DanhSachLopActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        btnSinhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachLopActivity.xetList = false;
                startActivity(new Intent(ManagerActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DanhSachLopActivity.xetList = false;
                startActivity(new Intent(ManagerActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });




        btnWebste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://eaut.edu.vn/"));
                startActivity(myWebLink);
            }
        });
       tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myWebLink = new Intent(android.content.Intent.ACTION_VIEW);
                myWebLink.setData(Uri.parse("https://eaut.edu.vn/"));
                startActivity(myWebLink);
            }
        });
        btnEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, Calendar.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this, Calendar.class));
                overridePendingTransition(R.anim.ani_intent, R.anim.ani_intenexit);
            }
        });
    }
    public void textWelcome()
    {
        String username = getIntent().getStringExtra("USERNAME_KEY");
        txtWelcome.setText(username);
    }
    public void onRestart () {

        super.onRestart();
        textWelcome();
    }
    public void onResume () {

        super.onResume();
        textWelcome();
    }




}
