package javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.controllers.LoginApp;

public class Main extends Application {
    public static Stage welcomeStage;

    @Override
    public void start(Stage stage) {
        welcomeStage = stage;
        LoginApp loginApp = new LoginApp();

        // Gọi start() của LoginApp để chạy
        try {
            loginApp.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Mở cửa sổ với kích thước tối đa mà vẫn giữ lại thanh tiêu đề của hệ điều hành
        stage.setMaximized(true);
    }
    public static void main(String[] args) {
        launch(args); // Gọi launch() trong Main để bắt đầu ứng dụng
    }
}
