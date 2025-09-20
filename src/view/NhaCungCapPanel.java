package view;

import controller.NhaCungCapController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class NhaCungCapPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfID, tfTenLienHe, tfDuongDan, tfTaiSan;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV;
    private NhaCungCapController controller;

    public NhaCungCapPanel() {
        controller = new NhaCungCapController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NCC", "Tên liên hệ", "Đường dẫn", "Tài sản", "Tạo lúc", "Cập nhật lúc"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        tfID = new JTextField(); 
        tfTenLienHe = new JTextField();
        tfDuongDan = new JTextField(); 
        tfTaiSan = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo mã");
        btnDelete = new JButton("Xóa");
        btnFilter = new JButton("Lọc");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        panel.add(new JLabel("Mã nhà cung cấp:")); panel.add(tfID);
        panel.add(new JLabel("Tên liên hệ:")); panel.add(tfTenLienHe);
        panel.add(new JLabel("Đường dẫn:")); panel.add(tfDuongDan);
        panel.add(new JLabel("Tài sản:")); panel.add(tfTaiSan);
        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);
        panel.add(btnExportCSV); panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        btnInsert.addActionListener(e -> {
            try {
                controller.insertNhaCungCap(tfID.getText(), tfTenLienHe.getText(), 
                                          tfDuongDan.getText(), Integer.parseInt(tfTaiSan.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tài sản phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(e -> {
            try {
                controller.updateNhaCungCap(tfID.getText(), tfTenLienHe.getText(), 
                                          tfDuongDan.getText(), Integer.parseInt(tfTaiSan.getText()));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Tài sản phải là số nguyên", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String id = table.getValueAt(selected, 0).toString();
                controller.deleteNhaCungCap(id);
            }
        });

        btnFilter.addActionListener(e -> controller.filterNhaCungCap(
                tfID.getText().trim(), 
                tfTenLienHe.getText().trim(), 
                tfDuongDan.getText().trim(),
                tfTaiSan.getText().trim()
        ));

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfID.setText(table.getValueAt(row, 0).toString());
                tfTenLienHe.setText(table.getValueAt(row, 1).toString());
                tfDuongDan.setText(table.getValueAt(row, 2).toString());
                tfTaiSan.setText(table.getValueAt(row, 3).toString());
            }
        });
    }

    private void exportCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("NhaCungCap.csv"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                // Ghi header
                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    writer.print(tableModel.getColumnName(i));
                    if (i < tableModel.getColumnCount() - 1) writer.print(",");
                }
                writer.println();
                
                // Ghi dữ liệu
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        Object value = tableModel.getValueAt(i, j);
                        String valueStr = (value != null) ? value.toString() : "";
                        // Escape comma nếu có trong dữ liệu
                        if (valueStr.contains(",")) {
                            valueStr = "\"" + valueStr + "\"";
                        }
                        writer.print(valueStr);
                        if (j < tableModel.getColumnCount() - 1) writer.print(",");
                    }
                    writer.println();
                }
                JOptionPane.showMessageDialog(this, "Xuất CSV thành công!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi xuất CSV: " + ex.getMessage(), 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void importCSV() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".csv");
            }
            
            @Override
            public String getDescription() {
                return "CSV Files (*.csv)";
            }
        });
        
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                reader.readLine(); // bỏ dòng tiêu đề
                int count = 0;
                int errors = 0;
                String line;
                
                while ((line = reader.readLine()) != null) {
                    try {
                        String[] parts = line.split(",");
                        if (parts.length >= 4) {
                            String id = parts[0].trim().replaceAll("\"", "");
                            String tenLienHe = parts[1].trim().replaceAll("\"", "");
                            String duongDan = parts[2].trim().replaceAll("\"", "");
                            int taiSan = Integer.parseInt(parts[3].trim().replaceAll("\"", ""));
                            
                            controller.insertNhaCungCap(id, tenLienHe, duongDan, taiSan);
                            count++;
                        }
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                        errors++;
                        System.err.println("Lỗi khi xử lý dòng: " + line + " - " + ex.getMessage());
                    }
                }
                
                controller.loadTableData();
                String message = "Nhập CSV thành công! Đã thêm " + count + " dòng.";
                if (errors > 0) {
                    message += "\nCó " + errors + " dòng bị lỗi và không thể nhập.";
                }
                JOptionPane.showMessageDialog(this, message);
                
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file CSV: " + ex.getMessage(), 
                                            "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void clearForm() {
        tfID.setText("");
        tfTenLienHe.setText("");
        tfDuongDan.setText("");
        tfTaiSan.setText("");
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}