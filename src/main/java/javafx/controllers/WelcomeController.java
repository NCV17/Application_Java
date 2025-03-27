package javafx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality; // Thêm Modality

import java.io.IOException;

public class WelcomeController {

    @FXML
    public void switchToRegister(ActionEvent event) { // Khi nhấn nút đăng ký
        openPopup(event, "/javafx/views/register.fxml");
    }

    @FXML
    public void switchToLogin(ActionEvent event) { // Khi nhấn nút đăng nhập
        openPopup(event, "/javafx/views/login.fxml");
    }

    private void openPopup(ActionEvent event, String fxmlPath) {
        try {
            // Tải FXML cho cửa sổ popup
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Tạo một cửa sổ mới (Stage)
            Stage popupStage = new Stage();
            popupStage.setTitle("Login/Register");

            // Đặt Scene cho cửa sổ popup
            Scene scene = new Scene(root);
            popupStage.setScene(scene);

            // Đảm bảo rằng cửa sổ con là cửa sổ chính (Modal)
            popupStage.initModality(Modality.APPLICATION_MODAL);

            // Hiển thị cửa sổ
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
