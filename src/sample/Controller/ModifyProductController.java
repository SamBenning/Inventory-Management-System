package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    public ModifyProductController(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

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

    public void modifyProductHandler (ActionEvent actionEvent) {
        errorLog.getChildren().clear();
        boolean hasException = false;

        String name = "";
        int stock = -1;
        double price = -1;
        int min = -1;
        int max = -1;

        name = fieldValidationUtil.parseName(nameTextField, name, errorLog);
        stock = fieldValidationUtil.parseStock(inventoryTextField, stock, errorLog);
        price = fieldValidationUtil.parsePrice(priceTextField, price, errorLog);
        min = fieldValidationUtil.parseMin(minTextField, min, errorLog);
        max = fieldValidationUtil.parseMax(maxTextField, max, errorLog);

        fieldValidationUtil.validateLogic(stock, min, max, errorLog);

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

    public void addSelectedPartHandler(ActionEvent actionEvent) {
        try {
            Part selectedPart = (Part)partsTableAll.getSelectionModel().getSelectedItem();
            selectedProduct.addAssociatedPart(selectedPart);
        } catch (Exception e) {
            System.out.println("There was a problem associating part. Did you select a part?");
            System.out.println(e);
        }
    }

    public void removeAssocPartHandler(ActionEvent actionEvent) {
        try {
            selectedProduct.deleteAssociatedPart((Part)partsTableAssoc.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println("Problem removing associated part.");
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

    public void toMainMenu(ActionEvent actionEvent, Product product) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/mainForm.fxml"));
        Parent root = loader.load();
        MainController mc = loader.getController();
        mc.addProductFromForm(product);
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 850, 600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

}
