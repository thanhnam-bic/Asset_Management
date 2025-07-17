package dao;

import config.DBConnection;
import model.DanhMuc;

import java.sql.*;
import java.util.*;

public class DanhMucDAO {
    public List<DanhMuc> findAll() {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new DanhMuc(
                        rs.getString("danh_muc"),
                        rs.getString("loai"),
                        rs.getInt("so_luong"),
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<DanhMuc> filter(String keyword) {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc WHERE danh_muc = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new DanhMuc(
                        rs.getString("danh_muc"),
                        rs.getString("loai"),
                        rs.getInt("so_luong"),
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(DanhMuc dm) {
        String sql = "INSERT INTO DanhMuc VALUES (?, ?, ?, NOW(), NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dm.getDanhMuc());
            stmt.setString(2, dm.getLoai());
            stmt.setInt(3, dm.getSoLuong());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(DanhMuc dm) {
        String sql = "UPDATE DanhMuc SET loai = ?, so_luong = ?, cap_nhat_luc = NOW() WHERE danh_muc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dm.getLoai());
            stmt.setInt(2, dm.getSoLuong());
            stmt.setString(3, dm.getDanhMuc());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM DanhMuc WHERE danh_muc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}