package controller;

import dao.ViTriDAO;
import model.ViTri;
import view.ViTriPanel;

import java.util.List;

public class ViTriController {
    private ViTriPanel view;
    private ViTriDAO dao = new ViTriDAO();

    public ViTriController(ViTriPanel view) {
        this.view = view;
    }

    public void loadTableData() {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (ViTri v : dao.findAll()) {
            model.addRow(new Object[]{
                    v.getViTri(), v.getSoNguoi(), v.getDiaChi(), v.getThanhPho(),
                    v.getTaoLuc(), v.getCapNhatLuc()
            });
        }
    }

    public void insertViTri(String viTri, int soNguoi, String diaChi, String thanhPho) {
        dao.insert(new ViTri(viTri, soNguoi, diaChi, thanhPho, null, null));
        loadTableData();
    }

    public void updateViTri(String viTri, int soNguoi, String diaChi, String thanhPho) {
        dao.update(new ViTri(viTri, soNguoi, diaChi, thanhPho, null, null));
        loadTableData();
    }

    public void deleteViTri(String id) {
        dao.delete(id);
        loadTableData();
    }

    public void filterViTri(String keyword) {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (ViTri v : dao.filter(keyword)) {
            model.addRow(new Object[]{
                    v.getViTri(), v.getSoNguoi(), v.getDiaChi(), v.getThanhPho(),
                    v.getTaoLuc(), v.getCapNhatLuc()
            });
        }
    }
}