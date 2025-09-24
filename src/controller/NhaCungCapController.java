package controller;

import dao.NhaCungCapDAO;
import model.NhaCungCap;
import view.NhaCungCapPanel;
import java.util.Vector;

import javax.swing.*;

public class NhaCungCapController {
    private NhaCungCapPanel view;
    private NhaCungCapDAO dao = new NhaCungCapDAO();

    public NhaCungCapController(NhaCungCapPanel view) {
        this.view = view;
    }

    public void loadTableData() {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (NhaCungCap ncc : dao.findAll()) {
            model.addRow(new Object[]{
                    ncc.getNhaCungCap(), 
                    ncc.getTenLienHe(), 
                    ncc.getDuongDan(),
                    ncc.getTaoLuc(), 
                    ncc.getCapNhatLuc()
            });
        }
    }

    public void insertNhaCungCap(String id, String tenLienHe, String duongDan) {
        try {
            // Kiểm tra trường bắt buộc
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà cung cấp không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Kiểm tra trùng lặp
            if (dao.exists(id.trim())) {
                view.showMessage("Mã nhà cung cấp đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.insert(new NhaCungCap(id.trim(), tenLienHe.trim(), duongDan.trim(), null, null));
            loadTableData();
            view.showMessage("Thêm nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            view.showMessage("Lỗi khi thêm nhà cung cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateNhaCungCap(String id, String tenLienHe, String duongDan) {
        try {
            // Kiểm tra trường bắt buộc
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà cung cấp không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra tồn tại
            if (!dao.exists(id.trim())) {
                view.showMessage("Không tìm thấy nhà cung cấp với mã: " + id, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dao.update(new NhaCungCap(id.trim(), tenLienHe.trim(), duongDan.trim(), null, null));
            loadTableData();
            view.showMessage("Cập nhật nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (Exception e) {
            view.showMessage("Lỗi khi cập nhật nhà cung cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteNhaCungCap(String id) {
        try {
            // Kiểm tra trường bắt buộc
            if (id == null || id.trim().isEmpty()) {
                view.showMessage("Mã nhà cung cấp không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra nhà cung cấp có tồn tại hay không
            if (!dao.exists(id.trim())) {
                view.showMessage("Không tìm thấy nhà cung cấp với mã: " + id, "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra xem nhà cung cấp có đang được sử dụng trong bảng TaiSan hay không
            if (dao.isUsedInTaiSan(id.trim())) {
                // Lấy danh sách tài sản đang sử dụng nhà cung cấp này (tùy chọn)
                var taiSanList = dao.getTaiSanUsingNhaCungCap(id.trim());
                String taiSanNames = String.join(", ", taiSanList);
                
                String message = "Không thể xóa nhà cung cấp này vì đang có tài sản liên kết!\n" +
                               "Các tài sản đang sử dụng: " + taiSanNames;
                
                view.showMessage(message, "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Hiển thị hộp thoại xác nhận trước khi xóa
            int confirm = JOptionPane.showConfirmDialog(
                view, 
                "Bạn có chắc chắn muốn xóa nhà cung cấp '" + id + "' không?", 
                "Xác nhận xóa", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                dao.delete(id.trim());
                loadTableData();
                view.showMessage("Xóa nhà cung cấp thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            view.showMessage("Lỗi khi xóa nhà cung cấp: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }


    public void filterNhaCungCap(String nhaCungCap, String tenLienHe, String duongDan) {
        try {
            var model = view.getTableModel();
            model.setRowCount(0);
            
            for (NhaCungCap ncc : dao.filter(nhaCungCap, tenLienHe, duongDan)) {
                model.addRow(new Object[]{
                        ncc.getNhaCungCap(), 
                        ncc.getTenLienHe(), 
                        ncc.getDuongDan(),
                        ncc.getTaoLuc(), 
                        ncc.getCapNhatLuc()
                });
            }
            
            // Hiển thị số kết quả tìm được
            int count = model.getRowCount();
            if (count == 0) {
                view.showMessage("Không tìm thấy nhà cung cấp nào phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } else {
                view.showMessage("Tìm thấy " + count + " nhà cung cấp phù hợp!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            view.showMessage("Lỗi khi lọc dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Phương thức bổ sung để reset bộ lọc
    public void resetFilter() {
        loadTableData();
    }

    // Phương thức tìm theo ID
    public NhaCungCap findById(String id) {
        try {
            return dao.findById(id);
        } catch (Exception e) {
            view.showMessage("Lỗi khi tìm kiếm: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Phương thức kiểm tra tồn tại
    public boolean checkExists(String id) {
        try {
            return dao.exists(id);
        } catch (Exception e) {
            view.showMessage("Lỗi khi kiểm tra dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}