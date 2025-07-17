package config;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConnection {
    private static final String ROOT_URL = "jdbc:mysql://localhost:3306/";
    private static final String USER = "root";
    private static final String PASSWORD = ""; 
    private static final String DB_NAME = "AssetManagement";
    private static final String URL = ROOT_URL + DB_NAME;

    static {
        try (Connection rootConn = DriverManager.getConnection(ROOT_URL, USER, PASSWORD);
             Statement rootStmt = rootConn.createStatement()) {

            // Tạo database nếu chưa tồn tại
            rootStmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

            String createDanhMuc = "CREATE TABLE IF NOT EXISTS DanhMuc ("
                    + "danh_muc VARCHAR(150) PRIMARY KEY,"
                    + "loai VARCHAR(150),"
                    + "so_luong INT,"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME"
                    + ")";
            stmt.executeUpdate(createDanhMuc);

            // Thêm dữ liệu mẫu cho ViTri
            for (int i = 1; i <= 6; i++) {
                String viTri = "VT" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ViTri WHERE vi_tri = '" + viTri + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO ViTri VALUES ('" + viTri + "', " + (i * 2) +
                            ", 'Địa chỉ " + i + "', 'Thành phố " + i + "', NOW(), NOW())");
                }
            }

            // Thêm dữ liệu mẫu cho NhanVien
            for (int i = 1; i <= 6; i++) {
                String maNV = "NV" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhanVien WHERE ma_nhan_vien = '" + maNV + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO NhanVien VALUES ('" + maNV + "', 'Tên" + i + "', 'Họ" + i + "', 'user" + i + "', 'VT" + ((i % 6) + 1) + "', 'email" + i + "@example.com')");
                }
            }

            // Thêm dữ liệu mẫu cho DanhMuc
            for (int i = 1; i <= 6; i++) {
                String danhMuc = "DM" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM DanhMuc WHERE danh_muc = '" + danhMuc + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO DanhMuc VALUES ('" + danhMuc + "', 'Loại " + i + "', " + (i * 10) + ", NOW(), NOW())");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}