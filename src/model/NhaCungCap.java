package model;

import java.util.Date;

public class NhaCungCap {
    private String nhaCungCap;
    private String tenLienHe;
    private String duongDan;
    private int taiSan;
    private Date taoLuc;
    private Date capNhatLuc;

    // Constructor với tất cả tham số
    public NhaCungCap(String nhaCungCap, String tenLienHe, String duongDan, int taiSan, Date taoLuc, Date capNhatLuc) {
        this.nhaCungCap = nhaCungCap;
        this.tenLienHe = tenLienHe;
        this.duongDan = duongDan;
        this.taiSan = taiSan;
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

    public int getTaiSan() {
        return taiSan;
    }

    public void setTaiSan(int taiSan) {
        this.taiSan = taiSan;
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