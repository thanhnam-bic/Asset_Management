package view;

import controller.NhanVienController;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class NhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfMa, tfTen, tfHo, tfTenDN, tfViTri, tfEmail;
    private JButton btnInsert, btnUpdate, btnDelete, btnFilter;
    private NhanVienController controller;

    public NhanVienPanel() {
        controller = new NhanVienController(this);
        initComponents();
        controller.loadTableData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Mã NV", "Tên", "Họ", "Tên đăng nhập", "Vị trí"});
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel panel = new JPanel(new GridLayout(8, 2));
        tfMa = new JTextField(); tfTen = new JTextField(); tfHo = new JTextField();
        tfTenDN = new JTextField(); tfViTri = new JTextField(); tfEmail = new JTextField();

        btnInsert = new JButton("Thêm");
        btnUpdate = new JButton("Cập nhật theo mã nhân viên");
        btnDelete = new JButton("Xoá");
        btnFilter = new JButton("Lọc chính xác");

        panel.add(new JLabel("Mã NV:")); panel.add(tfMa);
        panel.add(new JLabel("Tên:")); panel.add(tfTen);
        panel.add(new JLabel("Họ:")); panel.add(tfHo);
        panel.add(new JLabel("Tên đăng nhập:")); panel.add(tfTenDN);
        panel.add(new JLabel("Vị trí:")); panel.add(tfViTri);
        panel.add(new JLabel("Email:")); panel.add(tfEmail);
        panel.add(btnInsert); panel.add(btnDelete);
        panel.add(btnUpdate); panel.add(btnFilter);

        add(panel, BorderLayout.SOUTH);

        btnInsert.addActionListener(e -> controller.insertNhanVien(
                tfMa.getText(), tfTen.getText(), tfHo.getText(),
                tfTenDN.getText(), tfViTri.getText(), tfEmail.getText()
        ));

        btnUpdate.addActionListener(e -> controller.updateNhanVien(
                tfMa.getText(), tfTen.getText(), tfHo.getText(),
                tfTenDN.getText(), tfViTri.getText(), tfEmail.getText()
        ));

        btnDelete.addActionListener(e -> {
            int selected = table.getSelectedRow();
            if (selected >= 0) {
                String ma = table.getValueAt(selected, 0).toString();
                controller.deleteNhanVien(ma);
            }
        });

        btnFilter.addActionListener(e -> controller.filterNhanVien(
                tfMa.getText().trim(), tfTen.getText().trim(), tfViTri.getText().trim(), tfHo.getText().trim(), tfEmail.getText().trim()
        ));

        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                tfMa.setText(table.getValueAt(row, 0).toString());
                tfTen.setText(table.getValueAt(row, 1).toString());
                tfHo.setText(table.getValueAt(row, 2).toString());
                tfTenDN.setText(table.getValueAt(row, 3).toString());
                tfViTri.setText(table.getValueAt(row, 4).toString());
                tfEmail.setText(table.getValueAt(row, 5).toString());
            }
        });
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
}
