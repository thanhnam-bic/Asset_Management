package dao;

import config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import model.TaiSan;

public class TaiSanDAO {

    public List<TaiSan> findAll() {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, "
                + "nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang FROM TaiSan";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<TaiSan> filter(String maTaiSan, String maNhanVien, String danhMuc, String tinhTrang) {
        List<TaiSan> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, "
                + "nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang "
                + "FROM TaiSan WHERE 1=1"
        );

        if (maTaiSan != null && !maTaiSan.isBlank()) {
            sql.append(" AND ma_tai_san LIKE ?");
        }
        if (maNhanVien != null && !maNhanVien.isBlank()) {
            sql.append(" AND ma_nhan_vien = ?");
        }
        if (danhMuc != null && !danhMuc.isBlank()) {
            sql.append(" AND danh_muc = ?");
        }
        if (tinhTrang != null && !tinhTrang.isBlank()) {
            sql.append(" AND tinh_trang = ?");
        }
        sql.append(" ORDER BY ma_tai_san");

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int i = 1;
            if (maTaiSan != null && !maTaiSan.isBlank()) {
                ps.setString(i++, "%" + maTaiSan.trim() + "%");
            }
            if (maNhanVien != null && !maNhanVien.isBlank()) {
                ps.setString(i++, maNhanVien.trim());
            }
            if (danhMuc != null && !danhMuc.isBlank()) {
                ps.setString(i++, danhMuc.trim());
            }

            if (tinhTrang != null && !tinhTrang.isBlank()) {
                ps.setString(i++, tinhTrang.trim());
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(TaiSan ts) {
        String sql = "INSERT INTO TaiSan(ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, "
                + "nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            bind(ps, ts);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(TaiSan ts) {
        String sql = "UPDATE TaiSan SET ten_tai_san=?, so_serial=?, danh_muc=?, ma_nhan_vien=?, "
                + "nha_san_xuat=?, nha_cung_cap=?, gia_mua=?, tinh_trang=? WHERE ma_tai_san=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ts.getTenTaiSan());
            ps.setString(2, ts.getSoSerial());
            ps.setString(3, ts.getDanhMuc());
            ps.setString(4, ts.getMaNhanVien());
            ps.setString(5, ts.getNhaSanXuat());
            ps.setString(6, ts.getNhaCungCap());
            ps.setDouble(7, ts.getGiaMua());
            ps.setString(8, ts.getTinhTrang());
            ps.setString(9, ts.getMaTaiSan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteById(String maTaiSan) {
        String sql = "DELETE FROM TaiSan WHERE ma_tai_san=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maTaiSan);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Vector<String> loadAllDanhMucCodes() {
        Vector<String> v = new Vector<>();
        String sql = "SELECT ma_danh_muc FROM DanhMuc ORDER BY ma_danh_muc";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                v.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public Vector<String> loadAllNhanVienCodes() {
        Vector<String> v = new Vector<>();
        String sql = "SELECT ma_nhan_vien FROM NhanVien ORDER BY ma_nhan_vien";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                v.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    private TaiSan mapRow(ResultSet rs) throws SQLException {
        return new TaiSan(
                rs.getString("ma_tai_san"),
                rs.getString("ten_tai_san"),
                rs.getString("so_serial"),
                rs.getString("danh_muc"),
                rs.getString("ma_nhan_vien"),
                rs.getString("nha_san_xuat"),
                rs.getString("nha_cung_cap"),
                rs.getDouble("gia_mua"),
                rs.getString("tinh_trang")
        );
    }

    private void bind(PreparedStatement ps, TaiSan ts) throws SQLException {
        ps.setString(1, ts.getMaTaiSan());
        ps.setString(2, ts.getTenTaiSan());
        ps.setString(3, ts.getSoSerial());
        ps.setString(4, ts.getDanhMuc());
        ps.setString(5, ts.getMaNhanVien());
        ps.setString(6, ts.getNhaSanXuat());
        ps.setString(7, ts.getNhaCungCap());
        ps.setDouble(8, ts.getGiaMua());
        ps.setString(9, ts.getTinhTrang());
    }
}
