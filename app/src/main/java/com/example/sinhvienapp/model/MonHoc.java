package com.example.sinhvienapp.model;

public class MonHoc {
    private String maMH,tenmonhoc;

    public MonHoc(String maMH, String tenmonhoc) {
        this.maMH = maMH;
        this.tenmonhoc = tenmonhoc;
    }

    @Override
    public String toString() {
        return getTenmonhoc();
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenmonhoc() {
        return tenmonhoc;
    }

    public void setTenmonhoc(String tenmonhoc) {
        this.tenmonhoc = tenmonhoc;
    }
}
