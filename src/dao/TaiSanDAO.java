package dao;

import config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    /**
     * Filter linh hoạt giống pattern các DAO khác trong repo
     */
    public List<TaiSan> filter(String keyword, String danhMuc, String maNhanVien, String tinhTrang) {
        List<TaiSan> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder(
                "SELECT ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, "
                + "nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang FROM TaiSan WHERE 1=1");

        // xây where động
        if (keyword != null && !keyword.isBlank()) {
            sb.append(" AND (ma_tai_san LIKE ? OR ten_tai_san LIKE ? OR so_serial LIKE ?)");
        }
        if (danhMuc != null && !danhMuc.isBlank()) {
            sb.append(" AND danh_muc = ?");
        }
        if (maNhanVien != null && !maNhanVien.isBlank()) {
            sb.append(" AND ma_nhan_vien = ?");
        }
        if (tinhTrang != null && !tinhTrang.isBlank()) {
            sb.append(" AND tinh_trang = ?");
        }

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            int idx = 1;
            if (keyword != null && !keyword.isBlank()) {
                String kw = "%" + keyword.trim() + "%";
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
                ps.setString(idx++, kw);
            }
            if (danhMuc != null && !danhMuc.isBlank()) {
                ps.setString(idx++, danhMuc.trim());
            }
            if (maNhanVien != null && !maNhanVien.isBlank()) {
                ps.setString(idx++, maNhanVien.trim());
            }
            if (tinhTrang != null && !tinhTrang.isBlank()) {
                ps.setString(idx++, tinhTrang.trim());
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

    public List<String> getAllDanhMucIds() {
        List<String> ids = new ArrayList<>();
        String sql = "SELECT ma_danh_muc FROM DanhMuc ORDER BY ma_danh_muc";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
    }

    public List<String> getAllNhanVienIds() {
        List<String> ids = new ArrayList<>();
        String sql = "SELECT ma_nhan_vien FROM NhanVien ORDER BY ma_nhan_vien";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ids.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ids;
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
