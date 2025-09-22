package config;

import java.sql.*;

public class DBConnection {
    private static final String ROOT_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "AssetManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "abc@123"; 
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
            
            String createNhaSanXuat = "CREATE TABLE IF NOT EXISTS NhaSanXuat ("
                    + "nha_san_xuat VARCHAR(150) PRIMARY KEY,"
                    + "so_luong INT,"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME"
                    + ")";
            stmt.executeUpdate(createNhaSanXuat);

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

            String createNhaCungCap = "CREATE TABLE IF NOT EXISTS NhaCungCap ("
                    + "nha_cung_cap VARCHAR(150) PRIMARY KEY,"
                    + "ten_lien_he VARCHAR(150),"
                    + "duong_dan VARCHAR(150),"
                    + "tai_san INT,"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME"
                    + ")";
            stmt.executeUpdate(createNhaCungCap);
            
            String createUser = "CREATE TABLE IF NOT EXISTS User ("
                    + "username VARCHAR(150) PRIMARY KEY,"
                    + "password VARCHAR(150)" // Có thể hash bằng SHA-256 nếu muốn bảo mật
                    + ")";
            stmt.executeUpdate(createUser);

            // Thêm dữ liệu mẫu cho ViTri
            for (int i = 1; i <= 6; i++) {
                String viTri = "VT" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ViTri WHERE vi_tri = '" + viTri + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO ViTri VALUES ('" + viTri + "', " + (i * 2) +
                            ", 'Địa chỉ " + i + "', 'Thành phố " + i + "', NOW(), NOW())");
                }
            }
            // Thêm dữ liệu mẫu cho NhaSanXuat
            for (int i = 1; i <= 5; i++) {
                String nhaSanXuat = "NSX" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhaSanXuat WHERE nha_san_xuat = '" + nhaSanXuat + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO NhaSanXuat VALUES ('" + nhaSanXuat + "', " + (i * 5) + ", NOW(), NOW())");}
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

            // Thêm dữ liệu mẫu cho NhaCungCap
            String[] tenNhaCungCap = {"Công ty A", "Công ty B", "Công ty C", "Công ty D", "Công ty E", "Công ty F"};
            String[] tenLienHe = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D", "Hoàng Văn E", "Vũ Thị F"};
            String[] duongDan = {"www.congtya.com", "www.congtyb.com", "www.congtyc.com", "www.congtyd.com", "www.congtye.com", "www.congtyf.com"};
            
            for (int i = 1; i <= 6; i++) {
                String nhaCungCap = "NCC" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhaCungCap WHERE nha_cung_cap = '" + nhaCungCap + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO NhaCungCap VALUES ('" + nhaCungCap + "', '" + tenLienHe[i-1] + "', '" + duongDan[i-1] + "', " + (i * 100) + ", NOW(), NOW())");
                }
            }
            
            // Thêm user mẫu nếu chưa có
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username = 'admin'");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO User VALUES ('admin', 'admin123')");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}