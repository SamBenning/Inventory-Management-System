module C482 {
    requires javafx.fxml;
    requires javafx.controls;

    //exports sample.Controller;
    opens sample.Controller;
    opens sample.Model;
    opens sample to javafx.graphics;
}