package view;

import controller.TaiSanController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import model.TaiSan;

public class TaiSanPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaTaiSan, tfTenTaiSan, tfSerial, tfDanhMuc, tfMaNV,
            tfNSX, tfNCC, tfGiaMua, tfTinhTrang;
    private JTextField tfFilterMa, tfFilterTen, tfFilterTinhTrang;
    private JButton btnThem, btnCapNhat, btnXoa, btnLamMoi, btnLoc, btnXuatCSV, btnNhapCSV;
    private TaiSanController controller;

    public TaiSanPanel() {
        controller = new TaiSanController();
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ==== Table ====
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Mã tài sản", "Tên", "Serial", "Danh mục", "NV", "NSX", "NCC", "Giá", "Tình trạng"
        });
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== Form & Buttons ====
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        tfMaTaiSan = new JTextField();
        tfTenTaiSan = new JTextField();
        tfSerial = new JTextField();
        tfDanhMuc = new JTextField();
        tfMaNV = new JTextField();
        tfNSX = new JTextField();
        tfNCC = new JTextField();
        tfGiaMua = new JTextField();
        tfTinhTrang = new JTextField();

        formPanel.add(new JLabel("Mã tài sản:")); formPanel.add(tfMaTaiSan);
        formPanel.add(new JLabel("Tên tài sản:")); formPanel.add(tfTenTaiSan);
        formPanel.add(new JLabel("Serial:")); formPanel.add(tfSerial);
        formPanel.add(new JLabel("Danh mục:")); formPanel.add(tfDanhMuc);
        formPanel.add(new JLabel("Mã NV:")); formPanel.add(tfMaNV);
        formPanel.add(new JLabel("NSX:")); formPanel.add(tfNSX);
        formPanel.add(new JLabel("NCC:")); formPanel.add(tfNCC);
        formPanel.add(new JLabel("Giá mua:")); formPanel.add(tfGiaMua);
        formPanel.add(new JLabel("Tình trạng:")); formPanel.add(tfTinhTrang);

        add(formPanel, BorderLayout.NORTH);

        // ==== Lọc ====
        JPanel filterPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        tfFilterMa = new JTextField();
        tfFilterTen = new JTextField();
        tfFilterTinhTrang = new JTextField();
        btnLoc = new JButton("Lọc");

        filterPanel.add(new JLabel("Lọc mã:")); filterPanel.add(tfFilterMa);
        filterPanel.add(new JLabel("Lọc tên:")); filterPanel.add(tfFilterTen);
        filterPanel.add(new JLabel("Lọc tình trạng:")); filterPanel.add(tfFilterTinhTrang);
        filterPanel.add(new JLabel("")); filterPanel.add(btnLoc);
        add(filterPanel, BorderLayout.CENTER);

        // ==== Nút chức năng ====
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        btnThem = new JButton("Thêm");
        btnCapNhat = new JButton("Cập nhật");
        btnXoa = new JButton("Xoá");
        btnLamMoi = new JButton("Làm mới");
        btnXuatCSV = new JButton("Xuất CSV");
        btnNhapCSV = new JButton("Nhập CSV");

        buttonPanel.add(btnThem); buttonPanel.add(btnCapNhat);
        buttonPanel.add(btnXoa); buttonPanel.add(btnLamMoi);
        buttonPanel.add(btnXuatCSV); buttonPanel.add(btnNhapCSV);
        add(buttonPanel, BorderLayout.SOUTH);

        // ==== Sự kiện ====
        btnThem.addActionListener(e -> controller.insertTaiSan());
        btnCapNhat.addActionListener(e -> controller.updateTaiSan());
        btnXoa.addActionListener(e -> controller.deleteTaiSan());
        btnLamMoi.addActionListener(e -> {
            clearForm();
            controller.loadTableData();
        });
        btnLoc.addActionListener(e -> controller.filterTaiSan(
                tfFilterMa.getText(), tfFilterTen.getText(), tfFilterTinhTrang.getText()
        ));
        btnXuatCSV.addActionListener(e -> controller.exportCSV());
        btnNhapCSV.addActionListener(e -> controller.importCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaTaiSan.setText(valueAt(row, 0));
                tfTenTaiSan.setText(valueAt(row, 1));
                tfSerial.setText(valueAt(row, 2));
                tfDanhMuc.setText(valueAt(row, 3));
                tfMaNV.setText(valueAt(row, 4));
                tfNSX.setText(valueAt(row, 5));
                tfNCC.setText(valueAt(row, 6));
                tfGiaMua.setText(valueAt(row, 7));
                tfTinhTrang.setText(valueAt(row, 8));
            }
        });
    }

    private String valueAt(int row, int col) {
        Object val = table.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    private void clearForm() {
        tfMaTaiSan.setText("");
        tfTenTaiSan.setText("");
        tfSerial.setText("");
        tfDanhMuc.setText("");
        tfMaNV.setText("");
        tfNSX.setText("");
        tfNCC.setText("");
        tfGiaMua.setText("");
        tfTinhTrang.setText("");
        table.clearSelection();
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public TaiSan getFormData() {
        String ma = tfMaTaiSan.getText().trim();
        String ten = tfTenTaiSan.getText().trim();
        String serial = tfSerial.getText().trim();
        String danhMuc = tfDanhMuc.getText().trim();
        String maNV = tfMaNV.getText().trim();
        String nsx = tfNSX.getText().trim();
        String ncc = tfNCC.getText().trim();
        BigDecimal gia = null;
        try {
            gia = tfGiaMua.getText().trim().isEmpty() ? null : new BigDecimal(tfGiaMua.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Giá mua không hợp lệ!");
        }
        String tinhTrang = tfTinhTrang.getText().trim();

        return new TaiSan(ma, ten, serial, danhMuc, maNV, nsx, ncc, gia, tinhTrang);
    }

    public String getSelectedMaTaiSan() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            return table.getValueAt(row, 0).toString();
        }
        return null;
    }

}