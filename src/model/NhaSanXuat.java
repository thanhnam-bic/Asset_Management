package model;

import java.sql.Timestamp;

public class NhaSanXuat {
    private String nhaSanXuat;
    private Integer taiSan; // Integer để có thể null
    private Timestamp taoLuc;
    private Timestamp capNhatLuc;

    public NhaSanXuat(String nhaSanXuat, Integer taiSan, Timestamp taoLuc, Timestamp capNhatLuc) {
        this.nhaSanXuat = nhaSanXuat;
        this.taiSan = taiSan;
        this.taoLuc = taoLuc;
        this.capNhatLuc = capNhatLuc;
    }

    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) { this.nhaSanXuat = nhaSanXuat; }

    public Integer getTaiSan() { return taiSan; }
    public void setTaiSan(Integer taiSan) { this.taiSan = taiSan; }

    public Timestamp getTaoLuc() { return taoLuc; }
    public void setTaoLuc(Timestamp taoLuc) { this.taoLuc = taoLuc; }

    public Timestamp getCapNhatLuc() { return capNhatLuc; }
    public void setCapNhatLuc(Timestamp capNhatLuc) { this.capNhatLuc = capNhatLuc; }
}