package model;

import java.util.Date;

public class DanhMuc {
    private String danhMuc;
    private String loai;
    private int soLuong;
    private Date taoLuc;
    private Date capNhatLuc;

    public DanhMuc(String danhMuc, String loai, int soLuong, Date taoLuc, Date capNhatLuc) {
        this.danhMuc = danhMuc;
        this.loai = loai;
        this.soLuong = soLuong;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
    }

    public String getDanhMuc() { return danhMuc; }
    public void setDanhMuc(String danhMuc) { this.danhMuc = danhMuc; }

    public String getLoai() { return loai; }
    public void setLoai(String loai) { this.loai = loai; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public Date getTaoLuc() { return taoLuc; }
    public void setTaoLuc(Date taoLuc) { this.taoLuc = taoLuc; }

    public Date getCapNhatLuc() { return capNhatLuc; }
    public void setCapNhatLuc(Date capNhatLuc) { this.capNhatLuc = capNhatLuc; }
}