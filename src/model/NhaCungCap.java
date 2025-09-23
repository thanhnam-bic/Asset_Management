package model;

import java.util.Date;

public class NhaCungCap {
    private String nhaCungCap;
    private String tenLienHe;
    private String duongDan;
    private String maTaiSan;
    private Date taoLuc;
    private Date capNhatLuc;

    // Constructor với tất cả tham số
    public NhaCungCap(String nhaCungCap, String tenLienHe, String duongDan, String maTaiSan, Date taoLuc, Date capNhatLuc) {
        this.nhaCungCap = nhaCungCap;
        this.tenLienHe = tenLienHe;
        this.duongDan = duongDan;
        this.maTaiSan = maTaiSan;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
    }

    // Constructor mặc định
    public NhaCungCap() {
    }

    // Getter và Setter methods
    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getTenLienHe() {
        return tenLienHe;
    }

    public void setTenLienHe(String tenLienHe) {
        this.tenLienHe = tenLienHe;
    }

    public String getDuongDan() {
        return duongDan;
    }

    public void setDuongDan(String duongDan) {
        this.duongDan = duongDan;
    }

    public String getMaTaiSan() {
        return maTaiSan;
    }

    public void setMaTaiSan(String maTaiSan) {
        this.maTaiSan = maTaiSan;
    }

    public Date getTaoLuc() {
        return taoLuc;
    }

    public void setTaoLuc(Date taoLuc) {
        this.taoLuc = taoLuc;
    }

    public Date getCapNhatLuc() {
        return capNhatLuc;
    }

    public void setCapNhatLuc(Date capNhatLuc) {
        this.capNhatLuc = capNhatLuc;
    }
}