package view;

import controller.NhanVienController;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.util.Vector;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMa, tfTen, tfHo, tfTenDN, tfViTri, tfEmail;
    private JComboBox<String> cbViTri;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter, btnExportCSV, btnImportCSV;
    private NhanVienController controller;

    public NhanVienPanel() {
        controller = new NhanVienController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NV", "Tên", "Họ", "Tên đăng nhập", "Vị trí", "Email"});
        table = new JTable(tableModel);

        // === Thêm JComboBox cho cột "Vị trí" trong JTable ===
        Vector<String> viTriList = controller.loadAllViTri(); // Lấy danh sách vị trí từ DB
        viTriList.add(0, "-- Không chọn --");
        JComboBox<String> comboBox = new JComboBox<>(viTriList);

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(10, 2));
        tfMa = new JTextField(); tfTen = new JTextField(); tfHo = new JTextField();
        tfTenDN = new JTextField(); cbViTri = new JComboBox<>(viTriList); tfEmail = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo mã nhân viên");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc chính xác");
        btnExportCSV = new JButton("Xuất CSV");
        btnImportCSV = new JButton("Nhập CSV");

        panel.add(new JLabel("Mã NV:")); panel.add(tfMa);
        panel.add(new JLabel("Tên:")); panel.add(tfTen);
        panel.add(new JLabel("Họ:")); panel.add(tfHo);
        panel.add(new JLabel("Tên đăng nhập:")); panel.add(tfTenDN);
        panel.add(new JLabel("Vị trí:")); panel.add(cbViTri);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);
        panel.add(btnExportCSV); panel.add(btnImportCSV);

        add(panel, BorderLayout.SOUTH);

        // === Event Listeners ===
        btnInsert.addActionListener(e -> {
            Object selectedViTri = cbViTri.getSelectedItem();
            if (selectedViTri.equals("-- Không chọn --")) selectedViTri = "";

            controller.insertNhanVien(
                    tfMa.getText(), tfTen.getText(), tfHo.getText(),
                    tfTenDN.getText(), (String) selectedViTri, tfEmail.getText()
            );
        });

        btnUpdate.addActionListener(e -> {
            Object selectedViTri = cbViTri.getSelectedItem();
            if (selectedViTri.equals("-- Không chọn --")) selectedViTri = "";

            controller.updateNhanVien(
                    tfMa.getText(), tfTen.getText(), tfHo.getText(),
                    tfTenDN.getText(), (String) selectedViTri, tfEmail.getText()
            );
        });

        btnFilter.addActionListener(e -> {
            Object selectedViTri = cbViTri.getSelectedItem();
            if (selectedViTri.equals("-- Không chọn --")) selectedViTri = "";

            controller.filterNhanVien(
                    tfMa.getText().trim(), tfTen.getText().trim(),
                    (String) selectedViTri, tfHo.getText().trim(), tfEmail.getText().trim()
            );
        });

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String ma = table.getValueAt(selected, 0).toString();
                controller.deleteNhanVien(ma);
            }
        });

        btnExportCSV.addActionListener(e -> exportCSV());
        btnImportCSV.addActionListener(e -> importCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMa.setText(table.getValueAt(row, 0).toString());
                tfTen.setText(table.getValueAt(row, 1).toString());
                tfHo.setText(table.getValueAt(row, 2).toString());
                tfTenDN.setText(table.getValueAt(row, 3).toString());
                cbViTri.setSelectedItem(table.getValueAt(row, 4).toString());
                tfEmail.setText(table.getValueAt(row, 5).toString());
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
                reader.readLine(); // bỏ dòng tiêu đề
                int count = 0;
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        String ma = parts[0].trim();
                        String ten = parts[1].trim();
                        String ho = parts[2].trim();
                        String tenDN = parts[3].trim();
                        String viTri = parts[4].trim();
                        String email = parts[5].trim();

                        controller.insertNhanVien(ma, ten, ho, tenDN, viTri, email);
                        count++;
                    }
                }
                controller.loadTableData();
                JOptionPane.showMessageDialog(this, "Nhập CSV thành công! Đã thêm " + count + " dòng.");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi nhập file CSV: " + e.getMessage());
            }
        }
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
