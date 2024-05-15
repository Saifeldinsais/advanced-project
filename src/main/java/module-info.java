module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.application to javafx.fxml;
    exports com.example.application;
}
