package com.example.sinhvienapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sinhvienapp.database.DBHeplper;
import com.example.sinhvienapp.model.ChuyenNganh;

import java.util.ArrayList;

public class ChuyenNganhDao {
    DBHeplper db;

    public ChuyenNganhDao(Context context) {
        db = new DBHeplper(context);
    }

    public ArrayList<ChuyenNganh> getAll() {
        ArrayList<ChuyenNganh> lsList = new ArrayList<>();
        SQLiteDatabase dtb = db.getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM CHUYENNGANH", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String machuyennganh = cs.getString(0);
            String tenchuyennganh = cs.getString(1);
            ChuyenNganh s = new ChuyenNganh(machuyennganh, tenchuyennganh);
            lsList.add(s);
            cs.moveToNext();

        }
        cs.close();
        return lsList;
    }
    public ChuyenNganh getChuyenNganh(String ma){
        for(ChuyenNganh item : getAll()){
            if(item.getMaChuyenNganh().equals(ma)){
                return item;
            }
        }
        return null;
    }


    public boolean insert(ChuyenNganh chuyenNganh) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maChuyenNganh", chuyenNganh.getMaChuyenNganh());
        contentValues.put("tenChuyenNganh", chuyenNganh.getTenChuyenNganh());
        long r = sqLiteDatabase.insert("CHUYENNGANH", null, contentValues);
        return r > 0;
    }
    public boolean update(ChuyenNganh chuyenNganh) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maChuyenNganh", chuyenNganh.getMaChuyenNganh());
        contentValues.put("tenChuyenNganh", chuyenNganh.getTenChuyenNganh());
        ;

        long r = sqLiteDatabase.update("CHUYENNGANH", contentValues, "maChuyenNganh=?", new String[]{chuyenNganh.getMaChuyenNganh()});

        return r > 0;
    }
    public boolean delete(String machuyennganh) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete("SINHVIEN", "maChuyenNganh=?", new String[]{machuyennganh});
        int r = sqLiteDatabase.delete("CHUYENNGANH", "maChuyenNganh=?", new String[]{machuyennganh});
        return r > 0;
    }
}
