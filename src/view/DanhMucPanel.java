package view;

import controller.DanhMucController;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class DanhMucPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfID, tfLoai, tfSoLuong, tfFilter;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter;
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

        JPanel panel = new JPanel(new GridLayout(6, 2));
        tfID = new JTextField(); tfLoai = new JTextField();
        tfSoLuong = new JTextField(); tfFilter = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo danh mục");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc chính xác");

        panel.add(new JLabel("Danh mục:")); panel.add(tfID);
        panel.add(new JLabel("Loại:")); panel.add(tfLoai);
        panel.add(new JLabel("Số lượng:")); panel.add(tfSoLuong);
        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);

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

        btnFilter.addActionListener(e -> controller.filterDanhMuc(tfID.getText().trim(), tfLoai.getText().trim(), tfSoLuong.getText().trim()));

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfID.setText(table.getValueAt(row, 0).toString());
                tfLoai.setText(table.getValueAt(row, 1).toString());
                tfSoLuong.setText(table.getValueAt(row, 2).toString());
            }
        });
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
