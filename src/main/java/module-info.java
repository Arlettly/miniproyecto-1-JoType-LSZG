module com.example.fasttype {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.example.fasttype to javafx.fxml;
    opens com.example.fasttype.view to javafx.fxml;
    opens com.example.fasttype.controller to javafx.fxml;
    opens com.example.fasttype.model to javafx.fxml;

    exports com.example.fasttype;
    exports com.example.fasttype.view;
    exports com.example.fasttype.controller;
    exports com.example.fasttype.model;
}