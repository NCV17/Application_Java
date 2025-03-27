module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires javafx.graphics; // css

    exports javafx.controllers;

    opens javafx.controllers to javafx.fxml;
    exports javafx;
}
