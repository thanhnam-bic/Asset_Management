package dao;

import config.DBConnection;
import model.ViTri;

import java.sql.*;
import java.util.*;

public class ViTriDAO {
    public List<ViTri> findAll() {
        List<ViTri> list = new ArrayList<>();
        String sql = "SELECT * FROM ViTri";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new ViTri(
                        rs.getString("vi_tri"),
                        rs.getInt("so_nguoi"),
                        rs.getString("dia_chi"),
                        rs.getString("thanh_pho"),
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ViTri> filter(String keyword) {
        List<ViTri> list = new ArrayList<>();
        String sql = "SELECT * FROM ViTri WHERE vi_tri = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new ViTri(
                        rs.getString("vi_tri"),
                        rs.getInt("so_nguoi"),
                        rs.getString("dia_chi"),
                        rs.getString("thanh_pho"),
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(ViTri v) {
        String sql = "INSERT INTO ViTri (vi_tri, so_nguoi, dia_chi, thanh_pho, tao_luc, cap_nhat_luc) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, v.getViTri());
            ps.setInt(2, v.getSoNguoi());
            ps.setString(3, v.getDiaChi());
            ps.setString(4, v.getThanhPho());
            ps.setTimestamp(5, new java.sql.Timestamp(v.getTaoLuc().getTime()));
            ps.setTimestamp(6, new java.sql.Timestamp(v.getCapNhatLuc().getTime()));

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String viTriId) {
        String sql = "DELETE FROM ViTri WHERE vi_tri = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, viTriId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
    public void update(ViTri viTri) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE ViTri SET so_nguoi = ?, dia_chi = ?, thanh_pho = ?, cap_nhat_luc = ? WHERE vi_tri = ?")) {

            stmt.setInt(1, viTri.getSoNguoi());
            stmt.setString(2, viTri.getDiaChi());
            stmt.setString(3, viTri.getThanhPho());
            stmt.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
            stmt.setString(5, viTri.getViTri());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ViTri> filter(String viTri, String thanhPho, String soNguoiStr) {
        List<ViTri> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM ViTri WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!viTri.isEmpty()) {
            sql.append(" AND vi_tri = ?");
            params.add(viTri);
        }
        if (!thanhPho.isEmpty()) {
            sql.append(" AND thanh_pho = ?");
            params.add(thanhPho);
        }
        if (!soNguoiStr.isEmpty()) {
            try {
                int soNguoi = Integer.parseInt(soNguoiStr);
                sql.append(" AND so_nguoi = ?");
                params.add(soNguoi);
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
                    ViTri v = new ViTri(
                            rs.getString("vi_tri"),
                            rs.getInt("so_nguoi"),
                            rs.getString("dia_chi"),
                            rs.getString("thanh_pho"),
                            rs.getTimestamp("tao_luc"),
                            rs.getTimestamp("cap_nhat_luc")
                    );
                    list.add(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}