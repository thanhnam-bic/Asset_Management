package model;

import java.sql.Timestamp;

public class NhaSanXuat {
    private String nhaSanXuat;      // Mã NSX (PK)
    private String tenNsx;          // Tên nhà sản xuất
    private String quocGia;         // Quốc gia
    private String website;         // Website (có thể null)
    private String soDienThoai;     // SĐT (có thể null)
    private String email;           // Email (có thể null)
    private String diaChi;          // Địa chỉ (có thể null)
    private Timestamp taoLuc;
    private Timestamp capNhatLuc;

    public NhaSanXuat(String nhaSanXuat, String tenNsx, String quocGia, String website,
                      String soDienThoai, String email, String diaChi,
                      Timestamp taoLuc, Timestamp capNhatLuc) {
        this.nhaSanXuat = nhaSanXuat;
        this.tenNsx = tenNsx;
        this.quocGia = quocGia;
        this.website = website;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
    }

    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }

    public String getTenNsx() { return tenNsx; }
    public void setTenNsx(String tenNsx) { this.tenNsx = tenNsx; }

    public String getQuocGia() { return quocGia; }
    public void setQuocGia(String quocGia) { this.quocGia = quocGia; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public Timestamp getTaoLuc() { return taoLuc; }
    public void setTaoLuc(Timestamp taoLuc) { this.taoLuc = taoLuc; }

    public Timestamp getCapNhatLuc() { return capNhatLuc; }
    public void setCapNhatLuc(Timestamp capNhatLuc) { this.capNhatLuc = capNhatLuc; }
}
