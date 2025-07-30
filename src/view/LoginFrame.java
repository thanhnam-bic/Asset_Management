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
        setTitle("Đăng nhập");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tfUsername = new JTextField();
        pfPassword = new JPasswordField();
        btnLogin = new JButton("Đăng nhập");

        JPanel panel = new JPanel(new GridLayout(5, 4));
        panel.add(new JLabel("Tài khoản:")); panel.add(tfUsername);
        panel.add(new JLabel("Mật khẩu:")); panel.add(pfPassword);
        panel.add(new JLabel()); panel.add(btnLogin);
        add(panel);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = tfUsername.getText().trim();
        String password = new String(pfPassword.getPassword()).trim();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM User WHERE username = ? AND password = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dispose(); // Đóng login
                new MainFrame().setVisible(true); // Mở giao diện chính
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi kết nối cơ sở dữ liệu", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
