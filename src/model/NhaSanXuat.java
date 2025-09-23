package model;

import java.sql.Timestamp;

public class NhaSanXuat {
    private String maNhaSanXuat;   // Mã NSX
    private String maTaiSan;       // 🔹 Khóa phụ, tham chiếu sang TaiSan
    private Timestamp taoLuc;
    private Timestamp capNhatLuc;

    public NhaSanXuat(String maNhaSanXuat, String maTaiSan, Timestamp taoLuc, Timestamp capNhatLuc) {
        this.maNhaSanXuat = maNhaSanXuat;
        this.maTaiSan = maTaiSan;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
    }

    public String getMaNhaSanXuat() {
        return maNhaSanXuat;
    }

    public void setMaNhaSanXuat(String maNhaSanXuat) {
        this.maNhaSanXuat = maNhaSanXuat;
    }

    public String getMaTaiSan() {
        return maTaiSan;
    }

    public void setMaTaiSan(String maTaiSan) {
        this.maTaiSan = maTaiSan;
    }

    public Timestamp getTaoLuc() {
        return taoLuc;
    }

    public void setTaoLuc(Timestamp taoLuc) {
        this.taoLuc = taoLuc;
    }

    public Timestamp getCapNhatLuc() {
        return capNhatLuc;
    }

    public void setCapNhatLuc(Timestamp capNhatLuc) {
        this.capNhatLuc = capNhatLuc;
    }
}
