package com.example.sinhvienapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sinhvienapp.database.DBHeplper;
import com.example.sinhvienapp.model.SinhVien;

import java.util.ArrayList;

public class SinhVienDao {
    DBHeplper dbHelper;

    public SinhVienDao(Context context) {
        dbHelper = new DBHeplper(context);
    }
    public ArrayList<SinhVien> getALL() {
        ArrayList<SinhVien> ds = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(" SELECT * FROM SINHVIEN", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            try {
                String ma = cursor.getString(0);
                String ten = cursor.getString(1);
                String email = cursor.getString(2);
                String hinh=cursor.getString(3);
                String maLop = cursor.getString(4);
                String machuyennganh = cursor.getString(5);
                SinhVien sinhVien = new SinhVien(ma, ten, email,hinh, maLop,machuyennganh);
                ds.add(sinhVien);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        cursor.close();
        return ds;
    }

    public boolean insert(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSv", s.getMaSv());
        contentValues.put("tenSV", s.getTenSv());
        contentValues.put("email",s.getEmail());
        contentValues.put("hinh",s.getHinh());
        contentValues.put("maLop", s.getMaLop());
        contentValues.put("maChuyenNganh", s.getMaChuyenNganh());
        long r = db.insert("SINHVIEN", null, contentValues);
        return r > 0;
    }

    public boolean update(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("maSv", s.getMaSv());
        contentValues.put("tenSV", s.getTenSv());
        contentValues.put("email",s.getEmail());
        contentValues.put("hinh",s.getHinh());
        contentValues.put("maLop", s.getMaLop());
        contentValues.put("maChuyenNganh", s.getMaChuyenNganh());
        long r = db.update("SINHVIEN", contentValues, "maSv=?", new String[]{s.getMaSv()});

        return r > 0;
    }

    public boolean delete(SinhVien s) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int r = db.delete("SINHVIEN", "maSv=?", new String[]{s.getMaSv()});
        return r > 0;
    }
}
