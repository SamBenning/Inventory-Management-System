package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

/**
 * Controller for mainForm
 * <br>
 * FUTURE ENHANCEMENT: Allow user to save and load data to and from a file. */
public class MainController implements Initializable {

    public TableView partsTable;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryCol;
    public TableColumn partUnitCostCol;
    public TableView productsTable;
    public TableColumn productIdCol;
    public TableColumn productNameCol;
    public TableColumn productInventoryCol;
    public TableColumn productUnitCostCol;
    public Button exitAppButton;
    public TextField partSearch;
    public TextField productSearch;
    public Label partDeletionLabel;
    public Label productDeletionLabel;
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    /**
     * Initializes products and parts tables to the static ObservableLists in Inventory class.
     * Sets up TableView columns so that data will display properly.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts = Inventory.getAllParts();
        allProducts = Inventory.getAllProducts();
        partsTable.setItems(allParts);
        productsTable.setItems(allProducts);
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partUnitCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productUnitCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partDeletionLabel.setText("");
        productDeletionLabel.setText("");
    }

    /**
     * Every time a key is pressed in the parts search table, performs search and returns results.
     * <br>
     * RUNTIME ERROR (1): Results would not display properly if a valid number was entered that Integer.parseInt()
     * could handle, but was not a match for any part IDs. Had to modify lookupPart by id method to throw InvalidParameterException
     * and catch it here.
     * <br>
     * RUNTIME ERROR (2): Setting custom placeholder text for tableview was causing nonsensical placeholder message
     * to display when the table was empty. For example, user searches for 'steel screw' and custom placeholder
     * is generated and displayed. User then deletes all items. Rather than expected 'No content in table' message,
     * the tableview would display 'No results matching steel screw'. SOLUTION: Added check at end of method to see
     * if the search bar is empty. If true, placeholder is reset to default message.*/
    @FXML
    private void searchPartTableHandler (KeyEvent keyEvent) {
        String input = partSearch.getText();
        try {
            int idToSearch = Integer.parseInt(input);
            Part part = Inventory.lookupPart(idToSearch);
            ObservableList<Part> parts = FXCollections.observableArrayList();
            parts.add(part);
            partsTable.setItems(parts);
        } catch (InvalidParameterException e) {
            ObservableList<Part> parts = Inventory.lookupPart(input);
            partsTable.setItems(parts);
            setPlaceholderText(partsTable, partSearch);
        } catch (NumberFormatException e) {
            ObservableList<Part> parts = Inventory.lookupPart(input);
            partsTable.setItems(parts);
            setPlaceholderText(partsTable, partSearch);
        }
        if (partSearch.getText().isEmpty()) {
            partsTable.setPlaceholder(new Label("No content in table"));
        }
    }
    /**
     * Every time a key is pressed in the products search table, performs search and returns results.
     * <br>
     * RUNTIME ERROR: Results would not display properly if a valid number was entered that Integer.parseInt()
     * could handle, but was not a match for any product IDs. Had to modify lookupProduct by id method to throw InvalidParameterException
     * and catch it here.*/
    @FXML
    private void searchProductTableHandler (KeyEvent keyEvent) {
        String input = productSearch.getText();
        try {
            int idToSearch = Integer.parseInt(input);
            Product product = Inventory.lookupProduct(idToSearch);
            ObservableList<Product> products = FXCollections.observableArrayList();
            products.add(product);
            productsTable.setItems(products);
        } catch (InvalidParameterException e) {
            ObservableList<Product> products = Inventory.lookupProduct(input);
            productsTable.setItems(products);
            setPlaceholderText(productsTable, productSearch);
        } catch (NumberFormatException e) {
            ObservableList<Product> products = Inventory.lookupProduct(input);
            productsTable.setItems(products);
            setPlaceholderText(productsTable, productSearch);
        }
        if (productSearch.getText().isEmpty()) {
            productsTable.setPlaceholder((new Label("No content in table")));
        }
    }
    /**
     * This handler simply calls stage.close() to quit the application
     * @param actionEvent Exit button clicked.
     * <br>
     * FUTURE ENHANCEMENT: Add functionality to save current part/product lists to file prior to exiting.*/
    public void exitAppButtonHandler (ActionEvent actionEvent) {
        Stage stage = (Stage) exitAppButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Deletes currently selected part.
     * <br>
     * RUNTIME ERROR(1): Initially could not get the alert dialog box to appear. Added alert.showAndWait() method
     * to correct. This method displays the dialog box until a selection is made.
     * <br>
     * RUNTIME ERROR(2): Delete function did not seem to be working correctly when attempting to delete an item
     * after using the search bar. Found that the item was in fact being deleted, but because the search handler
     * sets the table view items, the deleted item was not disappearing right away as expected (though it would
     * disappear after search handler was called again). Resolved by calling setItems method again based on a lookup from
     * the search bar. This is done because otherwise deleting an item with filtered results would cause the table
     * view to display the full list once again, minus the deleted item--not the filtered results minus the
     * deleted item as expected.
     * <br>
     * RUNTIME ERROR(3): Using search bar to filter results to a single item and deleting that items resulted in
     * misleading "No content in table" message being displayed. SOLUTION: Added setPlaceHolderText method and called it after
     * setting table view. setPlaceHolderText grabs text from search bar and generates appropriate placeholder label,
     * correcting this behavior.
     * @param actionEvent Delete button clicked in parts pane.*/
    public void deletePartHandler (ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();
            String alertString = "Are you sure you wish to delete " + selectedPart.getName() +
                    "? This action cannot be undone.";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertString,
                    ButtonType.YES, ButtonType.CANCEL );
            alert.setTitle("Delete Part Confirmation");
            alert.setHeaderText("Delete Part");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                String partName = selectedPart.getName();
                boolean success = Inventory.deletePart(selectedPart);
                partsTable.setItems(Inventory.lookupPart(partSearch.getText()));
                setPlaceholderText(partsTable, partSearch);
                if (success) {
                    partDeletionLabel.setText(partName + " was successfully deleted.");
                    partDeletionLabel.setTextFill(Color.color(0,1,0));
                } else {
                    partDeletionLabel.setText("ERROR: " + partName + " could not be deleted.");
                    partDeletionLabel.setTextFill(Color.color(1,0,0));
                }
            } else {
                partDeletionLabel.setText(selectedPart.getName() + " was not deleted.");
                partDeletionLabel.setTextFill(Color.color(1,0,0));
            }
        } catch (RuntimeException e) {
            partDeletionLabel.setText("ERROR: No part selected.");
            partDeletionLabel.setTextFill(Color.color(1,0,0));
        }

    }
    /**
     * This helper method simply throws an AssociatedPartsException if the product passed
     * has a non-empty associatedParts ObservableList.
     * @param product The product to check for associated parts.
     * @throws AssociatedPartsException Thrown if product param has non-empty associatedParts
     * ObservableList.*/
    private void checkAssociatedParts(Product product) throws AssociatedPartsException {
        if(!product.getAllAssociatedParts().isEmpty()) {
            throw new AssociatedPartsException("Product with associated parts cannot be deleted.");
        }
    }
    /**
     * Deletes currently selected product.
     * <br>
     * RUNTIME ERROR (1): Initially could not get the alert dialog box to appear. Added alert.showAndWait() method
     * to correct. This method displays the dialog box until a selection is made.
     * <br>
     * RUNTIME ERROR (2): Deletion did not appear to work correctly after using the search bar. See the write-up
     * in deletePartHandler method--solution for this was the same.
     * @param actionEvent Delete product button clicked in Products Pane.*/
    public void deleteProductHandler (ActionEvent actionEvent){
        Product selectedProduct;
        try {
            selectedProduct = (Product)productsTable.getSelectionModel().getSelectedItem();
            String alertString = "Are you sure you wish to delete " + selectedProduct.getName() +
                    "? This action cannot be undone.";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, alertString, ButtonType.YES, ButtonType.CANCEL);
            alert.setTitle("Delete Product Confirmation");
            alert.setHeaderText("Delete Product");
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                checkAssociatedParts(selectedProduct);
                String productName = selectedProduct.getName();
                boolean success = Inventory.deleteProduct(selectedProduct);
                productsTable.setItems(Inventory.lookupProduct(productSearch.getText()));
                setPlaceholderText(productsTable, productSearch);
                if (success) {
                    productDeletionLabel.setText(productName + " was successfully deleted.");
                    productDeletionLabel.setTextFill(Color.color(0,1,0));
                } else {
                    productDeletionLabel.setText("ERROR: " + productName + " could not be deleted.");
                    productDeletionLabel.setTextFill(Color.color(1,0,0));
                }
            } else {
                productDeletionLabel.setText(selectedProduct.getName() + " was not deleted.");
                productDeletionLabel.setTextFill(Color.color(1,0,0));
            }
        } catch (AssociatedPartsException e) {
            String alertString = "This product has associated parts. " +
                    "Deletion only allowed on products with no associated parts.";
            Alert alert = new Alert(Alert.AlertType.ERROR, alertString, ButtonType.OK);
            alert.setHeaderText("Deletion Prohibited");
            alert.showAndWait();
            productDeletionLabel.setText("ERROR: Deletion of product with associated parts prohibited.");
            productDeletionLabel.setTextFill(Color.color(1,0,0));
            System.out.println("Cannot delete item with associated parts.");
        } catch (RuntimeException e) {
            productDeletionLabel.setText("ERROR: No product selected.");
            productDeletionLabel.setTextFill(Color.color(1,0,0));
        }
    }
    /*** Method that is called from AddPartController to add new part to allParts.
     * @param part The new part to be added.*/
    public void addPartFromForm(Part part) {
        allParts.add(part);
    }
    /** Method that is called from AddProductController to add new product to allProducts.
     * @param product The new product to be added.*/
    public void addProductFromForm(Product product) {
        allProducts.add(product);
    }

    /**
     * Checks whether searchBar is empty or not. If empty, placeholder is set to default message. If not empty,
     * custom message is generated for placeholder.
     * @param table The table whose placeholder text is to be set.
     * @param searchBar The search bar from which text is read.*/
    private void setPlaceholderText(TableView table, TextField searchBar) {
        String text = searchBar.getText();
        if (text.isEmpty()) {
            table.setPlaceholder(new Label("No content in table."));
        } else {
            String message = "No results matching '" + text + "'";
            table.setPlaceholder(new Label(message));
        }
    }
    /*** Changes scene to the AddPartForm
     * @throws IOException JavaFX requirement.
     * @param actionEvent Add button clicked in Part Pane.*/
    public void toAddPartForm (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/View/addPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Changes scene to ModifyPartForm.
     * <br>
     * RUNTIME ERROR: Data was not being passed from the selected item in the Parts TableView to the
     * ModifyPartForm to populate text fields. Solution: remove fx:controller declaration from root element in modifyPartForm.fxml
     * and instead instantiate ModifyPartController class first so that constructor can be called to pass in Part object.
     * Subsequently, the setController method is called to associate the controller and fxml file.
     * @throws IOException JavaFX requirement.
     * @param actionEvent Modify button clicked in Part Pane.*/
    public void toModifyPartForm (ActionEvent actionEvent) throws IOException {
        try {
            Part part = (Part) partsTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/modifyPartForm.fxml"));

            //Creates new controller so that constructor can be called to pass in selected part
            ModifyPartController modifyPartController = new ModifyPartController(part);
            //Sets the controller since fx:controller cannot be defined in modifyPartForm.fxml w/ this method.
            loader.setController(modifyPartController);
            Parent root = loader.load();

            //modifyPartController.setSelectedPart(part);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            partDeletionLabel.setText("ERROR: No part selected.");
            partDeletionLabel.setTextFill(Color.color(1,0,0));
        }
    }
    /**Changes scene to the AddProductForm.
     * @throws IOException JavaFX requirement.
     * @param actionEvent Add button clicked in Product Pane.*/
    public void toAddProductForm (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/addProductForm.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene (root, 1100, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Changes scene to ModifyProductForm.
     * <br>
     * RUNTIME ERROR: Data was not being passed from the selected item in the Parts TableView so that it could be accessed
     * from ModifyProductController. Solution: remove fx:controller declaration from root element in modifyProductForm.fxml
     * and instead instantiate ModifyProductController class first so that constructor can be called to pass in Product object.
     * Subsequently, the setController method is called to associate the controller and fxml file.
     * @throws  IOException JavaFX Requirement
     * @param actionEvent Modify button clicked in Product Pane.*/
    public void toModifyProductForm (ActionEvent actionEvent) throws IOException {
        try {
            Product product = (Product) productsTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/modifyProductForm.fxml"));
            ModifyProductController modifyProductController = new ModifyProductController(product);
            loader.setController(modifyProductController);
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene (root, 1100, 600);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            productDeletionLabel.setText("ERROR: No product selected.");
            productDeletionLabel.setTextFill(Color.color(1,0,0));
        }
    }
}
