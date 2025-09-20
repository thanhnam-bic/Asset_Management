package view;

import java.awt.*;
import javax.swing.*;

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
        // Thêm panel cho bảng ViTri
        tabbedPane.addTab("Nhà Cung Cấp", new NhaCungCapPanel());
        // Tạo thanh menu
        JMenuBar menuBar = new JMenuBar();
        JMenu heThongMenu = new JMenu("⚙ Hệ thống");
        heThongMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem logoutItem = new JMenuItem("🔓 Đăng xuất");
        logoutItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutItem.setBackground(new Color(255, 255, 255));

        // Xác nhận khi đăng xuất
        logoutItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận đăng xuất",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Đóng MainFrame
                new LoginFrame().setVisible(true); // Quay lại màn hình đăng nhập
            }
        });

        heThongMenu.add(logoutItem);
        menuBar.add(heThongMenu);
        setJMenuBar(menuBar); // Gắn thanh menu vào frame

        add(tabbedPane, BorderLayout.CENTER);
    }
}
