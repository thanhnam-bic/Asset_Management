package controller;

import dao.NhaSanXuatDAO;
import model.NhaSanXuat;
import view.NhaSanXuatPanel;

import javax.swing.table.DefaultTableModel;
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
            Object taiSanCell = (nsx.getMaTaiSan() == null) ? "" : nsx.getMaTaiSan();
            model.addRow(new Object[]{
                    nsx.getMaNhaSanXuat(),
                    taiSanCell,
                    nsx.getTaoLuc(),
                    nsx.getCapNhatLuc()
            });
        }
    }

    public void insertNhaSanXuat(String id, String maTaiSan) {
        dao.insert(new NhaSanXuat(id, maTaiSan, null, null));
        loadTableData();
    }

    public void updateNhaSanXuat(String id, String maTaiSan) {
        dao.update(new NhaSanXuat(id, maTaiSan, null, null));
        loadTableData();
    }

    public void deleteNhaSanXuat(String id) {
        dao.delete(id);
        loadTableData();
    }

    public void filterNhaSanXuat(String maNhaSanXuat, String maTaiSan) {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (NhaSanXuat nsx : dao.filter(maNhaSanXuat, maTaiSan)) {
            Object taiSanCell = (nsx.getMaTaiSan() == null) ? "" : nsx.getMaTaiSan();
            model.addRow(new Object[]{
                    nsx.getMaNhaSanXuat(),
                    taiSanCell,
                    nsx.getTaoLuc(),
                    nsx.getCapNhatLuc()
            });
        }
    }
}
