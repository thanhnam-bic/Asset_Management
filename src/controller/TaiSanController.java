package controller;

import dao.TaiSanDAO;
import model.TaiSan;
import view.TaiSanPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TaiSanController {

    private final TaiSanPanel view;
    private final TaiSanDAO dao = new TaiSanDAO();

    public TaiSanController(TaiSanPanel view) {
        if (view == null) {
            throw new IllegalArgumentException("TaiSanController: view is null");
        }
        this.view = view;
    }

    public void loadTableData() {
        List<TaiSan> list = dao.findAll();
        fillTable(list);
    }

    public void filter() {
        String keyword = view.getTfKeyword().getText().trim();
        String danhMuc = (String) view.getCbDanhMuc().getSelectedItem();
        String maNV = (String) view.getCbMaNhanVien().getSelectedItem();
        String tinhTrang = (String) view.getCbTinhTrang().getSelectedItem();

        if ("<Tất cả>".equalsIgnoreCase(danhMuc)) {
            danhMuc = null;
        }
        if ("<Tất cả>".equalsIgnoreCase(maNV)) {
            maNV = null;
        }
        if ("<Tất cả>".equalsIgnoreCase(tinhTrang)) {
            tinhTrang = null;
        }

        List<TaiSan> list = dao.filter(keyword, danhMuc, maNV, tinhTrang);
        fillTable(list);
    }

    public void insertOrUpdate(boolean isUpdate) {
        try {
            TaiSan ts = readForm();
            boolean ok = isUpdate ? dao.update(ts) : dao.insert(ts);
            if (ok) {
                JOptionPane.showMessageDialog(view, isUpdate ? "Cập nhật thành công" : "Thêm thành công");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view, "Thao tác thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteSelected() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Chọn một dòng để xóa");
            return;
        }
        String ma = (String) view.getTableModel().getValueAt(row, 0);
        if (dao.deleteById(ma)) {
            JOptionPane.showMessageDialog(view, "Đã xóa");
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(view, "Xóa thất bại", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void onTableClickFillForm() {
        int row = view.getTable().getSelectedRow();
        if (row < 0) {
            return;
        }
        DefaultTableModel tm = view.getTableModel();
        view.getTfMaTaiSan().setText((String) tm.getValueAt(row, 0));
        view.getTfTenTaiSan().setText((String) tm.getValueAt(row, 1));
        view.getTfSoSerial().setText((String) tm.getValueAt(row, 2));
        view.getCbDanhMuc().setSelectedItem((String) tm.getValueAt(row, 3));
        view.getCbMaNhanVien().setSelectedItem((String) tm.getValueAt(row, 4));
        view.getTfNhaSanXuat().setText((String) tm.getValueAt(row, 5));
        view.getTfNhaCungCap().setText((String) tm.getValueAt(row, 6));
        view.getTfGiaMua().setText(String.valueOf(tm.getValueAt(row, 7)));
        view.getCbTinhTrang().setSelectedItem((String) tm.getValueAt(row, 8));
    }

    private TaiSan readForm() {
        String ma = view.getTfMaTaiSan().getText().trim();
        String ten = view.getTfTenTaiSan().getText().trim();
        String ser = view.getTfSoSerial().getText().trim();
        String dm = (String) view.getCbDanhMuc().getSelectedItem();
        String nv = (String) view.getCbMaNhanVien().getSelectedItem();
        String nsx = view.getTfNhaSanXuat().getText().trim();
        String ncc = view.getTfNhaCungCap().getText().trim();
        String giaStr = view.getTfGiaMua().getText().trim();
        String tt = (String) view.getCbTinhTrang().getSelectedItem();

        if (ma.isBlank() || ten.isBlank()) {
            throw new IllegalArgumentException("Mã/Tên tài sản không được trống");
        }
        double gia;
        try {
            gia = Double.parseDouble(giaStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Giá mua không hợp lệ");
        }

        return new TaiSan(ma, ten, ser, dm, nv, nsx, ncc, gia, tt);
    }

    private void clearForm() {
        view.getTfMaTaiSan().setText("");
        view.getTfTenTaiSan().setText("");
        view.getTfSoSerial().setText("");
        view.getCbDanhMuc().setSelectedIndex(0);
        view.getCbMaNhanVien().setSelectedIndex(0);
        view.getTfNhaSanXuat().setText("");
        view.getTfNhaCungCap().setText("");
        view.getTfGiaMua().setText("");
        view.getCbTinhTrang().setSelectedIndex(0);
        view.getTfKeyword().setText("");
        view.getTable().clearSelection();
    }

    private void fillTable(List<TaiSan> list) {
        DefaultTableModel tm = view.getTableModel();
        tm.setRowCount(0);
        for (TaiSan ts : list) {
            tm.addRow(new Object[]{
                ts.getMaTaiSan(), ts.getTenTaiSan(), ts.getSoSerial(),
                ts.getDanhMuc(), ts.getMaNhanVien(),
                ts.getNhaSanXuat(), ts.getNhaCungCap(),
                ts.getGiaMua(), ts.getTinhTrang()
            });
        }
    }
}
