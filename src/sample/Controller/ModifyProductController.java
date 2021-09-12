package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for modifyProductForm*/
public class ModifyProductController implements Initializable {

    private Product selectedProduct;
    public VBox errorLog;
    public TextField idTextField;
    public TextField nameTextField;
    public TextField inventoryTextField;
    public TextField priceTextField;
    public TextField minTextField;
    public TextField maxTextField;
    public TableView partsTableAll;
    public TableColumn partIdColAll;
    public TableColumn partNameColAll;
    public TableColumn partInventoryColAll;
    public TableColumn partUnitCostColAll;
    public TableView partsTableAssoc;
    public TableColumn partIdColAssoc;
    public TableColumn partNameColAssoc;
    public TableColumn partInventoryColAssoc;
    public TableColumn partUnitCostColAssoc;


    /**
     *Constructor is used to pass the selected part from MainController to this controller.
     * @param selectedProduct The selected Product object from the Product TableView in MainController.*/
    public ModifyProductController(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    /**
     * Sets cell value factories for both Table Views. Reads data from the Product object
     * that was passed in and fills the appropriate text fields. with the data.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdColAll.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColAll.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColAll.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partUnitCostColAll.setCellValueFactory(new PropertyValueFactory<>("price"));
        partIdColAssoc.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColAssoc.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColAssoc.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partUnitCostColAssoc.setCellValueFactory(new PropertyValueFactory<>("price"));
        idTextField.setText(Integer.toString(selectedProduct.getId()));
        nameTextField.setText(selectedProduct.getName());
        inventoryTextField.setText(Integer.toString(selectedProduct.getStock()));
        priceTextField.setText(Double.toString(selectedProduct.getPrice()));
        minTextField.setText(Integer.toString(selectedProduct.getMin()));
        maxTextField.setText(Integer.toString(selectedProduct.getMax()));
        partsTableAll.setItems(Inventory.getAllParts());
        partsTableAssoc.setItems(selectedProduct.getAllAssociatedParts());

    }

    /**
     * Validates data in text fields. Adds errorMessage to errorLog if problem found. If all data
     * validates, creates a new Product object with the new data read from the text fields.
     * and associated parts TableView.
     * Preserves ID of existing Product by reading it from selectedProduct. Updated Product is passed into overloaded
     * toMainMenu method.
     * <br>
     * RUNTIME ERROR: IDs were not being preserved. After clicking the save button, the product would be
     * assigned a new ID. This is due the Product class constructor calling generateProductID from
     * UniqueID class.
     * <br>
     * SOLUTION: The method first instantiates a Product object using default
     * constructor, which does not call generatePartId. Then, setter methods are used to manually
     * assign plug in values. ID is assigned from the selectedProduct object to preserve original ID.
     * @param actionEvent Save button is clicked.*/
    public void modifyProductHandler (ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean hasException = false;

        String name = "";
        int stock = -1;
        double price = -1;
        int min = -1;
        int max = -1;

        name = FieldValidationUtil.parseName(nameTextField, errorLog);
        stock = FieldValidationUtil.parseStock(inventoryTextField, errorLog);
        price = FieldValidationUtil.parsePrice(priceTextField, errorLog);
        min = FieldValidationUtil.parseMin(minTextField, errorLog);
        max = FieldValidationUtil.parseMax(maxTextField, errorLog);

        FieldValidationUtil.validateLogic(stock, min, max, errorLog);

        if (!errorLog.getChildren().isEmpty()) {
            hasException = true;
        }

        if(!hasException) {
            int index = Inventory.getAllProducts().indexOf(selectedProduct);
            Product updatedProduct = new Product();
            updatedProduct.setId(selectedProduct.getId());
            updatedProduct.setName(name);
            updatedProduct.setStock(stock);
            updatedProduct.setPrice(price);
            updatedProduct.setMin(min);
            updatedProduct.setMax(max);
            updatedProduct.setAssociatedParts(partsTableAssoc.getItems());
            Inventory.updateProduct(index, updatedProduct);
            try {
                toMainMenu(actionEvent);
            } catch (IOException e) {
                System.out.println("damn");
            }
        }
    }

    /**
     * Gets selected Part from partsTableAll and adds it to partsTableAssoc.
     * @param actionEvent Add Part button is clicked.*/
    public void addSelectedPartHandler(ActionEvent actionEvent) {

        try {
            Part selectedPart = (Part)partsTableAll.getSelectionModel().getSelectedItem();
            selectedProduct.addAssociatedPart(selectedPart);
        } catch (Exception e) {
            System.out.println("There was a problem associating part. Did you select a part?");
            System.out.println(e);
        }
    }

    /**
     * Grabs the currently selected part from partsTableAssoc. Throws an alert to get
     * confirmation from user to disassociate part. If user selects YES, part is removed
     * from newProduct associate parts list.
     * @param actionEvent Remove Associated Part button is clicked.
     * <br>
     * FUTURE ENHANCEMENT: Add a checkbox to allow user to disable confirmation dialog box.*/
    public void removeAssocPartHandler(ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part)partsTableAssoc.getSelectionModel().getSelectedItem();
            String alertString = "Are you sure you wish to disassociate " + selectedPart.getName() +
                    " from this product?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertString, ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Disassociate Part Confirmation");
            alert.setHeaderText("Disassociate Part");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                selectedProduct.deleteAssociatedPart((Part)partsTableAssoc.getSelectionModel().getSelectedItem());
            }
        } catch (Exception e) {
            System.out.println("Problem removing associated part.");
        }
    }

    /**
     * Changes scene to the main menu.
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
