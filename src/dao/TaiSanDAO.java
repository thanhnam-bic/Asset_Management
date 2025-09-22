
package dao;

import config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.TaiSan;

public class TaiSanDAO {

    public List<TaiSan> getAllTaiSan() {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiSan";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TaiSan ts = new TaiSan(
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
                list.add(ts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertTaiSan(TaiSan ts) {
        String sql = "INSERT INTO TaiSan(ma_tai_san, ten_tai_san, so_serial, danh_muc, ma_nhan_vien, nha_san_xuat, nha_cung_cap, gia_mua, tinh_trang) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ts.getMaTaiSan());
            ps.setString(2, ts.getTenTaiSan());
            ps.setString(3, ts.getSoSerial());
            ps.setString(4, ts.getDanhMuc());
            ps.setString(5, ts.getMaNhanVien());
            ps.setString(6, ts.getNhaSanXuat());
            ps.setString(7, ts.getNhaCungCap());
            ps.setDouble(8, ts.getGiaMua());
            ps.setString(9, ts.getTinhTrang());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateTaiSan(TaiSan ts) {
        String sql = "UPDATE TaiSan SET ten_tai_san=?, so_serial=?, danh_muc=?, ma_nhan_vien=?, nha_san_xuat=?, nha_cung_cap=?, gia_mua=?, tinh_trang=? "
                   + "WHERE ma_tai_san=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteTaiSan(String maTaiSan) {
        String sql = "DELETE FROM TaiSan WHERE ma_tai_san=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTaiSan);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<TaiSan> searchTaiSan(String keyword) {
        List<TaiSan> list = new ArrayList<>();
        String sql = "SELECT * FROM TaiSan WHERE ma_tai_san LIKE ? OR ten_tai_san LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String kw = "%" + keyword + "%";
            ps.setString(1, kw);
            ps.setString(2, kw);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TaiSan ts = new TaiSan(
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
                list.add(ts);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
