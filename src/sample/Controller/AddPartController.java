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
import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.OutsourcedPart;
import sample.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.fieldValidationUtil.*;


public class AddPartController implements Initializable {

    public TableView partsTable;
    public RadioButton inhouseRadioButton;
    public RadioButton outsourcedRadioButton;
    public VBox errorLog;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField minTextField;
    public TextField maxTextField;
    public ToggleGroup group;
    public Label variableLabel;
    public TextField variableTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        group.selectToggle(inhouseRadioButton);
        setVariableField();
    }
    
    public void generateNewPartHandler(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean hasException = false;
        boolean isInHouse;
        String name = "";
        int stock = -1;
        double price = -1;
        int min = -1;
        int max = -1;
        int machineId = -1;
        String companyName = "";

        name = fieldValidationUtil.parseName(nameTextField, name, errorLog);
        stock = parseStock(inventoryTextField, stock, errorLog);
        price = parsePrice(priceTextField, price, errorLog);
        min = parseMin(minTextField, min, errorLog);
        max = parseMax(maxTextField, max, errorLog);

        if (group.getSelectedToggle() == inhouseRadioButton) {
            machineId = fieldValidationUtil.parseMachineId(variableTextField, machineId, errorLog);
            isInHouse = true;
        } else {
            companyName = fieldValidationUtil.parseCompanyName(variableTextField, companyName, errorLog);
            isInHouse = false;
        }

        fieldValidationUtil.validateLogic(stock, min, max, errorLog);

        if (!errorLog.getChildren().isEmpty()) {
            hasException = true;
        }

        if (!hasException) {
            Part newPart;
            if (isInHouse) {
                newPart = new InHousePart(name, price, stock, min, max, machineId);
            } else {
                newPart = new OutsourcedPart(name,price,stock,min,max,companyName);
            }
            try {
                toMainMenu(actionEvent, newPart);
            } catch (IOException e) {
                System.out.println("damn");
            }
        }
    }

    public void changePartSourceHandler(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        variableTextField.clear();
        setVariableField();
    }

    public void setVariableField() {
        if (group.getSelectedToggle() == inhouseRadioButton) {
            variableLabel.setText("Machine ID");
        } else {
            variableLabel.setText("Company Name");
        }
    }

    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/mainForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    public void toMainMenu(ActionEvent actionEvent, Part part) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/mainForm.fxml"));
        Parent root = loader.load();
        MainController mc = loader.getController();
        mc.addPartFromForm(part);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
