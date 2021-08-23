package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



public class AddPartController implements Initializable {

    public RadioButton inhouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public VBox errorLog;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField minTextField;
    public TextField maxTextField;


    public void generateNewPart(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean noExceptions = true;
        boolean noLogicalErrors = true;

        int id = -1;
        String name = "";
        int stock = -1;
        double price = -1;
        int min = -1;
        int max = -1;

        try {
            id = Integer.parseInt(idTextField.getText());
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("ID must be an integer.");
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }
        try {
            name = nameTextField.getText();
        } catch (Exception e) {
            Label errorMessage = new Label("Invalid name. " + e);
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }
        try {
            stock = Integer.parseInt(inventoryTextField.getText());
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Inv must be an integer.");
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }
        try {
            price = Double.parseDouble(priceTextField.getText());
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Price/Cost must be a double.");
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }
        try {
            min = Integer.parseInt(minTextField.getText());
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Min must be an integer.");
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }
        try {
            max = Integer.parseInt(maxTextField.getText());
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Max must be an integer.");
            errorLog.getChildren().add(errorMessage);
            noExceptions = false;
        }

        if (noExceptions) {

            if (stock < min) {
                Label errorMessage = new Label("Inv cannot be less than Min.");
                errorLog.getChildren().add(errorMessage);
                noLogicalErrors = false;
            }
            if (stock > max) {
                Label errorMessage = new Label("Inv cannot be greater tan Max.");
                errorLog.getChildren().add(errorMessage);
                noLogicalErrors = false;
            }
            if (min > max) {
                Label errorMessage = new Label("Min cannot be greater than Max");
                errorLog.getChildren().add(errorMessage);
                noLogicalErrors = false;
            }
        }

        if (noLogicalErrors) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ToggleGroup radioButtonGroup = new ToggleGroup();
        inhouseRadioButton.setToggleGroup(radioButtonGroup);
        outsourcedRadioButton.setToggleGroup(radioButtonGroup);



    }



    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/View/mainForm.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
