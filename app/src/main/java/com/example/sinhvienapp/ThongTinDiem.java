package com.example.sinhvienapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinhvienapp.adapter.DiemAdapter;
import com.example.sinhvienapp.dao.ChuyenNganhDao;
import com.example.sinhvienapp.dao.DiemDAO;
import com.example.sinhvienapp.dao.LopDao;
import com.example.sinhvienapp.dao.MonHocDao;
import com.example.sinhvienapp.model.DiemDTO;
import com.example.sinhvienapp.model.MonHoc;
import com.example.sinhvienapp.model.SinhVien;

import java.util.List;

public class ThongTinDiem extends AppCompatActivity {
    private TextView tvMa,tvTen,tvEmail,tvtenLop,tvTenNganh;
    private Spinner spinnerMH;
    private EditText etDiem;
    private ListView listView;
    private Button btnThem;
    ImageView btnBack;
    List<DiemDTO> list;

    //GET DAO

    private LopDao lopDao;
    private ChuyenNganhDao chuyenNganhDao;
    private MonHocDao monHocDao;
    private DiemDAO diemDAO;


    //Adapter

    private DiemAdapter diemAdapter;

    SinhVien sinhVien;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_diem);
        Intent intent = getIntent();
        sinhVien = (SinhVien) intent.getExtras().getSerializable("SINHVIEN");
        AnhXa();

        btnBack = findViewById(R.id.btnBackTTDiem);
        btnBack.setOnClickListener(view -> {
            onBackPressed();
        });

        tvMa.setText("Mã sinh viên : "+ sinhVien.getMaSv());
        tvTen.setText("Tên sinh viên : "+ sinhVien.getTenSv());
        tvEmail.setText("Email : "+ sinhVien.getEmail());
        String tenlop = lopDao.getLop(sinhVien.getMaLop()).getTenLop();
        String tennganh = chuyenNganhDao.getChuyenNganh(sinhVien.getMaChuyenNganh()).getTenChuyenNganh();

        tvtenLop.setText("Tên lớp : "+ tenlop);
        tvTenNganh.setText("Tên ngành : "+ tennganh);

        List<MonHoc> lsList = monHocDao.getAll();
        final ArrayAdapter adapter = new ArrayAdapter(ThongTinDiem.this, android.R.layout.simple_spinner_item, lsList);
        spinnerMH.setAdapter(adapter);
        List<DiemDTO> list = diemDAO.getAll(sinhVien.getMaSv());
        diemAdapter = new DiemAdapter(ThongTinDiem.this,R.layout.dong_diem,list,sinhVien.getMaSv());
        listView.setAdapter(diemAdapter);
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etDiemS = etDiem.getText().toString();
                if(etDiemS.isEmpty()){
                    Toast.makeText(ThongTinDiem.this,"Không được bỏ trống thông tin",Toast.LENGTH_SHORT).show();
                }else{
                    Float diem = Float.valueOf(etDiem.getText().toString());
                    MonHoc monHoc = (MonHoc) spinnerMH.getSelectedItem();
                    if(diem>=0 || diem<=10){
                        if(diemDAO.insert(new DiemDTO(sinhVien.getMaSv(),monHoc.getMaMH(),diem))){
                            Toast.makeText(ThongTinDiem.this,"Thêm điểm thành công",Toast.LENGTH_SHORT).show();
                            diemAdapter.reset(diemDAO.getAll(sinhVien.getMaSv()));
                        }else{
                            Toast.makeText(ThongTinDiem.this,"Môn học này đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });


    }

    private void AnhXa() {
        lopDao = new LopDao(ThongTinDiem.this);
        chuyenNganhDao = new ChuyenNganhDao(ThongTinDiem.this);
        monHocDao = new MonHocDao(ThongTinDiem.this);
        diemDAO = new DiemDAO(ThongTinDiem.this);
        tvMa = findViewById(R.id.tvMaSinhVien);
        tvTen = findViewById(R.id.tvTenSV);
        tvEmail = findViewById(R.id.tvEmail);
        tvtenLop = findViewById(R.id.tvTenLop);
        tvTenNganh = findViewById(R.id.tvTenNganh);
        spinnerMH = findViewById(R.id.spMonHoc);
        etDiem = findViewById(R.id.etDiem);
        listView = findViewById(R.id.listDiem);
        btnThem = findViewById(R.id.btnThemDiem);
    }
}