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
                int tmp = rs.getInt("so_luong");
                Integer taiSan = rs.wasNull() ? null : tmp;

                list.add(new NhaSanXuat(
                        rs.getString("nha_san_xuat"),
                        taiSan,
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhaSanXuat> filter(String nhaSanXuat, String taiSan) {
        List<NhaSanXuat> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM nhasanxuat WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nhaSanXuat != null && !nhaSanXuat.isEmpty()) {
            sql.append(" AND nha_san_xuat = ?");
            params.add(nhaSanXuat);
        }
        if (taiSan != null && !taiSan.isEmpty()) {
            try {
                int ts = Integer.parseInt(taiSan);
                sql.append(" AND so_luong = ?");
                params.add(ts);
            } catch (NumberFormatException e) {
                // skip
            }
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int tmp = rs.getInt("so_luong");
                    Integer tsVal = rs.wasNull() ? null : tmp;

                    list.add(new NhaSanXuat(
                            rs.getString("nha_san_xuat"),
                            tsVal,
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
        String sql = "INSERT INTO nhasanxuat (nha_san_xuat, so_luong, tao_luc, cap_nhat_luc) VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nsx.getNhaSanXuat());
            if (nsx.getTaiSan() == null) {
                stmt.setNull(2, Types.INTEGER);
            } else {
                stmt.setInt(2, nsx.getTaiSan());
            }
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhaSanXuat nsx) {
        String sql = "UPDATE nhasanxuat SET so_luong = ?, cap_nhat_luc = NOW() WHERE nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (nsx.getTaiSan() == null) {
                stmt.setNull(1, Types.INTEGER);
            } else {
                stmt.setInt(1, nsx.getTaiSan());
            }
            stmt.setString(2, nsx.getNhaSanXuat());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM nhasanxuat WHERE nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
