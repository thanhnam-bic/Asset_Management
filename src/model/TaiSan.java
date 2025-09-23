package model;

public class TaiSan {

    private String maTaiSan;
    private String tenTaiSan;
    private String soSerial;
    private String danhMuc;
    private String maNhanVien;
    private String nhaSanXuat;
    private String nhaCungCap;
    private double giaMua;
    private String tinhTrang;

    public TaiSan() {
    }

    public TaiSan(String maTaiSan, String tenTaiSan, String soSerial, String danhMuc,
            String maNhanVien, String nhaSanXuat, String nhaCungCap,
            double giaMua, String tinhTrang) {
        this.maTaiSan = maTaiSan;
        this.tenTaiSan = tenTaiSan;
        this.soSerial = soSerial;
        this.danhMuc = danhMuc;
        this.maNhanVien = maNhanVien;
        this.nhaSanXuat = nhaSanXuat;
        this.nhaCungCap = nhaCungCap;
        this.giaMua = giaMua;
        this.tinhTrang = tinhTrang;
    }

    public String getMaTaiSan() {
        return maTaiSan;
    }

    public void setMaTaiSan(String maTaiSan) {
        this.maTaiSan = maTaiSan;
    }

    public String getTenTaiSan() {
        return tenTaiSan;
    }

    public void setTenTaiSan(String tenTaiSan) {
        this.tenTaiSan = tenTaiSan;
    }

    public String getSoSerial() {
        return soSerial;
    }

    public void setSoSerial(String soSerial) {
        this.soSerial = soSerial;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getNhaSanXuat() {
        return nhaSanXuat;
    }

    public void setNhaSanXuat(String nhaSanXuat) {
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public double getGiaMua() {
        return giaMua;
    }

    public void setGiaMua(double giaMua) {
        this.giaMua = giaMua;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }
}
