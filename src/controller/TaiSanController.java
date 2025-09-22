
package controller;

import dao.TaiSanDAO;
import java.util.List;
import model.TaiSan;
import view.TaiSanPanel; // Add this import if TaiSanPanel exists in the 'view' package

public class TaiSanController {
    private TaiSanPanel view;
    private TaiSanDAO dao = new TaiSanDAO();
    public TaiSanController() {
               this.view = view;

    }


    public void loadTableData() {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (TaiSan ts : dao.getAllTaiSan()) {
            model.addRow(new Object[]{
                   ts.getDanhMuc(), ts.getGiaMua(), ts.getMaNhanVien(), ts.getMaTaiSan(), ts.getNhaCungCap(),
                   ts.getNhaSanXuat(), ts.getSoSerial(), ts.getTenTaiSan(), ts.getTinhTrang()
            });
        }
    }
    public List<TaiSan> getAllTaiSan() {
        return dao.getAllTaiSan();
    }

    public boolean addTaiSan(TaiSan ts) {
        return dao.insertTaiSan(ts);
    }

    public boolean updateTaiSan(TaiSan ts) {
        return dao.updateTaiSan(ts);
    }

    public boolean deleteTaiSan(String maTaiSan) {
        return dao.deleteTaiSan(maTaiSan);
    }

    public List<TaiSan> searchTaiSan(String keyword) {
        return dao.searchTaiSan(keyword);
    }
}
