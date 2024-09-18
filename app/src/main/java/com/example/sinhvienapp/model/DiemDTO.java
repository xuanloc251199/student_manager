package com.example.sinhvienapp.model;

public class DiemDTO {
    private String maSV,maMH;
    private Float diem;

    public DiemDTO(String maSV, String maMH, Float diem) {
        this.maSV = maSV;
        this.maMH = maMH;
        this.diem = diem;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public Float getDiem() {
        return diem;
    }

    public void setDiem(Float diem) {
        this.diem = diem;
    }
}
