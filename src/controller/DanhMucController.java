package controller;

import dao.DanhMucDAO;
import model.DanhMuc;
import view.DanhMucPanel;

public class DanhMucController {
    private DanhMucPanel view;
    private DanhMucDAO dao = new DanhMucDAO();

    public DanhMucController(DanhMucPanel view) {
        this.view = view;
    }

    public void loadTableData() {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (DanhMuc dm : dao.findAll()) {
            model.addRow(new Object[]{
                    dm.getDanhMuc(), dm.getLoai(), dm.getSoLuong(),
                    dm.getTaoLuc(), dm.getCapNhatLuc()
            });
        }
    }

    public void insertDanhMuc(String id, String loai, int soLuong) {
        dao.insert(new DanhMuc(id, loai, soLuong, null, null));
        loadTableData();
    }

    public void updateDanhMuc(String id, String loai, int soLuong) {
        dao.update(new DanhMuc(id, loai, soLuong, null, null));
        loadTableData();
    }

    public void deleteDanhMuc(String id) {
        dao.delete(id);
        loadTableData();
    }

    public void filterDanhMuc(String danhMuc, String loai, String soLuong) {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (DanhMuc dm : dao.filter(danhMuc, loai, soLuong)) {
            model.addRow(new Object[]{
                    dm.getDanhMuc(), dm.getLoai(), dm.getSoLuong(),
                    dm.getTaoLuc(), dm.getCapNhatLuc()
            });
        }
    }
}