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
import sample.Model.Part;


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
        boolean isInHouse;

    }

    public void setSelectedPart(Part selectedPart) {
        System.out.println("setSelectedPart called.");
        this.selectedPart = selectedPart;
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
    }

    public void changePartSource () {

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



}
