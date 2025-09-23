package config;

import java.sql.*;

public class DBConnection {

    private static final String ROOT_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "AssetManagement";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String URL = ROOT_URL + DB_NAME;

    static {
        try (Connection rootConn = DriverManager.getConnection(ROOT_URL, USER, PASSWORD); Statement rootStmt = rootConn.createStatement()) {

            rootStmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement()) {

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
                    + "ma_tai_san VARCHAR(50),"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME,"
                    + "FOREIGN KEY (ma_tai_san) REFERENCES TaiSan(ma_tai_san)"
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
                    + "ma_tai_san VARCHAR(150),"
                    + "tao_luc DATETIME,"
                    + "cap_nhat_luc DATETIME"
                    + "FOREIGN KEY (ma_tai_san) REFERENCES TaiSan(ma_tai_san)"
                    + ")";
            stmt.executeUpdate(createNhaCungCap);

            String createTaiSan = "CREATE TABLE IF NOT EXISTS TaiSan ("
                    + "ma_tai_san VARCHAR(10) PRIMARY KEY,"
                    + "ten_tai_san VARCHAR(150),"
                    + "so_serial VARCHAR(150),"
                    + "danh_muc VARCHAR(150),"
                    + "ma_nhan_vien VARCHAR(10),"
                    + "nha_san_xuat VARCHAR(150),"
                    + "nha_cung_cap VARCHAR(150),"
                    + "gia_mua DECIMAL(15,2),"
                    + "tinh_trang VARCHAR(50),"
                    + "FOREIGN KEY (danh_muc) REFERENCES DanhMuc(danh_muc),"
                    + "FOREIGN KEY (ma_nhan_vien) REFERENCES NhanVien(ma_nhan_vien),"
                    + "FOREIGN KEY (nha_san_xuat) REFERENCES NhaSanXuat(nha_san_xuat),"
                    + "FOREIGN KEY (nha_cung_cap) REFERENCES NhaCungCap(nha_cung_cap)"
                    + ")";

            stmt.executeUpdate(createTaiSan);

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
                    stmt.executeUpdate("INSERT INTO ViTri VALUES ('" + viTri + "', " + (i * 2)
                            + ", 'Địa chỉ " + i + "', 'Thành phố " + i + "', NOW(), NOW())");
                }
            }
            // Thêm dữ liệu mẫu cho NhaSanXuat
            for (int i = 1; i <= 5; i++) {
                String nhaSanXuat = "NSX" + i;
                String maTaiSan = "TS" + i; 
                ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM NhaSanXuat WHERE nha_san_xuat = '" + nhaSanXuat + "'"
                );
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate(
                        "INSERT INTO NhaSanXuat (nha_san_xuat, ma_tai_san, tao_luc, cap_nhat_luc) " +
                        "VALUES ('" + nhaSanXuat + "', '" + maTaiSan + "', NOW(), NOW())"
                    );
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

            // Thêm dữ liệu mẫu cho NhaCungCap
            String[] tenNhaCungCap = {"Công ty A", "Công ty B", "Công ty C", "Công ty D", "Công ty E", "Công ty F"};
            String[] tenLienHe = {"Nguyễn Văn A", "Trần Thị B", "Lê Văn C", "Phạm Thị D", "Hoàng Văn E", "Vũ Thị F"};
            String[] duongDan = {"www.congtya.com", "www.congtyb.com", "www.congtyc.com", "www.congtyd.com", "www.congtye.com", "www.congtyf.com"};

            for (int i = 1; i <= 6; i++) {
                String nhaCungCap = "NCC" + i;
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM NhaCungCap WHERE nha_cung_cap = '" + nhaCungCap + "'");
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.executeUpdate("INSERT INTO NhaCungCap VALUES ('" + nhaCungCap + "', '" + tenLienHe[i - 1] + "', '" + duongDan[i - 1] + "', 'TS" + ((i % 6) + 1) + "', NOW(), NOW())");
                }
            }

            // Thêm user mẫu nếu chưa có
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM User WHERE username = 'admin'");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.executeUpdate("INSERT INTO User VALUES ('admin', 'admin123')");
            }

            // Thêm dữ liệu mẫu cho TaiSan
            String[][] sampleTaiSan = {
                {"TS1", "Máy tính xách tay", "SERIAL001", "DM1", "NV1", "NSX1", "NCC1", "15000000", "Đang sử dụng"},
                {"TS2", "Máy in laser", "SERIAL002", "DM2", "NV2", "NSX2", "NCC2", "4500000", "Bảo trì"},
                {"TS3", "Màn hình LCD", "SERIAL003", "DM1", "NV1", "NSX3", "NCC3", "3000000", "Hư hỏng"},
                {"TS4", "Bàn phím cơ", "SERIAL004", "DM2", "NV2", "NSX1", "NCC2", "1200000", "Đang sử dụng"},
                {"TS5", "Chuột không dây", "SERIAL005", "DM1", "NV1", "NSX2", "NCC1", "700000", "Thanh lý"},
                {"TS6", "Chuột không dây 1", "SERIAL006", "DM1", "NV2", "NSX2", "NCC2", "700000", "Thanh lý"},
                {"TS7", "Tai nghe", "SERIAL007", "DM2", "NV1", "NSX2", "NCC3", "10000", "Mới"},
                {"TS8", "PC", "SERIAL008", "DM3", "NV4", "NSX3", "NCC1", "1200000", "Mới"}
            };

            for (String[] ts : sampleTaiSan) {
                String ma = ts[0];
                ResultSet result = stmt.executeQuery("SELECT COUNT(*) FROM TaiSan WHERE ma_tai_san = '" + ma + "'");
                if (result.next() && result.getInt(1) == 0) {
                    String insert = "INSERT INTO TaiSan (ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang) "
                            + "VALUES ('" + ts[0] + "', '" + ts[1] + "', '" + ts[2] + "', '" + ts[3] + "', '" + ts[4] + "', "
                            + "'" + ts[5] + "', '" + ts[6] + "', " + ts[7] + ", '" + ts[8] + "')";
                    stmt.executeUpdate(insert);
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
