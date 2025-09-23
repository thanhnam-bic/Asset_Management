package controller;

import dao.NhaSanXuatDAO;
import model.NhaSanXuat;
import view.NhaSanXuatPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class NhaSanXuatController {
    private NhaSanXuatPanel view;
    private NhaSanXuatDAO dao = new NhaSanXuatDAO();

    public NhaSanXuatController(NhaSanXuatPanel view) {
        this.view = view;
    }

    // Load dữ liệu vào bảng
    public void loadTableData() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (NhaSanXuat nsx : dao.findAll()) {
            model.addRow(new Object[]{
                    nsx.getNhaSanXuat(),   // Mã NSX (PK)
                    nsx.getTenNsx(),      // Tên NSX
                    nsx.getQuocGia(),     // Quốc gia
                    nsx.getWebsite(),     // Website
                    nsx.getSoDienThoai(), // SĐT
                    nsx.getEmail(),       // Email
                    nsx.getDiaChi(),      // Địa chỉ
                    nsx.getTaoLuc(),
                    nsx.getCapNhatLuc()
            });
        }
    }

    // Thêm NSX
    public void insertNhaSanXuat(String id, String ten, String quocGia,
                                 String website, String sdt, String email, String diaChi) {
        try {
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà sản xuất không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dao.exists(id.trim())) {
                view.showMessage("Mã nhà sản xuất đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.insert(new NhaSanXuat(id.trim(), ten, quocGia, website, sdt, email, diaChi, null, null));
            loadTableData();
            view.showMessage("Thêm nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            view.showMessage("Lỗi khi thêm nhà sản xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Cập nhật NSX
    public void updateNhaSanXuat(String id, String ten, String quocGia,
                                 String website, String sdt, String email, String diaChi) {
        try {
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà sản xuất không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!dao.exists(id.trim())) {
                view.showMessage("Không tìm thấy nhà sản xuất với mã: " + id, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.update(new NhaSanXuat(id.trim(), ten, quocGia, website, sdt, email, diaChi, null, null));
            loadTableData();
            view.showMessage("Cập nhật nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            view.showMessage("Lỗi khi cập nhật nhà sản xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Xoá NSX
    public void deleteNhaSanXuat(String id) {
        try {
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà sản xuất không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.delete(id.trim());
            loadTableData();
            view.showMessage("Xoá nhà sản xuất thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            view.showMessage("Lỗi khi xoá nhà sản xuất: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lọc NSX
    public void filterNhaSanXuat(String id, String ten) {
        try {
            DefaultTableModel model = view.getTableModel();
            model.setRowCount(0);

            List<NhaSanXuat> list = dao.filter(id, ten);
            for (NhaSanXuat nsx : list) {
                model.addRow(new Object[]{
                        nsx.getNhaSanXuat(),
                        nsx.getTenNsx(),
                        nsx.getQuocGia(),
                        nsx.getWebsite(),
                        nsx.getSoDienThoai(),
                        nsx.getEmail(),
                        nsx.getDiaChi(),
                        nsx.getTaoLuc(),
                        nsx.getCapNhatLuc()
                });
            }

            int count = model.getRowCount();
            if (count == 0) {
                view.showMessage("Không tìm thấy nhà sản xuất nào phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                view.showMessage("Tìm thấy " + count + " nhà sản xuất phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            view.showMessage("Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Reset filter
    public void resetFilter() {
        loadTableData();
    }

    // Tìm theo ID
    public NhaSanXuat findById(String id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            view.showMessage("Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Kiểm tra tồn tại
    public boolean checkExists(String id) {
        try {
            return dao.exists(id);
        } catch (Exception e) {
            view.showMessage("Lỗi khi kiểm tra dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
