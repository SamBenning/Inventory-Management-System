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

import static sample.Controller.FieldValidationUtil.*;

/**
 * Controller for addPartForm*/
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


    /**
     * Simply sets the initial radio button to in-house and calls the setVariableField function
     * to set the variable field label.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        group.selectToggle(inhouseRadioButton);
        setVariableField();
    }

    /**
     * Grabs the user-entered values from the appropriate fields, and validates all entries.
     * If all data is valid, generates a new Part object. Passes new Part to Inventory.addPart().
     * @param actionEvent Save button clicked.
     * <br>
     * FUTURE ENHANCEMENT: Validate that stock, min, and max are non-negative. Validate
     * string length, and check for matches in previous entries.*/
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

        name = FieldValidationUtil.parseName(nameTextField, errorLog);
        stock = parseStock(inventoryTextField, errorLog);
        price = parsePrice(priceTextField, errorLog);
        min = parseMin(minTextField, errorLog);
        max = parseMax(maxTextField, errorLog);

        if (group.getSelectedToggle() == inhouseRadioButton) {
            machineId = FieldValidationUtil.parseMachineId(variableTextField, errorLog);
            isInHouse = true;
        } else {
            companyName = FieldValidationUtil.parseCompanyName(variableTextField, errorLog);
            isInHouse = false;
        }

        FieldValidationUtil.validateLogic(stock, min, max, errorLog);

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
            Inventory.addPart(newPart);
            try {
                toMainMenu(actionEvent);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     *Handles changing over the variable field when radio button is clicked.
     * <br>
     * FUTURE ENHANCEMENT: Preserve what user types in variable text field when they switch back and forth between
     * radio buttons. If they type 12 for machine id, switch to company name, type 'abc', they should see 12 when
     * they switch back to In-House again.
     * @param actionEvent Radio button is clicked.*/
    public void changePartSourceHandler(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        setVariableField();
    }

    /**
     * Helper function that simply checks which radio button is selected and changes
     * the variable field label text accordingly.*/
    private void setVariableField() {
        if (group.getSelectedToggle() == inhouseRadioButton) {
            variableLabel.setText("Machine ID");
        } else {
            variableLabel.setText("Company Name");
        }
    }

    /**
     * Simply changes scene to main menu.
     * @param actionEvent Cancel button is clicked.*/
    public void toMainMenu(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/mainForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
