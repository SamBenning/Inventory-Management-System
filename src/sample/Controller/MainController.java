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
        System.out.println("searchPartTableHandler called");
        String input = partSearch.getText();

        ObservableList<Part> parts = searchByPartName(input);
        partsTable.setItems(parts);

    }

    @FXML
    private void searchProductTableHandler (KeyEvent keyEvent) {
        System.out.println("searchProductTablehandler called");
        String input = productSearch.getText();

        ObservableList<Product> products = searchByProductName(input);
        productsTable.setItems(products);

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        allParts = Inventory.getAllParts();
        allProducts = Inventory.getAllProducts();
        partsTable.setItems(allParts);
        productsTable.setItems(allProducts);

/*        allProducts.add(new Product(allParts,1, "TV", 100.4, 4,1,10));
        allProducts.add(new Product(allParts, 2, "Stereo", 50.75, 15, 1, 25));
        allProducts.add(new Product(allParts, 3, "Washing Machine", 75.00, 7, 1, 10));
        allProducts.add(new Product(allParts, 4, "Refrigerator", 150.99, 4, 1, 10));*/
        Inventory.addPart(new InHousePart("10mm Bolt", 0.10, 567, 100, 2000, 54));
        Inventory.addPart(new InHousePart("15mm Bolt", 0.15, 204, 100, 2000, 54));
        Inventory.addPart(new InHousePart("Spring", 0.05, 749, 150, 5000, 7));
        Inventory.addPart(new InHousePart("Washer", 0.50, 38, 15, 1000, 2));
        System.out.println("Initialize ran.");

        Part part = new InHousePart("test",1,1,1,1,1);
        Inventory.addPart(part);
        System.out.println(Inventory.getAllParts().indexOf(part));
        System.out.println(part.getClass().getSimpleName());

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
        //partsTable.setItems(allParts);
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

        Part part = (Part)partsTable.getSelectionModel().getSelectedItem();
        System.out.println(part.getName());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/View/modifyPartForm.fxml"));

        //Creates new controller so that constructor can be called to pass in selected part
        ModifyPartController modifyPartController = new ModifyPartController(part);
        //Sets the controller since fx:controller cannot be defined in modifyPartForm.fxml w/ this method.
        loader.setController(modifyPartController);
        Parent root = loader.load();


        //modifyPartController.setSelectedPart(part);


        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }



}
