package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Quản lý tài sản");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Thêm panel cho bảng ViTri
        tabbedPane.addTab("Vị trí", new ViTriPanel());

        // Thêm panel cho bảng NhanVien
        tabbedPane.addTab("Nhân viên", new NhanVienPanel());

        // Thêm panel cho bảng DanhMuc
        tabbedPane.addTab("Danh mục", new DanhMucPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}