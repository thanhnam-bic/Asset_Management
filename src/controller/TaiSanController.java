package controller;

import dao.TaiSanDAO;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.TaiSan;
import view.TaiSanPanel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

    public void loadComboboxes() {
        var dm = new javax.swing.DefaultComboBoxModel<String>();
        dm.addElement("-- Không chọn --");
        for (String code : dao.getAllDanhMuc()) {
            dm.addElement(code);
        }
        view.getCbDanhMuc().setModel(dm);
        view.getCbDanhMuc().setSelectedIndex(0);

        var nv = new javax.swing.DefaultComboBoxModel<String>();
        nv.addElement("-- Không chọn --");
        for (String code : dao.getAllNhanVien()) {
            nv.addElement(code);
        }
        view.getCbMaNhanVien().setModel(nv);
        view.getCbMaNhanVien().setSelectedIndex(0);

        var ncc = new javax.swing.DefaultComboBoxModel<String>();
        ncc.addElement("-- Không chọn --");
        for (String code : dao.getAllNhaCungCap()) {
            ncc.addElement(code);
        }
        view.getCbNhaCungCap().setModel(ncc);
        view.getCbNhaCungCap().setSelectedIndex(0);

        var nsx = new javax.swing.DefaultComboBoxModel<String>();
        nsx.addElement("-- Không chọn --");
        for (String code : dao.getAllNhaSanXuat()) {
            nsx.addElement(code);
        }
        view.getCbNhaSanXuat().setModel(nsx);
        view.getCbNhaSanXuat().setSelectedIndex(0);
    }

    public void filter() {
        String maTS = view.getTfMaTaiSan().getText().trim();
        String maNV = (String) view.getCbMaNhanVien().getSelectedItem();
        String danhMuc = (String) view.getCbDanhMuc().getSelectedItem();
        String tinhTrang = (String) view.getCbTinhTrang().getSelectedItem();
        String ncc = (String) view.getCbNhaCungCap().getSelectedItem();   // <== đúng
        String nsx = (String) view.getCbNhaSanXuat().getSelectedItem();   // <== đúng

        if (maNV != null && maNV.equalsIgnoreCase("-- Không chọn --")) {
            maNV = null;
        }
        if (danhMuc != null && danhMuc.equalsIgnoreCase("-- Không chọn --")) {
            danhMuc = null;
        }
        if ("-- Không chọn --".equalsIgnoreCase(tinhTrang)) {
            tinhTrang = null;
        }
        if (ncc != null && ncc.equalsIgnoreCase("-- Không chọn --")) {
            ncc = null;
        }
        if (nsx != null && nsx.equalsIgnoreCase("-- Không chọn --")) {
            nsx = null;
        }

        List<TaiSan> list = dao.filter(maTS, maNV, danhMuc, tinhTrang, ncc, nsx);
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
        view.getCbNhaSanXuat().setSelectedItem((String) tm.getValueAt(row, 5));
        view.getCbNhaCungCap().setSelectedItem((String) tm.getValueAt(row, 6));
        view.getTfGiaMua().setText(String.valueOf(tm.getValueAt(row, 7)));
        view.getCbTinhTrang().setSelectedItem((String) tm.getValueAt(row, 8));
    }

    private TaiSan readForm() {
        String ma = view.getTfMaTaiSan().getText().trim();
        String ten = view.getTfTenTaiSan().getText().trim();
        String ser = view.getTfSoSerial().getText().trim();
        String dm = (String) view.getCbDanhMuc().getSelectedItem();
        String nv = (String) view.getCbMaNhanVien().getSelectedItem();
        String nsx = (String) view.getCbNhaSanXuat().getSelectedItem();
        String ncc = (String) view.getCbNhaCungCap().getSelectedItem();
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

    public void clearForm() {
        view.getTfMaTaiSan().setText("");
        view.getTfTenTaiSan().setText("");
        view.getTfSoSerial().setText("");
        view.getCbDanhMuc().setSelectedIndex(0);
        view.getCbMaNhanVien().setSelectedIndex(0);
        view.getCbNhaSanXuat().setSelectedIndex(0);
        view.getCbNhaCungCap().setSelectedIndex(0);
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

    public void xuatCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("TaiSan.csv"));
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath() + ".csv");
            }
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                DefaultTableModel tm = view.getTableModel();
                for (int i = 0; i < tm.getColumnCount(); i++) {
                    writer.print(tm.getColumnName(i));
                    if (i < tm.getColumnCount() - 1) {
                        writer.print(",");
                    }
                }
                writer.println();

                // data
                for (int r = 0; r < tm.getRowCount(); r++) {
                    for (int c = 0; c < tm.getColumnCount(); c++) {
                        Object val = tm.getValueAt(r, c);
                        writer.print(val == null ? "" : val.toString());
                        if (c < tm.getColumnCount() - 1) {
                            writer.print(",");
                        }
                    }
                    writer.println();
                }
                JOptionPane.showMessageDialog(view, "Xuất CSV thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xuất file CSV: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void nhapCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                reader.readLine();
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.isBlank()) {
                        continue;
                    }
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 9) {
                        String maTaiSan = parts[0].trim();
                        String tenTaiSan = parts[1].trim();
                        String soSerial = parts[2].trim();
                        String danhMuc = parts[3].trim();
                        String maNhanVien = parts[4].trim();
                        String nhaSX = parts[5].trim();
                        String nhaCC = parts[6].trim();
                        double giaMua = 0;
                        try {
                            if (!parts[7].trim().isEmpty()) {
                                giaMua = Double.parseDouble(parts[7].trim());
                            }
                        } catch (NumberFormatException ignore) {
                        }
                        String tinhTrang = parts[8].trim();

                        TaiSan ts = new TaiSan(maTaiSan, tenTaiSan, soSerial, danhMuc,
                                maNhanVien, nhaSX, nhaCC, giaMua, tinhTrang);

                        dao.insert(ts);
                        count++;
                    }
                }
                loadTableData();
                JOptionPane.showMessageDialog(view, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi nhập file CSV: " + e.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
