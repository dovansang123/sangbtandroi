package com.example.cuahangdienthoai;

import java.io.Serializable;

public class DienThoai implements Serializable {
    private int id;
    private String tendt;
    private String gioithieudt;
    private int giadt;
    private String nhasanxuat;
    private int soLuong;
    private int hinhanhdt;
    private String diaChiMua;
    private String emailDangNhap;

    public DienThoai() {
    }

    public DienThoai(int id, String tendt, String gioithieudt, int giadt, String nhasanxuat, int soLuong, int hinhanhdt, String diaChiMua, String emailDangNhap) {
        this.id = id;
        this.tendt= tendt;
        this.gioithieudt= gioithieudt;
        this.giadt = giadt;
        this.nhasanxuat = nhasanxuat;
        this.soLuong = soLuong;
        this.hinhanhdt = hinhanhdt;
        this.diaChiMua = diaChiMua;
        this.emailDangNhap = emailDangNhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTendt() {
        return tendt;
    }

    public void setTendt(String tendt) {
        this.tendt = tendt;
    }

    public String getGioithieudt() {
        return gioithieudt;
    }

    public void setGioithieudt(String gioithieudt) {
        this.gioithieudt = gioithieudt;
    }

    public int getGiadt() {
        return giadt;
    }

    public void setGiadt(int giadt) {
        this.giadt = giadt;
    }

    public String getNhasanxuat() {
        return nhasanxuat;
    }

    public void setNhasanxuat(String nhasanxuat) {
        this.nhasanxuat = nhasanxuat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getHinhanhdt() {
        return hinhanhdt;
    }

    public void setHinhanhdt(int hinhanhdt) {
        this.hinhanhdt = hinhanhdt;
    }

    public String getDiaChiMua() {
        return diaChiMua;
    }

    public void setDiaChiMua(String diaChiMua) {
        this.diaChiMua = diaChiMua;
    }

    public String getEmailDangNhap() {
        return emailDangNhap;
    }

    public void setEmailDangNhap(String emailDangNhap) {
        this.emailDangNhap = emailDangNhap;
    }
}
