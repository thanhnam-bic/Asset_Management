package view;

import controller.DanhMucController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class DanhMucPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfID, tfLoai, tfSoLuong, tfFilter;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV;
    private DanhMucController controller;

    public DanhMucPanel() {
        controller = new DanhMucController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Danh mục", "Loại", "Số lượng", "Tạo lúc", "Cập nhật lúc"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(7, 2));
        tfID = new JTextField(); tfLoai = new JTextField();
        tfSoLuong = new JTextField(); tfFilter = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo danh mục");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc chính xác");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        panel.add(new JLabel("Danh mục:")); panel.add(tfID);
        panel.add(new JLabel("Loại:")); panel.add(tfLoai);
        panel.add(new JLabel("Số lượng:")); panel.add(tfSoLuong);
        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);
        panel.add(btnExportCSV); panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        btnInsert.addActionListener(e -> {
            try {
                controller.insertDanhMuc(tfID.getText(), tfLoai.getText(), Integer.parseInt(tfSoLuong.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                controller.updateDanhMuc(tfID.getText(), tfLoai.getText(), Integer.parseInt(tfSoLuong.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = table.getValueAt(selected, 0).toString();
                controller.deleteDanhMuc(id);
            }
        });

        btnFilter.addActionListener(e -> controller.filterDanhMuc(
                tfID.getText().trim(), tfLoai.getText().trim(), tfSoLuong.getText().trim()
        ));

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfID.setText(table.getValueAt(row, 0).toString());
                tfLoai.setText(table.getValueAt(row, 1).toString());
                tfSoLuong.setText(table.getValueAt(row, 2).toString());
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
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất CSV: " + e.getMessage());
            }
        }
    }

    private void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                reader.readLine(); // bỏ dòng tiêu đề
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3) {
                        String id = parts[0].trim();
                        String loai = parts[1].trim();
                        int soLuong = Integer.parseInt(parts[2].trim());
                        controller.insertDanhMuc(id, loai, soLuong);
                        count++;
                    }
                }
                controller.loadTableData();
                JOptionPane.showMessageDialog(this, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập CSV: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
