module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
//    requires de.jensd.fx.glyphs.fontawesome;

    opens com.example.OOP_Project to javafx.fxml;
    opens com.example.OOP_Project.Controller to javafx.fxml;
    exports com.example.OOP_Project;
    exports com.example.OOP_Project.Controller;
    exports com.example.OOP_Project.Test;

}