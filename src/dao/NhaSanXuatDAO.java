package dao;

import config.DBConnection;
import model.NhaSanXuat;

import java.sql.*;
import java.util.*;

public class NhaSanXuatDAO {

    // Lấy tất cả
    public List<NhaSanXuat> findAll() {
        List<NhaSanXuat> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaSanXuat";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                NhaSanXuat nsx = mapResultSetToNhaSanXuat(rs);
                list.add(nsx);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lọc theo id hoặc tên (LIKE cho tên)
    public List<NhaSanXuat> filter(String nhaSanXuat, String tenNsx) {
        List<NhaSanXuat> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM NhaSanXuat WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (nhaSanXuat != null && !nhaSanXuat.isEmpty()) {
            sql.append(" AND nha_san_xuat = ?");
            params.add(nhaSanXuat);
        }
        if (tenNsx != null && !tenNsx.isEmpty()) {
            sql.append(" AND ten_nsx LIKE ?");
            params.add("%" + tenNsx + "%");
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhaSanXuat nsx = mapResultSetToNhaSanXuat(rs);
                    list.add(nsx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Thêm mới
    public void insert(NhaSanXuat nsx) {
        String sql = "INSERT INTO NhaSanXuat (nha_san_xuat, ten_nsx, quoc_gia, website, so_dien_thoai, email, dia_chi, tao_luc, cap_nhat_luc) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nsx.getNhaSanXuat());
            setStringOrNull(stmt, 2, nsx.getTenNsx());
            setStringOrNull(stmt, 3, nsx.getQuocGia());
            setStringOrNull(stmt, 4, nsx.getWebsite());
            setStringOrNull(stmt, 5, nsx.getSoDienThoai());
            setStringOrNull(stmt, 6, nsx.getEmail());
            setStringOrNull(stmt, 7, nsx.getDiaChi());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Cập nhật
    public void update(NhaSanXuat nsx) {
        String sql = "UPDATE NhaSanXuat SET ten_nsx = ?, quoc_gia = ?, website = ?, so_dien_thoai = ?, email = ?, dia_chi = ?, cap_nhat_luc = NOW() "
                   + "WHERE nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setStringOrNull(stmt, 1, nsx.getTenNsx());
            setStringOrNull(stmt, 2, nsx.getQuocGia());
            setStringOrNull(stmt, 3, nsx.getWebsite());
            setStringOrNull(stmt, 4, nsx.getSoDienThoai());
            setStringOrNull(stmt, 5, nsx.getEmail());
            setStringOrNull(stmt, 6, nsx.getDiaChi());
            stmt.setString(7, nsx.getNhaSanXuat());

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xoá
    public void delete(String id) {
        String sql = "DELETE FROM NhaSanXuat WHERE nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tìm theo ID
    public NhaSanXuat findById(String id) {
        String sql = "SELECT * FROM NhaSanXuat WHERE nha_san_xuat = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNhaSanXuat(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra tồn tại
    public boolean exists(String id) {
        String sql = "SELECT COUNT(*) FROM NhaSanXuat WHERE nha_san_xuat = ?";
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

    // Helper: ánh xạ ResultSet -> model
    private NhaSanXuat mapResultSetToNhaSanXuat(ResultSet rs) throws SQLException {
        String nha = rs.getString("nha_san_xuat");
        String ten = rs.getString("ten_nsx");
        String quocGia = rs.getString("quoc_gia");
        String website = rs.getString("website");
        String sdt = rs.getString("so_dien_thoai");
        String email = rs.getString("email");
        String diaChi = rs.getString("dia_chi");
        Timestamp tao = rs.getTimestamp("tao_luc");
        Timestamp cap = rs.getTimestamp("cap_nhat_luc");

        return new NhaSanXuat(nha, ten, quocGia, website, sdt, email, diaChi, tao, cap);
    }

    // Helper: setString hoặc setNull
    private void setStringOrNull(PreparedStatement stmt, int index, String value) throws SQLException {
        if (value == null) {
            stmt.setNull(index, Types.VARCHAR);
        } else {
            stmt.setString(index, value);
        }
    }
}
