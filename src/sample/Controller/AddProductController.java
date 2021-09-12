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
 * Controller for addProductForm
 * <br>
 * FUTURE ENHANCEMENT: Allow user to add/modify parts directly from the product
 * screen.*/
public class AddProductController implements Initializable {
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

    private Product newProduct;

    /**
     * Sets cell value factories for both Table Views. Initializes newProduct object.*/
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

        partsTableAll.setItems(Inventory.getAllParts());
        newProduct = new Product();
        partsTableAssoc.setItems(newProduct.getAllAssociatedParts());
    }

    /**
     * Grabs and validates user-input data from text fields, and any associated parts added,
     * and generates new Product object to pass into Inventory.addProduct().
     * @param actionEvent Save button clicked.
     * <br>
     * FUTURE ENHANCEMENT: Validate that stock, min, and max are non-negative. Validate
     * string length, and check for matches in previous entries.*/
    public void generateNewProductHandler (ActionEvent actionEvent) {
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

        if (!hasException) {
            newProduct.setId(UniqueID.generateProductId());
            newProduct.setName(name);
            newProduct.setStock(stock);
            newProduct.setPrice(price);
            newProduct.setMin(min);
            newProduct.setMax(max);
            Inventory.addProduct(newProduct);
            try {
                toMainMenu(actionEvent);
            } catch (IOException e ) {
                System.out.println(e);
            }
        }
    }

    /**
     * Grabs the currently selected part from partsTableAll and adds it to the associated
     * parts list of newProduct.
     * <br>
     * FUTURE ENHANCEMENT(1): Disallow duplicate parts from being associated.
     * <br>
     * FUTURE ENHANCEMENT(2): Add a "Disassociate All Parts" Button.
     * @param actionEvent Add Part button is clicked.*/
    public void addSelectedPartHandler(ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part)partsTableAll.getSelectionModel().getSelectedItem();
            newProduct.addAssociatedPart(selectedPart);
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
                newProduct.deleteAssociatedPart((Part)partsTableAssoc.getSelectionModel().getSelectedItem());
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
