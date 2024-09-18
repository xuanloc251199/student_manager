package com.example.sinhvienapp.model;

public class ChuyenNganh {
    private String maChuyenNganh;
    private String tenChuyenNganh;

    public ChuyenNganh(String maChuyenNganh, String tenChuyenNganh) {
        this.maChuyenNganh = maChuyenNganh;
        this.tenChuyenNganh = tenChuyenNganh;
    }

    public String getMaChuyenNganh() {
        return maChuyenNganh;
    }

    public void setMaChuyenNganh(String maChuyenNganh) {
        this.maChuyenNganh = maChuyenNganh;
    }

    public String getTenChuyenNganh() {
        return tenChuyenNganh;
    }

    public void setTenChuyenNganh(String tenChuyenNganh) {
        this.tenChuyenNganh = tenChuyenNganh;
    }

    @Override
    public String toString() {
        return getMaChuyenNganh();
    }
}
