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

        // Tạo thanh menu chứa nút "Đăng xuất"
        JMenuBar menuBar = new JMenuBar();
        JMenu heThongMenu = new JMenu("Hệ thống");
        JMenuItem logoutItem = new JMenuItem("Đăng xuất");

        logoutItem.addActionListener(e -> {
            dispose(); // Đóng MainFrame
            new LoginFrame().setVisible(true); // Quay lại màn hình đăng nhập
        });

        heThongMenu.add(logoutItem);
        menuBar.add(heThongMenu);
        setJMenuBar(menuBar); // Gắn thanh menu vào frame

        add(tabbedPane, BorderLayout.CENTER);
    }
}
