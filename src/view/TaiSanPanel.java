package view;

import controller.TaiSanController;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TaiSanPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Mã TS", "Tên tài sản", "Serial", "Danh mục", "Mã NV", "NSX", "NCC", "Giá mua", "Tình trạng"}, 0
    );
    private final JTable table = new JTable(tableModel);

    private final JTextField tfMaTaiSan = new JTextField(18);
    private final JTextField tfTenTaiSan = new JTextField(18);
    private final JTextField tfSoSerial = new JTextField(18);
    private final JTextField tfDanhMuc = new JTextField(18);
    private final JComboBox<String> cbMaNhanVien = new JComboBox<>(new String[]{"<Tất cả>"});
    private final JTextField tfNhaSanXuat = new JTextField(18);
    private final JTextField tfNhaCungCap = new JTextField(18);
    private final JTextField tfGiaMua = new JTextField(18);
    private final JComboBox<String> cbTinhTrang = new JComboBox<>(new String[]{"<Tất cả>", "Mới", "Đang sử dụng", "Bảo trì", "Thanh lý"});

    private final JButton btnLamMoi = new JButton("Làm mới");
    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnCapNhat = new JButton("Cập nhật theo mã");
    private final JButton btnLoc = new JButton("Lọc");
    private final JButton btnXuatCSV = new JButton("Xuất CSV");
    private final JButton btnNhapCSV = new JButton("Nhập CSV");

    private final JTextField tfKeyword = new JTextField(18);

    private TaiSanController controller;

    public TaiSanPanel() {
        setLayout(new BorderLayout(6, 6));

        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel south = new JPanel(new BorderLayout(6, 6));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4, 6, 4, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0;
        gc.gridx = 0;

        int r = 0;
        addRow(form, gc, r++, new JLabel("Mã TS:"), tfMaTaiSan);
        addRow(form, gc, r++, new JLabel("Tên tài sản:"), tfTenTaiSan);
        addRow(form, gc, r++, new JLabel("Serial:"), tfSoSerial);
        addRow(form, gc, r++, new JLabel("Danh mục:"), tfDanhMuc);
        addRow(form, gc, r++, new JLabel("Mã NV:"), cbMaNhanVien);
        addRow(form, gc, r++, new JLabel("NSX:"), tfNhaSanXuat);
        addRow(form, gc, r++, new JLabel("NCC:"), tfNhaCungCap);
        addRow(form, gc, r++, new JLabel("Giá mua:"), tfGiaMua);
        addRow(form, gc, r++, new JLabel("Tình trạng:"), cbTinhTrang);

        south.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(3, 2, 6, 6));
        buttons.add(btnLamMoi);
        buttons.add(btnThem);
        buttons.add(btnXoa);
        buttons.add(btnCapNhat);
        buttons.add(btnLoc);
        buttons.add(btnXuatCSV);
        buttons.add(btnNhapCSV);

        south.add(buttons, BorderLayout.SOUTH);

        add(south, BorderLayout.SOUTH);

        controller = new TaiSanController(this);
        controller.loadComboboxes();
        controller.loadTableData();

        btnLamMoi.addActionListener(e -> {
            controller.clearForm();
            controller.loadTableData();
        });
        btnThem.addActionListener(e -> controller.insertOrUpdate(false));
        btnCapNhat.addActionListener(e -> controller.insertOrUpdate(true));
        btnXoa.addActionListener(e -> controller.deleteSelected());
        btnLoc.addActionListener(e -> controller.filter());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.onTableClickFillForm();
            }
        });
    }

    private void addRow(JPanel p, GridBagConstraints gc, int row, JComponent label, JComponent field) {
        gc.gridy = row;
        gc.gridx = 0;
        gc.weightx = 0;
        p.add(label, gc);
        gc.gridx = 1;
        gc.weightx = 1;
        p.add(field, gc);
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTable getTable() {
        return table;
    }

    public JTextField getTfMaTaiSan() {
        return tfMaTaiSan;
    }

    public JTextField getTfTenTaiSan() {
        return tfTenTaiSan;
    }

    public JTextField getTfSoSerial() {
        return tfSoSerial;
    }

    public JTextField getTfDanhMuc() {
        return tfDanhMuc;
    }

    public JComboBox<String> getCbMaNhanVien() {
        return cbMaNhanVien;
    }

    public JTextField getTfNhaSanXuat() {
        return tfNhaSanXuat;
    }

    public JTextField getTfNhaCungCap() {
        return tfNhaCungCap;
    }

    public JTextField getTfGiaMua() {
        return tfGiaMua;
    }

    public JComboBox<String> getCbTinhTrang() {
        return cbTinhTrang;
    }

    public JTextField getTfKeyword() {
        return tfKeyword;
    }
}
