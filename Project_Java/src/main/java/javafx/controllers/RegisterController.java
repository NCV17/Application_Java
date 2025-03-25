package javafx.controllers;

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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.*;

public class RegisterController {
    @FXML
    private TextField txtUsername, txtEmail, txtPhone;

    @FXML
    private PasswordField txtPassword, txtConfirmPassword;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        // Gán sự kiện cho từng ô nhập liệu
        txtUsername.setOnKeyPressed(this::handleKeyNavigation);
        txtPassword.setOnKeyPressed(this::handleKeyNavigation);
        txtConfirmPassword.setOnKeyPressed(this::handleKeyNavigation);
        txtEmail.setOnKeyPressed(this::handleKeyNavigation);
        txtPhone.setOnKeyPressed(this::handleKeyNavigation);
    }

    // Xử lý di chuyển giữa các ô input
    private void handleKeyNavigation(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) { // Xử lý Enter để di chuyển hoặc đăng ký
            if (event.getSource() == txtUsername) {
                txtPassword.requestFocus();
            } else if (event.getSource() == txtPassword) {
                txtConfirmPassword.requestFocus();
            } else if (event.getSource() == txtConfirmPassword) {
                txtEmail.requestFocus();
            } else if (event.getSource() == txtEmail) {
                txtPhone.requestFocus();
            } else if (event.getSource() == txtPhone) {
                handleRegister(); // Chỉ ENTER tại txtPhone mới đăng ký
            }
        } else if (event.getCode() == KeyCode.DOWN) { // Xử lý mũi tên xuống để di chuyển
            if (event.getSource() == txtUsername) {
                txtPassword.requestFocus();
            } else if (event.getSource() == txtPassword) {
                txtConfirmPassword.requestFocus();
            } else if (event.getSource() == txtConfirmPassword) {
                txtEmail.requestFocus();
            } else if (event.getSource() == txtEmail) {
                txtPhone.requestFocus();
            }
            // Không cho xuống thêm khi đang ở txtPhone
        } else if (event.getCode() == KeyCode.UP) { // Xử lý mũi tên lên để di chuyển ngược lại
            if (event.getSource() == txtPhone) {
                txtEmail.requestFocus();
            } else if (event.getSource() == txtEmail) {
                txtConfirmPassword.requestFocus();
            } else if (event.getSource() == txtConfirmPassword) {
                txtPassword.requestFocus();
            } else if (event.getSource() == txtPassword) {
                txtUsername.requestFocus();
            }
        }
    }


    public void handleRegister() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();

        // Kiểm tra không để trống
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            messageLabel.setText("❌ Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        // Kiểm tra mật khẩu và xác nhận mật khẩu có khớp không
        if (!password.equals(confirmPassword)) {
            messageLabel.setText("❌ Mật khẩu không khớp!");
            return;
        }

        // Kiểm tra định dạng email hợp lệ (phải có @ và .abc)
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            messageLabel.setText("❌ Email không hợp lệ!");
            return;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!phone.matches("^(0\\d{9}|\\+84\\d{9})$")) {
            messageLabel.setText("❌ Số điện thoại phải có 10 chữ số, bắt đầu bằng 0 hoặc +84!");
            return;
        }

        // Kiểm tra độ mạnh của mật khẩu
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
            messageLabel.setText("❌ Password có ít nhất 8 ký tự, bao gồm chữ hoa/thường, số và ký tự đặc biệt!");
            return;
        }

        // Kiểm tra xem username đã tồn tại chưa
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM Users WHERE username = ?")) {

            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                messageLabel.setText("❌ Tên đăng nhập đã tồn tại!");
                return;
            }

            // Kiểm tra trùng lặp email
            PreparedStatement checkStmt2 = conn.prepareStatement("SELECT COUNT(*) FROM Users WHERE email = ?");
            checkStmt2.setString(1, email);
            ResultSet rs2 = checkStmt2.executeQuery();
            if (rs2.next() && rs2.getInt(1) > 0) {
                messageLabel.setText("❌ Email đã được sử dụng!");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("❌ Lỗi kiểm tra tài khoản!");
            return;
        }

        // Thêm user vào database
        String sql = "INSERT INTO Users (username, password, email, phone, role) VALUES (?, ?, ?, ?, 'USER')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password); // TODO: Cần mã hóa mật khẩu
            stmt.setString(3, email);
            stmt.setString(4, phone);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                messageLabel.setText("✅ Đăng ký thành công! Tự động đăng nhập...(3s)");

                // Tạo timeline đếm ngược
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1), event -> {
                            String messageText = messageLabel.getText();

                            // Kiểm tra nếu chuỗi không chứa số
                            String numberPart = messageText.replaceAll("[^0-9]", "");
                            if (numberPart.isEmpty()) {
                                System.out.println("Lỗi: Không tìm thấy số trong messageLabel!");
                                return;
                            }

                            int remainingTime = Integer.parseInt(numberPart) - 1;

                            // Cập nhật messageLabel nếu còn thời gian
                            if (remainingTime > 0) {
                                messageLabel.setText("✅ Đăng ký thành công! Tự động đăng nhập... (" + remainingTime + "s)");
                            } else {
                                System.out.println("Tự động đăng nhập...");
                                autoLogin(username, password);
                            }
                        })
                );
                timeline.setCycleCount(3);
                timeline.play();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            messageLabel.setText("❌ Đăng ký thất bại!");
        }
    }

    // ✅ Hàm tự động đăng nhập
    private void autoLogin(String username, String password) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/login.fxml"));
            Parent root = loader.load();

            // Lấy controller của login.fxml để gọi hàm đăng nhập
            LoginController loginController = loader.getController();
            loginController.login(username, password); // Gọi hàm đăng nhập

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
