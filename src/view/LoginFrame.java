package view;

import config.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JButton btnLogin;

    public LoginFrame() {
        setTitle("Đăng nhập hệ thống");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        // Font chính
        Font font = new Font("Segoe UI", Font.PLAIN, 16);

        JLabel lblTitle = new JLabel("HỆ THỐNG QUẢN LÝ TÀI SẢN", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(0, 102, 204));

        JLabel lblUser = new JLabel("Tài khoản:");
        JLabel lblPass = new JLabel("Mật khẩu:");

        tfUsername = new JTextField();
        pfPassword = new JPasswordField();
        btnLogin = new JButton("Đăng nhập");

        lblUser.setFont(font);
        lblPass.setFont(font);
        tfUsername.setFont(font);
        pfPassword.setFont(font);
        btnLogin.setFont(font);

        btnLogin.setBackground(new Color(0, 102, 204));
        btnLogin.setForeground(Color.BLACK);

        // Panel nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUser, gbc);
        gbc.gridx = 1;
        formPanel.add(tfUsername, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPass, gbc);
        gbc.gridx = 1;
        formPanel.add(pfPassword, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        gbc.insets = new Insets(15, 10, 10, 10);
        formPanel.add(btnLogin, gbc);

        // Layout chính
        setLayout(new BorderLayout(10, 10));
        add(lblTitle, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword()).trim();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM User WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dispose(); // Đóng login
                new MainFrame().setVisible(true); // Mở giao diện chính
            } else {
                JOptionPane.showMessageDialog(this,
                        "Sai tài khoản hoặc mật khẩu",
                        "Lỗi đăng nhập",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Không thể kết nối đến cơ sở dữ liệu",
                    "Lỗi hệ thống",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
