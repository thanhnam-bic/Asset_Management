package dao;

import model.NhanVien;
import config.DBConnection;

import java.sql.*;
import java.util.*;

public class NhanVienDAO {
    public List<NhanVien> findAll() {
        List<NhanVien> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM NhanVien")) {

            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten"),
                        rs.getString("ho"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("vi_tri"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhanVien> filter(String ma, String ten, String viTri, String ho, String email) {
        List<NhanVien> list = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE 1=1";
        if (!ma.isEmpty()) sql += " AND ma_nhan_vien = '" + ma + "'";
        if (!ten.isEmpty()) sql += " AND ten = '" + ten + "'";
        if (!ho.isEmpty()) sql += " AND ho = '" + ho + "'";
        if (!viTri.isEmpty()) sql += " AND vi_tri = '" + viTri + "'";
        if (!email.isEmpty()) sql += " AND email = '" + email + "'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new NhanVien(
                        rs.getString("ma_nhan_vien"),
                        rs.getString("ten"),
                        rs.getString("ho"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("vi_tri"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(NhanVien nv) {
        String sql = "INSERT INTO NhanVien VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getMaNhanVien());
            stmt.setString(2, nv.getTen());
            stmt.setString(3, nv.getHo());
            stmt.setString(4, nv.getTenDangNhap());
            stmt.setString(5, nv.getViTri());
            stmt.setString(6, nv.getEmail());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhanVien nv) {
        String sql = "UPDATE NhanVien SET ten = ?, ho = ?, ten_dang_nhap = ?, vi_tri = ?, email = ? WHERE ma_nhan_vien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nv.getTen());
            stmt.setString(2, nv.getHo());
            stmt.setString(3, nv.getTenDangNhap());
            stmt.setString(4, nv.getViTri());
            stmt.setString(5, nv.getEmail());
            stmt.setString(6, nv.getMaNhanVien());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE ma_nhan_vien = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maNV);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}