package view;

import controller.NhaSanXuatController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class NhaSanXuatPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfID, tfTen, tfQuocGia, tfWebsite, tfSoDienThoai, tfEmail, tfDiaChi;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV;
    private NhaSanXuatController controller;

    public NhaSanXuatPanel() {
        controller = new NhaSanXuatController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "Mã NSX", "Tên NSX", "Quốc gia", "Website",
            "Số điện thoại", "Email", "Địa chỉ"
        });
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        tfID = new JTextField();
        tfTen = new JTextField();
        tfQuocGia = new JTextField();
        tfWebsite = new JTextField();
        tfSoDienThoai = new JTextField();
        tfEmail = new JTextField();
        tfDiaChi = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo mã NSX");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc chính xác");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        panel.add(new JLabel("Mã NSX:")); panel.add(tfID);
        panel.add(new JLabel("Tên NSX:")); panel.add(tfTen);
        panel.add(new JLabel("Quốc gia:")); panel.add(tfQuocGia);
        panel.add(new JLabel("Website:")); panel.add(tfWebsite);
        panel.add(new JLabel("Số điện thoại:")); panel.add(tfSoDienThoai);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(new JLabel("Địa chỉ:")); panel.add(tfDiaChi);

        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);
        panel.add(btnExportCSV); panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        // === Event Listeners ===
        btnInsert.addActionListener(e -> controller.insertNhaSanXuat(
                tfID.getText(), tfTen.getText(), tfQuocGia.getText(),
                tfWebsite.getText(), tfSoDienThoai.getText(),
                tfEmail.getText(), tfDiaChi.getText()
        ));

        btnUpdate.addActionListener(e -> controller.updateNhaSanXuat(
                tfID.getText(), tfTen.getText(), tfQuocGia.getText(),
                tfWebsite.getText(), tfSoDienThoai.getText(),
                tfEmail.getText(), tfDiaChi.getText()
        ));

        btnFilter.addActionListener(e -> controller.filterNhaSanXuat(
                tfID.getText().trim(), tfTen.getText().trim()
        ));

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = table.getValueAt(selected, 0).toString();
                controller.deleteNhaSanXuat(id);
            }
        });

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfID.setText(getValue(row, 0));
                tfTen.setText(getValue(row, 1));
                tfQuocGia.setText(getValue(row, 2));
                tfWebsite.setText(getValue(row, 3));
                tfSoDienThoai.setText(getValue(row, 4));
                tfEmail.setText(getValue(row, 5));
                tfDiaChi.setText(getValue(row, 6));
            }
        });
    }

    private String getValue(int row, int col) {
        Object val = table.getValueAt(row, col);
        return val == null ? "" : val.toString();
    }

    private void exportCSV() {
    JFileChooser fileChooser = new JFileChooser();
    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(fileChooser.getSelectedFile()), "UTF-8")) {

            // Ghi BOM để Excel nhận UTF-8
            writer.write('\uFEFF');

            // --- Ghi header ---
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                writer.write(tableModel.getColumnName(i));
                if (i < tableModel.getColumnCount() - 1) writer.write(",");
            }
            writer.write("\n");

            // --- Ghi dữ liệu ---
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = 0; j < tableModel.getColumnCount(); j++) {
                    Object val = tableModel.getValueAt(i, j);
                    writer.write(val == null ? "" : val.toString());
                    if (j < tableModel.getColumnCount() - 1) writer.write(",");
                }
                writer.write("\n");
            }

            writer.flush();
            JOptionPane.showMessageDialog(this, "Xuất CSV thành công (UTF-8)!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xuất CSV: " + e.getMessage());
        }
    }
}


    private void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), "UTF-8"))
) {
                reader.readLine(); // bỏ header
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 7) {
                        controller.insertNhaSanXuat(
                                parts[0].trim(), parts[1].trim(),
                                parts[2].trim(), parts[3].trim(),
                                parts[4].trim(), parts[5].trim(), parts[6].trim()
                        );
                        count++;
                    }
                }
                controller.loadTableData();
                JOptionPane.showMessageDialog(this, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập CSV: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public void showMessage(String message, String title, int messageType) { JOptionPane.showMessageDialog(this, message, title, messageType); }
}
