package javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button myCoursesButton, paymentHistoryButton, profileButton, logoutButton;

    @FXML
    private VBox contentArea;

    public void initialize() {
        welcomeLabel.setText("Chào mừng bạn đến với User Dashboard!");
    }

    @FXML
    private void showMyCourses() {
        loadView("/javafx/views/my_courses.fxml");
    }

    @FXML
    private void showPaymentHistory() {
        loadView("/javafx/views/payment_history.fxml");
    }

    @FXML
    private void showProfile() {
        loadView("/javafx/views/profile.fxml");
    }

    private void loadView(String fxmlPath) {
        try {
            VBox view = FXMLLoader.load(getClass().getResource(fxmlPath));
            contentArea.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận đăng xuất");
        alert.setHeaderText("Bạn có chắc chắn muốn đăng xuất?");
       // alert.setContentText("Nhấn 'Có' để đăng xuất, 'Không' để hủy.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Chuyển về giao diện đăng nhập
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/login.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
