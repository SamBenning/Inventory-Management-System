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
import sample.Controller.AddPartController.*;
import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.OutsourcedPart;
import sample.Model.Part;

import static sample.Controller.fieldValidationUtil.*;
import static sample.Controller.fieldValidationUtil.parseMax;


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

    public ModifyPartController(Part selectedPart) {
        this.selectedPart = selectedPart;
    }

    public void modifyPart(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean hasException = false;
        boolean inHouseIsSelected;

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
            inHouseIsSelected = true;
        } else {
            companyName = fieldValidationUtil.parseCompanyName(variableTextField, companyName, errorLog);
            inHouseIsSelected = false;
        }

        fieldValidationUtil.validateLogic(stock, min, max, errorLog);

        if (!errorLog.getChildren().isEmpty()) {
            hasException = true;
        }

        if (!hasException) {

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

    public void setSelectedPart(Part selectedPart) {
        System.out.println("setSelectedPart called.");
        this.selectedPart = selectedPart;
    }
    //Checks whether selected part is inhouse or outsourced based on inInHouse member varaible of Part abstract class
    //and sets the variable text field accordingly.
    public void setInitialVariableField() {
        if(selectedPart.isInHouse()) {
            //System.out.println("selectedPart.isInHouse evaluated true");
            variableLabel.setText("Machine ID");
            InHousePart selectedInHousePart = (InHousePart) selectedPart;
            String machineIdText = Integer.toString(selectedInHousePart.getMachineId());
            variableTextField.setText(machineIdText);
        } else {
            //System.out.println("selectedPart.isInHouse evaluated" + selectedPart.isInHouse());
            variableLabel.setText("Company Name");
            OutsourcedPart selectedOutsourcedPart = (OutsourcedPart) selectedPart;
            String companyNameText = selectedOutsourcedPart.getCompanyName();
            variableTextField.setText(companyNameText);
        }
    }

    public void setInitialToggle() {
        if(selectedPart.isInHouse()) {
            //System.out.println("selectedPart.isInHouse evaluated true");
            group.selectToggle(inhouseRadioButton);
        } else {
            //System.out.println("selectedPart.isInHouse evaluated" + selectedPart.isInHouse());
            group.selectToggle(outsourcedRadioButton);
        }
    }

    public void setVariableField() {

        if (group.getSelectedToggle() == inhouseRadioButton) {
            variableLabel.setText("Machine ID");
        } else {
            variableLabel.setText("Company Name");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println("mod part init ran");
        idTextField.setText(Integer.toString(selectedPart.getId()));
        nameTextField.setText(selectedPart.getName());
        inventoryTextField.setText(Integer.toString(selectedPart.getStock()));
        priceTextField.setText(Double.toString(selectedPart.getPrice()));
        minTextField.setText(Integer.toString(selectedPart.getMin()));
        maxTextField.setText(Integer.toString(selectedPart.getMax()));
        setInitialVariableField();
        setInitialToggle();
    }

    public void changePartSource(ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        variableTextField.clear();
        setVariableField();
    }

    public void generateNewPart () {
        System.out.println(selectedPart.getName());
    }

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

    public void toMainMenu(ActionEvent actionEvent, Part part) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/sample/View/mainForm.fxml"));

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
