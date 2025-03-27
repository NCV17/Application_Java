package javafx;

import javafx.application.Application;
import javafx.stage.Stage;

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
        //kich thuoc tuy chinh
         stage.setWidth(500);
         stage.setHeight(700);
    }
    public static void main(String[] args) {
        launch(args); // Gọi launch() trong Main để bắt đầu ứng dụng
    }
}
