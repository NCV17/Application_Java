package javafx.controllers;

import javafx.Main;
import javafx.database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // Xử lý phím Enter và phím mũi tên
        txtUsername.setOnKeyPressed(this::handleKeyNavigation);
        txtPassword.setOnKeyPressed(this::handleKeyNavigation);
    }

    // Hàm xử lý di chuyển giữa các ô input bằng Enter hoặc phím mũi tên
    private void handleKeyNavigation(KeyEvent event) {
        if (event.getSource() == txtUsername) {
            if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.DOWN) {
                txtPassword.requestFocus(); // Chuyển xuống ô mật khẩu
            }
        } else if (event.getSource() == txtPassword) {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(); // Nhấn Enter trong ô mật khẩu để đăng nhập
            } else if (event.getCode() == KeyCode.UP) {
                txtUsername.requestFocus(); // Nhấn Up để quay lại ô username
            }
        }
    }

    private void openDashboard(String role) {
        try {
            Stage newStage = new Stage();
            Parent root;

            if ("ADMIN".equalsIgnoreCase(role)) {
                root = FXMLLoader.load(getClass().getResource("/javafx/views/admin_dashboard.fxml"));
                newStage.setTitle("Admin Dashboard");
            } else {
                root = FXMLLoader.load(getClass().getResource("/javafx/views/user_dashboard.fxml"));
                newStage.setTitle("User Dashboard");
            }

            newStage.setScene(new Scene(root, 1000, 600));
            newStage.show();

        } catch (IOException e) {
            messageLabel.setText("❌ Lỗi tải giao diện!");
            e.printStackTrace();
        }
    }

    public void handleLogin() {
        if (txtUsername.getScene() == null) {
            System.out.println("⚠ Scene chưa sẵn sàng, không thể lấy window!");
            return;
        }

        String username = txtUsername.getText();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("❌ Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");

                messageLabel.setText("✅ Đăng nhập thành công!");

                // Đóng cửa sổ đăng nhập (kiểm tra Scene trước)
                Stage loginStage = (Stage) txtUsername.getScene().getWindow();
                if (loginStage != null) {
                    loginStage.close();
                }

                // Đóng Welcome nếu cần
                if (Main.welcomeStage != null) {
                    Main.welcomeStage.close();
                }

                // Mở giao diện Dashboard
                openDashboard(role);

            } else {
                messageLabel.setText("❌ Sai tài khoản hoặc mật khẩu!");
            }
        } catch (SQLException e) {
            messageLabel.setText("❌ Lỗi kết nối cơ sở dữ liệu!");
            e.printStackTrace();
        }
    }



    public void login(String username, String password) {
        txtUsername.setText(username);
        txtPassword.setText(password);
        handleLogin(); // Gọi luôn hàm đăng nhập
    }

    @FXML
    private void switchToRegister(ActionEvent event) {
        try {
            // Tải giao diện đăng ký từ file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/register.fxml"));
            Parent root = loader.load();

            // Lấy stage hiện tại và thay đổi scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
