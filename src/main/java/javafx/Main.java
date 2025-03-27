package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;

        try {
            // Load giao diện đăng nhập từ login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx/views/login.fxml"));
            BorderPane root = loader.load();

            // Tạo Scene
            Scene scene = new Scene(root);

            // Cấu hình cửa sổ
            primaryStage.setTitle("Đăng nhập");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
