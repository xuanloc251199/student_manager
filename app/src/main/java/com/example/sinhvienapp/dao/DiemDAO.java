package com.example.sinhvienapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sinhvienapp.database.DBHeplper;
import com.example.sinhvienapp.model.DiemDTO;


import java.util.ArrayList;

public class DiemDAO {
    DBHeplper db;

    public DiemDAO(Context context) {
        db = new DBHeplper(context);
    }

    public ArrayList<DiemDTO> getAll(String maSV) {
        ArrayList<DiemDTO> lsList = new ArrayList<>();
        SQLiteDatabase dtb = db.getReadableDatabase();
        Cursor cs = dtb.rawQuery("SELECT * FROM DIEM WHERE maSV="+maSV, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            String masv = cs.getString(0);
            String mamh = cs.getString(1);
            Float diem = cs.getFloat(2);
            DiemDTO s = new DiemDTO(masv, mamh,diem);
            lsList.add(s);
            cs.moveToNext();

        }
        cs.close();
        return lsList;
    }




    public boolean insert(DiemDTO diem) {
        try{
            SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("maSV", diem.getMaSV());
            contentValues.put("maMH", diem.getMaMH());
            contentValues.put("diem", diem.getDiem());
            long r = sqLiteDatabase.insert("DIEM", null, contentValues);
            return r > 0;
        }catch (Exception E){
            return  false;
        }
    }
    public boolean update(DiemDTO diem) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("diem", diem.getDiem());

        long r = sqLiteDatabase.update("DIEM", contentValues, "maSv=? AND maMH=?", new String[]{diem.getMaSV(),diem.getMaMH()});

        return r > 0;
    }
    public boolean delete(DiemDTO diem) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        int r = sqLiteDatabase.delete("DIEM", "maSv=? AND maMH=?", new String[]{diem.getMaSV(),diem.getMaMH()});
        return r > 0;
    }


}
