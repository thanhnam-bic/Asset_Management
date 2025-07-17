package view;

import controller.ViTriController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViTriPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ViTriController controller;
    private JTextField tfViTri, tfSoNguoi, tfDiaChi, tfThanhPho, tfFilter;
    private JLabel lblTaoLuc, lblCapNhatLuc;
    private JButton btnInsert, btnDelete, btnFilter, btnUpdate;

    public ViTriPanel() {
        controller = new ViTriController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "Vị trí", "Số người", "Địa chỉ", "Thành phố", "Tạo lúc", "Cập nhật lúc"
        });
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(9, 2));
        tfViTri = new JTextField();
        tfSoNguoi = new JTextField();
        tfDiaChi = new JTextField();
        tfThanhPho = new JTextField();
        tfFilter = new JTextField();

        lblTaoLuc = new JLabel();
        lblCapNhatLuc = new JLabel();

        btnInsert = new JButton("Thêm");
        btnDelete = new JButton("Xoá");
        btnUpdate = new JButton("Cập nhật theo vị trí");
        btnFilter = new JButton("Lọc chính xác");
        

        inputPanel.add(new JLabel("Vị trí:")); inputPanel.add(tfViTri);
        inputPanel.add(new JLabel("Số người:")); inputPanel.add(tfSoNguoi);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Thành phố:")); inputPanel.add(tfThanhPho);
        inputPanel.add(btnInsert); inputPanel.add(btnDelete);
        inputPanel.add(btnUpdate); inputPanel.add(btnFilter);

        add(inputPanel, BorderLayout.SOUTH);

        btnInsert.addActionListener(e -> {
            try {
                controller.insertViTri(
                        tfViTri.getText().trim(),
                        Integer.parseInt(tfSoNguoi.getText().trim()),
                        tfDiaChi.getText().trim(),
                        tfThanhPho.getText().trim()
                );
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho 'Số người'", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String viTriId = table.getValueAt(selectedRow, 0).toString();
                controller.deleteViTri(viTriId);
            }
        });

        btnFilter.addActionListener(e -> {
            controller.filterViTri(tfFilter.getText().trim());
        });

        btnUpdate.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                try {
                    String viTri = tfViTri.getText().trim();
                    int soNguoi = Integer.parseInt(tfSoNguoi.getText().trim());
                    String diaChi = tfDiaChi.getText().trim();
                    String thanhPho = tfThanhPho.getText().trim();
                    controller.updateViTri(viTri, soNguoi, diaChi, thanhPho);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Số người không hợp lệ", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                tfViTri.setText(table.getValueAt(selectedRow, 0).toString());
                tfSoNguoi.setText(table.getValueAt(selectedRow, 1).toString());
                tfDiaChi.setText(table.getValueAt(selectedRow, 2).toString());
                tfThanhPho.setText(table.getValueAt(selectedRow, 3).toString());
                lblTaoLuc.setText(table.getValueAt(selectedRow, 4).toString());
                lblCapNhatLuc.setText(table.getValueAt(selectedRow, 5).toString());
            }
        });
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}