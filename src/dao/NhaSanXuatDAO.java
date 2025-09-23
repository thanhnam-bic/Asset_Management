package dao;

import config.DBConnection;
import model.NhaSanXuat;

import java.sql.*;
import java.util.*;

public class NhaSanXuatDAO {

    public List<NhaSanXuat> findAll() {
        List<NhaSanXuat> list = new ArrayList<>();
        String sql = "SELECT * FROM nhasanxuat";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new NhaSanXuat(
                        rs.getString("ma_nha_san_xuat"),
                        rs.getString("ma_tai_san"),   // 🔹 String thay cho int
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhaSanXuat> filter(String maNhaSanXuat, String maTaiSan) {
        List<NhaSanXuat> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM nhasanxuat WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (maNhaSanXuat != null && !maNhaSanXuat.isEmpty()) {
            sql.append(" AND ma_nha_san_xuat = ?");
            params.add(maNhaSanXuat);
        }
        if (maTaiSan != null && !maTaiSan.isEmpty()) {
            sql.append(" AND ma_tai_san = ?");
            params.add(maTaiSan);
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new NhaSanXuat(
                            rs.getString("ma_nha_san_xuat"),
                            rs.getString("ma_tai_san"),
                            rs.getTimestamp("tao_luc"),
                            rs.getTimestamp("cap_nhat_luc")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(NhaSanXuat nsx) {
        String sql = "INSERT INTO nhasanxuat (ma_nha_san_xuat, ma_tai_san, tao_luc, cap_nhat_luc) VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nsx.getMaNhaSanXuat());
            stmt.setString(2, nsx.getMaTaiSan());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhaSanXuat nsx) {
        String sql = "UPDATE nhasanxuat SET ma_tai_san = ?, cap_nhat_luc = NOW() WHERE ma_nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nsx.getMaTaiSan());
            stmt.setString(2, nsx.getMaNhaSanXuat());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM nhasanxuat WHERE ma_nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
