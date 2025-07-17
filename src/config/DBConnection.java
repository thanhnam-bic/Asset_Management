package config;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/AssetManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Tạo bảng nếu chưa tồn tại
            String createViTri = "CREATE TABLE IF NOT EXISTS ViTri ("
                    + "vi_tri VARCHAR(150) PRIMARY KEY,"
                    + "so_nguoi INT,"
                    + "dia_chi VARCHAR(150),"
                    + "thanh_pho VARCHAR(150),"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME"
                    + ")";

            stmt.executeUpdate(createViTri);

            // Tạo bảng NhanVien nếu chưa tồn tại
            String createNhanVien = "CREATE TABLE IF NOT EXISTS NhanVien ("
                    + "ma_nhan_vien VARCHAR(10) PRIMARY KEY,"
                    + "ten VARCHAR(150),"
                    + "ho VARCHAR(150),"
                    + "ten_dang_nhap VARCHAR(150),"
                    + "vi_tri VARCHAR(150),"
                    + "email VARCHAR(150),"
                    + "FOREIGN KEY (vi_tri) REFERENCES ViTri(vi_tri)"
                    + ")";
            stmt.executeUpdate(createNhanVien);
            
            // Thêm bảng DanhMuc
        String createDanhMuc = "CREATE TABLE IF NOT EXISTS DanhMuc ("
                + "danh_muc VARCHAR(150) PRIMARY KEY,"
                + "loai VARCHAR(150),"
                + "so_luong INT,"
                + "tao_luc DATETIME,"
                + "cap_nhat_luc DATETIME"
                + ")";
        stmt.executeUpdate(createDanhMuc);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
