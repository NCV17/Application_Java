package javafx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class AdminDashboardController {

    @FXML private Button btnDashboard, btnUserManagement, btnCourseManagement, btnPayments, btnReports;
    @FXML private AnchorPane dashboardView, userManagementView, courseManagementView, paymentsView, reportsView;

    @FXML
    public void initialize() {
        setupButtonHoverEffect(btnDashboard);
        setupButtonHoverEffect(btnUserManagement);
        setupButtonHoverEffect(btnCourseManagement);
        setupButtonHoverEffect(btnPayments);
        setupButtonHoverEffect(btnReports);
        showDashboard(); // Mặc định hiển thị Dashboard
    }

    private void setupButtonHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-background-radius: 5px;"));
    }

    @FXML private void showDashboard() { setViewVisible(dashboardView); }
    @FXML private void showUserManagement() { setViewVisible(userManagementView); }
    @FXML private void showCourseManagement() { setViewVisible(courseManagementView); }
    @FXML private void showPayments() { setViewVisible(paymentsView); }
    @FXML private void showReports() { setViewVisible(reportsView); }

    private void setViewVisible(AnchorPane view) {
        dashboardView.setVisible(false);
        userManagementView.setVisible(false);
        courseManagementView.setVisible(false);
        paymentsView.setVisible(false);
        reportsView.setVisible(false);
        view.setVisible(true);
    }
}
