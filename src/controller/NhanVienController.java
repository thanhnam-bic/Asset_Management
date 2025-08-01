package controller;

import dao.NhanVienDAO;
import model.NhanVien;
import view.NhanVienPanel;
import java.util.Vector;

public class NhanVienController {
    private NhanVienPanel view;
    private NhanVienDAO dao = new NhanVienDAO();

    public NhanVienController(NhanVienPanel view) {
        this.view = view;
    }

    public void loadTableData() {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (NhanVien nv : dao.findAll()) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(), nv.getTen(), nv.getHo(),
                    nv.getTenDangNhap(), nv.getViTri(), nv.getEmail()
            });
        }
    }

    public void insertNhanVien(String maNV, String ten, String ho, String tenDN, String viTri, String email) {
        dao.insert(new NhanVien(maNV, ten, ho, tenDN, viTri, email));
        loadTableData();
    }

    public void updateNhanVien(String maNV, String ten, String ho, String tenDN, String viTri, String email) {
        dao.update(new NhanVien(maNV, ten, ho, tenDN, viTri, email));
        loadTableData();
    }

    public void deleteNhanVien(String maNV) {
        dao.delete(maNV);
        loadTableData();
    }

    public void filterNhanVien(String ma, String ten, String viTri, String ho, String email) {
        var model = view.getTableModel();
        model.setRowCount(0);
        for (NhanVien nv : dao.filter(ma, ten, viTri, ho, email)) {
            model.addRow(new Object[]{
                    nv.getMaNhanVien(), nv.getTen(), nv.getHo(),
                    nv.getTenDangNhap(), nv.getViTri(), nv.getEmail()
            });
        }
    }
    
    public Vector<String> loadAllViTri() {
        return dao.loadAllViTri();
    }
    
}