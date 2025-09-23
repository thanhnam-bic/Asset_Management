package view;

import controller.TaiSanController;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class TaiSanPanel extends JPanel {

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"Mã TS", "Tên tài sản", "Serial", "Danh mục", "Mã NV", "NSX", "NCC", "Giá mua", "Tình trạng"}, 0
    );
    private final JTable table = new JTable(tableModel);

    private final JTextField tfMaTaiSan = new JTextField(18);
    private final JTextField tfTenTaiSan = new JTextField(18);
    private final JTextField tfSoSerial = new JTextField(18);
    private final JTextField tfGiaMua = new JTextField(18);
    private final JComboBox<String> cbDanhMuc = new JComboBox<>(new String[]{"-- Không chọn --"});
    private final JComboBox<String> cbMaNhanVien = new JComboBox<>(new String[]{"-- Không chọn --"});
    private final JComboBox<String> cbNhaSanXuat = new JComboBox<>(new String[]{"-- Không chọn --"});
    private final JComboBox<String> cbNhaCungCap = new JComboBox<>(new String[]{"-- Không chọn --"});
    private final JComboBox<String> cbTinhTrang = new JComboBox<>(new String[]{"-- Không chọn --", "Mới", "Đang sử dụng", "Bảo trì", "Thanh lý"});

    private final JButton btnLamMoi = new JButton("Làm mới");
    private final JButton btnThem = new JButton("Thêm");
    private final JButton btnXoa = new JButton("Xóa");
    private final JButton btnCapNhat = new JButton("Cập nhật theo mã");
    private final JButton btnLoc = new JButton("Lọc");
    private final JButton btnXuatCSV = new JButton("Xuất CSV");
    private final JButton btnNhapCSV = new JButton("Nhập CSV");

    private final JTextField tfKeyword = new JTextField(18);

    private final TaiSanController controller;

    public TaiSanPanel() {
        controller = new TaiSanController(this);

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
        addRow(form, gc, r++, new JLabel("Giá mua:"), tfGiaMua);
        addRow(form, gc, r++, new JLabel("Danh mục:"), cbDanhMuc);
        addRow(form, gc, r++, new JLabel("Mã NV:"), cbMaNhanVien);
        addRow(form, gc, r++, new JLabel("NSX:"), cbNhaSanXuat);
        addRow(form, gc, r++, new JLabel("NCC:"), cbNhaCungCap);
        addRow(form, gc, r++, new JLabel("Tình trạng:"), cbTinhTrang);

        south.add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(4, 2, 6, 6));
        buttons.add(btnLamMoi);
        buttons.add(btnThem);
        buttons.add(btnXoa);
        buttons.add(btnCapNhat);
        buttons.add(btnLoc);
        buttons.add(btnXuatCSV);
        buttons.add(btnNhapCSV);
        south.add(buttons, BorderLayout.SOUTH);

        add(south, BorderLayout.SOUTH);
        btnLamMoi.addActionListener(e -> {
            controller.clearForm();
            controller.loadTableData();
        });
        btnThem.addActionListener(e -> controller.insertOrUpdate(false));
        btnCapNhat.addActionListener(e -> controller.insertOrUpdate(true));
        btnXoa.addActionListener(e -> controller.deleteSelected());
        btnLoc.addActionListener(e -> controller.filter());
        btnXuatCSV.addActionListener(e -> controller.xuatCSV());
        btnNhapCSV.addActionListener(e -> controller.nhapCSV());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                controller.onTableClickFillForm();
            }
        });

        controller.loadComboboxes();
        controller.loadTableData();
        TableColumn priceCol = table.getColumnModel().getColumn(7);
        DefaultTableCellRenderer currencyRenderer = new DefaultTableCellRenderer() {
            private final DecimalFormat fmt = new DecimalFormat("#,##0 'VND'",
                    new DecimalFormatSymbols(Locale.US));

            @Override
            public void setValue(Object value) {
                if (value instanceof Number) {
                    super.setValue(fmt.format(((Number) value).doubleValue()));
                } else if (value != null) {
                    try {
                        double d = Double.parseDouble(value.toString().trim().replace(",", ""));
                        super.setValue(fmt.format(d));
                    } catch (NumberFormatException e) {
                        super.setValue(value);
                    }
                } else {
                    super.setValue("");
                }
            }
        };
        currencyRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        priceCol.setCellRenderer(currencyRenderer);
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

    public JTextField getTfGiaMua() {
        return tfGiaMua;
    }

    public JTextField getTfKeyword() {
        return tfKeyword;
    }

    public JComboBox<String> getCbDanhMuc() {
        return cbDanhMuc;
    }

    public JComboBox<String> getCbMaNhanVien() {
        return cbMaNhanVien;
    }

    public JComboBox<String> getCbNhaSanXuat() {
        return cbNhaSanXuat;
    }

    public JComboBox<String> getCbNhaCungCap() {
        return cbNhaCungCap;
    }

    public JComboBox<String> getCbTinhTrang() {
        return cbTinhTrang;
    }
}
