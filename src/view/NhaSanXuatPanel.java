package view;

import controller.NhaSanXuatController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class NhaSanXuatPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMaNSX, tfMaTaiSan;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV;
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
        tableModel.setColumnIdentifiers(new String[]{"Mã NSX", "Mã Tài Sản", "Tạo lúc", "Cập nhật lúc"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== Panel nhập liệu & nút ====
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        tfMaNSX = new JTextField();
        tfMaTaiSan = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        // ======= Thêm label + field =======
        panel.add(new JLabel("Mã NSX:"));
        panel.add(tfMaNSX);

        panel.add(new JLabel("Mã Tài sản:"));
        panel.add(tfMaTaiSan);

        // ======= Thêm nút theo cặp =======
        panel.add(btnInsert);
        panel.add(btnDelete);

        panel.add(btnUpdate);
        panel.add(btnFilter);

        panel.add(btnExportCSV);
        panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        // ==== Event Listeners ====
        btnInsert.addActionListener(e -> {
            String maNSX = tfMaNSX.getText().trim();
            String maTaiSan = tfMaTaiSan.getText().trim();
            if (maNSX.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mã NSX không được để trống");
                return;
            }
            controller.insertNhaSanXuat(maNSX, maTaiSan.isEmpty() ? null : maTaiSan);
            clearForm();
        });

        btnUpdate.addActionListener(e -> {
            String maNSX = tfMaNSX.getText().trim();
            String maTaiSan = tfMaTaiSan.getText().trim();
            if (maNSX.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chọn một bản ghi để cập nhật");
                return;
            }
            controller.updateNhaSanXuat(maNSX, maTaiSan.isEmpty() ? null : maTaiSan);
            clearForm();
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String maNSX = table.getValueAt(selected, 0).toString();
                controller.deleteNhaSanXuat(maNSX);
                clearForm();
            }
        });

        btnFilter.addActionListener(e -> {
            controller.filterNhaSanXuat(
                    tfMaNSX.getText().trim(),
                    tfMaTaiSan.getText().trim()
            );
        });

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        // ==== Khi chọn dòng trên bảng ====
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMaNSX.setText(table.getValueAt(row, 0).toString());
                Object val = table.getValueAt(row, 1);
                tfMaTaiSan.setText(val == null ? "" : val.toString());
            }
        });
    }

    private void clearForm() {
        tfMaNSX.setText("");
        tfMaTaiSan.setText("");
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
                        String maNSX = parts[0].trim();
                        String maTaiSan = parts[1].trim();
                        controller.insertNhaSanXuat(maNSX, maTaiSan.isEmpty() ? null : maTaiSan);
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
