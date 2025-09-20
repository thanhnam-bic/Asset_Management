package controller;

import dao.NhaSanXuatDAO;
import model.NhaSanXuat;
import view.NhaSanXuatPanel;

import javax.swing.table.DefaultTableModel;
import java.sql.Timestamp;
import java.util.List;

public class NhaSanXuatController {
    private NhaSanXuatPanel view;
    private NhaSanXuatDAO dao = new NhaSanXuatDAO();

    public NhaSanXuatController(NhaSanXuatPanel view) {
        this.view = view;
    }

    public void loadTableData() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        List<NhaSanXuat> list = dao.findAll();
        for (NhaSanXuat nsx : list) {
            Object taiSanCell = nsx.getTaiSan() == null ? "" : nsx.getTaiSan();
            model.addRow(new Object[]{
                    nsx.getNhaSanXuat(), taiSanCell,
                    nsx.getTaoLuc(), nsx.getCapNhatLuc()
            });
        }
    }

    public void insertNhaSanXuat(String id, Integer taiSan) {
        dao.insert(new NhaSanXuat(id, taiSan, null, null));
        loadTableData();
    }

    public void updateNhaSanXuat(String id, Integer taiSan) {
        dao.update(new NhaSanXuat(id, taiSan, null, null));
        loadTableData();
    }

    public void deleteNhaSanXuat(String id) {
        dao.delete(id);
        loadTableData();
    }

    public void filterNhaSanXuat(String nhaSanXuat, String taiSan) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (NhaSanXuat nsx : dao.filter(nhaSanXuat, taiSan)) {
            Object taiSanCell = nsx.getTaiSan() == null ? "" : nsx.getTaiSan();
            model.addRow(new Object[]{
                    nsx.getNhaSanXuat(), taiSanCell,
                    nsx.getTaoLuc(), nsx.getCapNhatLuc()
            });
        }
    }
}
