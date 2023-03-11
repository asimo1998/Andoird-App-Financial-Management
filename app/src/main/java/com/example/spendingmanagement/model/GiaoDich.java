package com.example.spendingmanagement.model;

import java.util.Date;

public class GiaoDich {
    private int maGD;
    private String tieuDe;
    private Date ngay;
    private double tien;
    private String moTa;
    private int maLoai;

    public GiaoDich(int maGD, String tieuDe, Date ngay, double tien, String moTa, int maLoai) {
        this.maGD = maGD;
        this.tieuDe = tieuDe;
        this.ngay = ngay;
        this.tien = tien;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }

    public GiaoDich(String tieuDe, Date ngay, double tien, String moTa, int maLoai) {
        this.tieuDe = tieuDe;
        this.ngay = ngay;
        this.tien = tien;
        this.moTa = moTa;
        this.maLoai = maLoai;
    }

    public int getMaGD() {
        return maGD;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public Date getNgay() {
        return ngay;
    }

    public double getTien() {
        return tien;
    }

    public String getMoTa() {
        return moTa;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaGD(int maGD) {
        this.maGD = maGD;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public void setTien(double tien) {
        this.tien = tien;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }
}
