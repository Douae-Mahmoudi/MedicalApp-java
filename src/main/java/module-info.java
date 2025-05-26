module com.example.medicalapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // pour JDBC/MySQL

    // Packages accessibles au loader FXML
    opens controller to javafx.fxml;
    // Package contenant Patient et DAO, accessible aussi à PropertyValueFactory (javafx.base)
    opens module to javafx.fxml, javafx.base;



    // Exports si besoin d'accès depuis d'autres modules
    exports com.example.medicalapp;
    exports controller;
    exports module;
}