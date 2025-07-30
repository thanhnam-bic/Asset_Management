package view;

import controller.ViTriController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class ViTriPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private ViTriController controller;
    private JTextField tfViTri, tfSoNguoi, tfDiaChi, tfThanhPho, tfFilter;
    private JLabel lblTaoLuc, lblCapNhatLuc;
    private JButton btnInsert, btnDelete, btnFilter, btnUpdate, btnExportCSV, btnImportCSV;

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
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");
        

        inputPanel.add(new JLabel("Vị trí:")); inputPanel.add(tfViTri);
        inputPanel.add(new JLabel("Số người:")); inputPanel.add(tfSoNguoi);
        inputPanel.add(new JLabel("Địa chỉ:")); inputPanel.add(tfDiaChi);
        inputPanel.add(new JLabel("Thành phố:")); inputPanel.add(tfThanhPho);
        inputPanel.add(btnInsert); inputPanel.add(btnDelete);
        inputPanel.add(btnUpdate); inputPanel.add(btnFilter);
        inputPanel.add(btnExportCSV); inputPanel.add(btnImportCSV);

        add(inputPanel, BorderLayout.SOUTH);
        
        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

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
            controller.filterViTri(tfViTri.getText().trim(), tfThanhPho.getText().trim(), tfSoNguoi.getText().trim());
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
    
    private void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.print(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        writer.print(tableModel.getValueAt(i, j));
                        if (j < tableModel.getColumnCount() - 1) writer.print(",");
                    }
                    writer.println();
                }
                JOptionPane.showMessageDialog(this, "Xuất CSV thành công!");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất file CSV: " + e.getMessage());
            }
        }
    }

    private void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                String line = reader.readLine(); // Bỏ qua dòng tiêu đề

                int count = 0;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 4) {
                        String viTri = parts[0].trim();
                        int soNguoi = Integer.parseInt(parts[1].trim());
                        String diaChi = parts[2].trim();
                        String thanhPho = parts[3].trim();

                        // Ghi vào cơ sở dữ liệu
                        controller.insertViTri(viTri, soNguoi, diaChi, thanhPho);
                        count++;
                    }
                }

                // Tải lại dữ liệu bảng sau khi import xong
                controller.loadTableData();
                JOptionPane.showMessageDialog(this, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập file CSV: " + ex.getMessage());
            }
        }
    }

    public JTable getTable() {
        return table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}