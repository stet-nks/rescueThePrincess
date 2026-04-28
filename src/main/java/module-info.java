module com.example.projectmaven {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.example.projectmaven to javafx.fxml;
    exports com.example.projectmaven;
}