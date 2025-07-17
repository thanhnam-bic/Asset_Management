package model;

import java.util.Date;

public class ViTri {
    private String viTri;
    private int soNguoi;
    private String diaChi;
    private String thanhPho;
    private Date taoLuc;
    private Date capNhatLuc;
    
    public ViTri(String viTri, int soNguoi, String diaChi, String thanhPho, Date taoLuc, Date capNhapLuc) {
        this.viTri = viTri;
        this.soNguoi = soNguoi;
        this.diaChi = diaChi;
        this.thanhPho = thanhPho;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhapLuc;
    }

    // Constructors, Getters, Setters
    public String getViTri() { return viTri; }
    public void setViTri(String viTri) { this.viTri = viTri; }
    public int getSoNguoi() { return soNguoi; }
    public void setSoNguoi(int soNguoi) { this.soNguoi = soNguoi; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getThanhPho() { return thanhPho; }
    public void setThanhPho(String thanhPho) { this.thanhPho = thanhPho; }
    public Date getTaoLuc() { return taoLuc; }
    public void setTaoLuc(Date taoLuc) { this.taoLuc = taoLuc; }
    public Date getCapNhatLuc() { return capNhatLuc; }
    public void setCapNhatLuc(Date capNhatLuc) { this.capNhatLuc = capNhatLuc; }
}
