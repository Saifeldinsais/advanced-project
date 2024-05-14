module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.application to javafx.fxml;
    exports com.example.application;
}
