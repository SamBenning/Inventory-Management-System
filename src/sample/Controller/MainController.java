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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    public TextField partSearch;
    public TextField productSearch;





    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    @FXML
    public ObservableList<Part> searchByPartName (String input) {
        ObservableList<Part>matchList = FXCollections.observableArrayList();

        String inputLower = input.toLowerCase();

        allParts.forEach(part -> {
            if(part.getName().toLowerCase().contains(inputLower)) {
                matchList.add(part);
            }
        });

        return matchList;
    }

    @FXML
    public ObservableList<Product> searchByProductName (String input) {
        ObservableList<Product>matchList = FXCollections.observableArrayList();

        String inputLower = input.toLowerCase();

        allProducts.forEach(product -> {
            if(product.getName().toLowerCase().contains(inputLower)) {
                matchList.add(product);
            }
        });

        return matchList;
    }

    @FXML
    private void searchPartTableHandler (KeyEvent keyEvent) {
        String input = partSearch.getText();

        ObservableList<Part> parts = searchByPartName(input);
        partsTable.setItems(parts);

    }

    @FXML
    private void searchProductTableHandler (KeyEvent keyEvent) {
        String input = productSearch.getText();

        ObservableList<Product> products = searchByProductName(input);
        productsTable.setItems(products);

    }

    public void deletePartHandler (ActionEvent actionEvent) {
        boolean deleteSuccessful;
        deleteSuccessful = Inventory.deletePart((Part)partsTable.getSelectionModel().getSelectedItem());
    }

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
        //partSearch.setOnKeyTyped(searchTable(allParts, partSearch.getText()));

/*        allParts.add(new InHousePart(1,"10mm Bolt", 0.10, 567, 100, 2000, 54));
        System.out.println((Inventory.lookupPart(1)).getName());*/

    }

    public void addPartFromForm(Part part) {
        allParts.add(part);
    }

    public void addProductFromForm(Product product) {
        allProducts.add(product);
    }


    public void toAddPartForm (ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/View/addPartForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

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
            System.out.println("Error loading ModifyPart form. Did you select a part?");
            System.out.println(e);
        }
    }

    public void toAddProductForm (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/addProductForm.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene (root, 1100, 600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();

    }



}
