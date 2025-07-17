package model;

import java.util.Date;

public class NhanVien {
    private String maNhanVien;
    private String ten;
    private String ho;
    private String tenDangNhap;
    private String viTri;
    private String email;

    public NhanVien(String maNhanVien, String ten, String ho, String tenDangNhap, String viTri, String email) {
        this.maNhanVien = maNhanVien;
        this.ten = ten;
        this.ho = ho;
        this.tenDangNhap = tenDangNhap;
        this.viTri = viTri;
        this.email = email;
    }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }

    public String getTenDangNhap() { return tenDangNhap; }
    public void setTenDangNhap(String tenDangNhap) { this.tenDangNhap = tenDangNhap; }

    public String getViTri() { return viTri; }
    public void setViTri(String viTri) { this.viTri = viTri; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
