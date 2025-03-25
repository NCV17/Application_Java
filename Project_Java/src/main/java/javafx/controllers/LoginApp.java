package javafx.controllers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.net.URL;

public class LoginApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tải FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/welcome.fxml"));
        AnchorPane root = loader.load();

        // Kiểm tra xem file CSS có được tìm thấy không
        URL cssUrl = getClass().getResource("/javafx/views/styles.css");
        if (cssUrl != null) {
            System.out.println("CSS file found: " + cssUrl);
        } else {
            System.out.println("CSS file not found!");
        }

        // Tạo scene và thêm file CSS vào scene nếu file CSS tồn tại
        Scene scene = new Scene(root);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        }

        // Hiển thị cửa sổ
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
