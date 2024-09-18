package com.example.sinhvienapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sinhvienapp.database.DBHeplper;
import com.example.sinhvienapp.model.MonHoc;

import java.util.ArrayList;

public class MonHocDao {
    DBHeplper db;

    public MonHocDao(Context context) {
        db = new DBHeplper(context);
    }

    public ArrayList<MonHoc> getAll() {
        ArrayList<MonHoc> lsList = new ArrayList<>();
        SQLiteDatabase dtb = db.getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM MONHOC", null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String ma = cs.getString(0);
            String ten = cs.getString(1);
            MonHoc s = new MonHoc(ma, ten);
            lsList.add(s);
            cs.moveToNext();

        }
        cs.close();
        return lsList;
    }

    public MonHoc getMonHoc(String ma){
        for(MonHoc item : getAll()){
            if(item.getMaMH().equals(ma)){
                return item;
            }
        }
        return null;
    }

    public boolean insert(MonHoc monhoc) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maMH", monhoc.getMaMH());
        contentValues.put("tenmonhoc", monhoc.getTenmonhoc());
        long r = sqLiteDatabase.insert("MONHOC", null, contentValues);

        return r > 0;
    }
    public boolean update(MonHoc monhoc) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("maMH", monhoc.getMaMH());
        contentValues.put("tenmonhoc", monhoc.getTenmonhoc());
        ;

        long r = sqLiteDatabase.update("MONHOC", contentValues, "maMH=?", new String[]{monhoc.getMaMH()});

        return r > 0;
    }
    public boolean delete(String mamonhoc) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        int r = sqLiteDatabase.delete("MONHOC", "maMH=?", new String[]{mamonhoc});
        return r > 0;
    }
}
