package dao;

import config.DBConnection;
import model.NhaCungCap;

import java.sql.*;
import java.util.*;

public class NhaCungCapDAO {
    
    public List<NhaCungCap> findAll() {
        List<NhaCungCap> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(new NhaCungCap(
                        rs.getString("nha_cung_cap"),
                        rs.getString("ten_lien_he"),
                        rs.getString("duong_dan"),
                        rs.getString("ma_tai_san"),
                        rs.getTimestamp("tao_luc"),
                        rs.getTimestamp("cap_nhat_luc")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<NhaCungCap> filter(String nhaCungCap, String tenLienHe, String duongDan, String maTaiSan) {
        List<NhaCungCap> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM NhaCungCap WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (!nhaCungCap.isEmpty()) {
            sql.append(" AND nha_cung_cap = ?");
            params.add(nhaCungCap);
        }
        if (!tenLienHe.isEmpty()) {
            sql.append(" AND ten_lien_he LIKE ?");
            params.add("%" + tenLienHe + "%");
        }
        if (!duongDan.isEmpty()) {
            sql.append(" AND duong_dan LIKE ?");
            params.add("%" + duongDan + "%");
        }
        if (!maTaiSan.isEmpty()) {
            sql.append(" AND ma_tai_san LIKE ?");
            params.add("%" + maTaiSan + "%");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new NhaCungCap(
                            rs.getString("nha_cung_cap"),
                            rs.getString("ten_lien_he"),
                            rs.getString("duong_dan"),
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

    public void insert(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap VALUES (?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ncc.getNhaCungCap());
            stmt.setString(2, ncc.getTenLienHe());
            stmt.setString(3, ncc.getDuongDan());
            stmt.setString(4, ncc.getMaTaiSan());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(NhaCungCap ncc) {
        String sql = "UPDATE NhaCungCap SET ten_lien_he = ?, duong_dan = ?, tai_san = ?, cap_nhat_luc = NOW() WHERE nha_cung_cap = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ncc.getTenLienHe());
            stmt.setString(2, ncc.getDuongDan());
            stmt.setString(3, ncc.getMaTaiSan());
            stmt.setString(4, ncc.getNhaCungCap());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(String id) {
        String sql = "DELETE FROM NhaCungCap WHERE nha_cung_cap = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Phương thức tìm kiếm theo ID
    public NhaCungCap findById(String id) {
        String sql = "SELECT * FROM NhaCungCap WHERE nha_cung_cap = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new NhaCungCap(
                            rs.getString("nha_cung_cap"),
                            rs.getString("ten_lien_he"),
                            rs.getString("duong_dan"),
                            rs.getString("ma_tai_san"),
                            rs.getTimestamp("tao_luc"),
                            rs.getTimestamp("cap_nhat_luc")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Phương thức kiểm tra tồn tại
    public boolean exists(String id) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE nha_cung_cap = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public Vector<String> loadAllMaTaiSan() {
        Vector<String> TaiSanList = new Vector<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ma_tai_san FROM TaiSan");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                TaiSanList.add(rs.getString("ma_tai_san"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return TaiSanList;
    }
}