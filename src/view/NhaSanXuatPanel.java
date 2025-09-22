package view;

import controller.NhaSanXuatController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class NhaSanXuatPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfID, tfTaiSan, tfFilterID, tfFilterTaiSan;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV, btnRefresh;
    private NhaSanXuatController controller;

    public NhaSanXuatPanel() {
        controller = new NhaSanXuatController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // ==== Bảng ====
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NSX", "Tài Sản", "Tạo lúc", "Cập nhật lúc"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== Panel nhập liệu & nút ====
        JPanel panel = new JPanel(new GridLayout(8, 2, 5, 5));
        tfID = new JTextField();
        tfTaiSan = new JTextField();
        tfFilterID = new JTextField();
        tfFilterTaiSan = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc");
        btnRefresh = new JButton("Làm mới");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        panel.add(new JLabel("Mã NSX:")); panel.add(tfID);
        panel.add(new JLabel("Tài sản:")); panel.add(tfTaiSan);
        panel.add(btnInsert); panel.add(btnUpdate);
        panel.add(btnDelete); panel.add(btnRefresh);

        panel.add(new JLabel("Lọc Mã NSX:")); panel.add(tfFilterID);
        panel.add(new JLabel("Lọc Tài sản:")); panel.add(tfFilterTaiSan);
        panel.add(btnFilter); panel.add(new JLabel(""));
        panel.add(btnExportCSV); panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        // ==== Event Listeners ====
        btnInsert.addActionListener(e -> {
            String id = tfID.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã NSX không được để trống");
                return;
            }
            try {
                String txt = tfTaiSan.getText().trim();
                Integer taiSan = txt.isEmpty() ? null : Integer.parseInt(txt);
                controller.insertNhaSanXuat(id, taiSan);
                clearForm();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tài sản phải là số hoặc để trống");
            }
        });

        btnUpdate.addActionListener(e -> {
            String id = tfID.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chọn một bản ghi để cập nhật");
                return;
            }
            try {
                String txt = tfTaiSan.getText().trim();
                Integer taiSan = txt.isEmpty() ? null : Integer.parseInt(txt);
                controller.updateNhaSanXuat(id, taiSan);
                clearForm();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tài sản phải là số hoặc để trống");
            }
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = table.getValueAt(selected, 0).toString();
                controller.deleteNhaSanXuat(id);
                clearForm();
            }
        });

        btnFilter.addActionListener(e -> {
            controller.filterNhaSanXuat(
                    tfFilterID.getText().trim(),
                    tfFilterTaiSan.getText().trim()
            );
        });

        btnRefresh.addActionListener(e -> {
            controller.loadTableData();
            clearForm();
        });

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        // ==== Khi chọn dòng trên bảng ====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfID.setText(table.getValueAt(row, 0).toString());
                Object val = table.getValueAt(row, 1);
                tfTaiSan.setText(val == null ? "" : val.toString());
            }
        });
    }

    private void clearForm() {
        tfID.setText("");
        tfTaiSan.setText("");
        table.clearSelection();
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
            try (BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                reader.readLine(); // bỏ dòng tiêu đề
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String id = parts[0].trim();
                        String taiSanStr = parts[1].trim();
                        Integer taiSan = taiSanStr.isEmpty() ? null : Integer.parseInt(taiSanStr);
                        controller.insertNhaSanXuat(id, taiSan);
                        count++;
                    }
                }
                controller.loadTableData();
                JOptionPane.showMessageDialog(this, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập CSV: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
