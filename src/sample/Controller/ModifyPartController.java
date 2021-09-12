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

import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.OutsourcedPart;
import sample.Model.Part;

/**
 * Controller for modifyPartForm*/
public class ModifyPartController implements Initializable {

    public TableView partsTable;
    public TableView productsTable;
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
    private Part selectedPart;

    /**
     *Constructor is used to pass the selected part from MainController to this controller.
     * @param selectedPart The selected Part object from the Part TableView in MainController.*/
    public ModifyPartController(Part selectedPart) {
        this.selectedPart = selectedPart;
        //String selectedClassName = (String)selectedPart.getClass().getSimpleName();
    }

    /**
     * Reads data from the Part object that was passed in and fills the appropriate text fields.
     * with the data. Also sets initial radio button appropriately.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTextField.setText(Integer.toString(selectedPart.getId()));
        nameTextField.setText(selectedPart.getName());
        inventoryTextField.setText(Integer.toString(selectedPart.getStock()));
        priceTextField.setText(Double.toString(selectedPart.getPrice()));
        minTextField.setText(Integer.toString(selectedPart.getMin()));
        maxTextField.setText(Integer.toString(selectedPart.getMax()));
        setInitialVariableField();
        setInitialToggle();
    }


    /**
     * Validates data in text fields. Adds errorMessage to errorLog if problem found. If all data
     * validates, creates a new Part object with the new data read from the text fields. Preserves
     * ID of existing part by reading it from selectedPart. Updated Part is passed into overloaded
     * toMainMenu method.
     * <br>
     * RUNTIME ERROR: IDs were not being preserved. After clicking the save button, the part would be
     * assigned a new ID. This is due to the fact that both InHousePart and OutsourcedPart class
     * constructors call generatePartId from UniqueID class.
     * <br>
     * SOLUTION: The method first instantiates a Part object of appropriate subclass using default
     * constructor, which does not call generatePartId. Then, setter methods are used to manually
     * assign plug in values. ID is assigned from the selectedPart object to preserve original ID.
     * @param actionEvent Save button is clicked.*/
    public void modifyPartHandler(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean hasException = false;
        boolean inHouseIsSelected;
        //Variable initialization to be used for Part generation.
        String name = "";
        int stock = -1;
        double price = -1;
        int min = -1;
        int max = -1;
        int machineId = -1;
        String companyName = "";
        //Parse & validate field entries and assign them to appropriate variable.
        //The parse functions print to errorLog if no value is entered.
        name = FieldValidationUtil.parseName(nameTextField, errorLog);
        stock = FieldValidationUtil.parseStock(inventoryTextField, errorLog);
        price = FieldValidationUtil.parsePrice(priceTextField, errorLog);
        min = FieldValidationUtil.parseMin(minTextField, errorLog);
        max = FieldValidationUtil.parseMax(maxTextField, errorLog);

        //Call appropriate parse function depending on selected radio button.
        if (group.getSelectedToggle() == inhouseRadioButton) {
            machineId = FieldValidationUtil.parseMachineId(variableTextField, errorLog);
            inHouseIsSelected = true;
        } else {
            companyName = FieldValidationUtil.parseCompanyName(variableTextField, errorLog);
            inHouseIsSelected = false;
        }
        //validateLogic verifies that Integer field values make sense. Prints to errorLog if
        //problem found
        FieldValidationUtil.validateLogic(stock, min, max, errorLog);
        //Determine whether any exceptions have been found by checking whether errorLog is empty.
        if (!errorLog.getChildren().isEmpty()) {
            hasException = true;
        }

        if (!hasException) {
            /*generate new part using default constructor and set all member variables using setters to avoid
            * incrementing static count variable in UniqueID class.*/
            int index = Inventory.getAllParts().indexOf(selectedPart);
            if (inHouseIsSelected) {
                InHousePart updatedPart = new InHousePart();
                updatedPart.setId(selectedPart.getId());
                updatedPart.setName(name);
                updatedPart.setStock(stock);
                updatedPart.setPrice(price);
                updatedPart.setMin(min);
                updatedPart.setMax(max);
                updatedPart.setMachineId(machineId);
                Inventory.updatePart(index, updatedPart);
            } else {
                OutsourcedPart updatedPart = new OutsourcedPart();
                updatedPart.setId(selectedPart.getId());
                updatedPart.setName(name);
                updatedPart.setStock(stock);
                updatedPart.setPrice(price);
                updatedPart.setMin(min);
                updatedPart.setMax(max);
                updatedPart.setCompanyName(companyName);
                Inventory.updatePart(index, updatedPart);
            }
            try {
                toMainMenu(actionEvent);
            } catch (IOException e) {
                System.out.println("damn");
            }
        }
    }

    /**
     *Handles changing over the variable field when radio button is clicked.
     * @param actionEvent Radio button is clicked.*/
    public void changePartSourceHandler(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        variableTextField.clear();
        setVariableField();
    }

    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;
    }

    /**
     * Checks the subclass of the passed in Part object and sets the variable field when
     * form is first opened.*/
    private void setInitialVariableField() {
        if(selectedPart.getClass() == InHousePart.class) {
            variableLabel.setText("Machine ID");
            InHousePart selectedInHousePart = (InHousePart) selectedPart;
            String machineIdText = Integer.toString(selectedInHousePart.getMachineId());
            variableTextField.setText(machineIdText);
        } else {
            variableLabel.setText("Company Name");
            OutsourcedPart selectedOutsourcedPart = (OutsourcedPart) selectedPart;
            String companyNameText = selectedOutsourcedPart.getCompanyName();
            variableTextField.setText(companyNameText);
        }
    }

    /**
     * Checks the subclass of the passed in Part object and sets the initial radio toggle
     * when form is first opened.*/
    private void setInitialToggle() {
        if(selectedPart.getClass() == InHousePart.class) {
            System.out.println("selectedPart.getClass().getSimpleName == 'InHousePart' evaluated true");
            group.selectToggle(inhouseRadioButton);
        } else {
            group.selectToggle(outsourcedRadioButton);
        }
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
        //Parent root = FXMLLoader.load(getClass().getResource("/sample/View/mainForm.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/mainForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
