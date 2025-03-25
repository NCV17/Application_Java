package javafx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button myCoursesButton;

    @FXML
    private Button paymentHistoryButton;

    @FXML
    private Button profileButton;

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
}
