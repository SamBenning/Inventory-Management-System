package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

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




    Inventory inventory = new Inventory();
    private ObservableList<Part> allParts = Inventory.getAllParts();
    private ObservableList<Product> allProducts = Inventory.getAllProducts();

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
        allProducts.add(new Product(allParts,1, "TV", 100.4, 4,1,10));
        allProducts.add(new Product(allParts, 2, "Stereo", 50.75, 15, 1, 25));
        allProducts.add(new Product(allParts, 3, "Washing Machine", 75.00, 7, 1, 10));
        allProducts.add(new Product(allParts, 4, "Refrigerator", 150.99, 4, 1, 10));
        allParts.add(new InHousePart(1,"10mm Bolt", 0.10, 567, 100, 2000, 54));
        allParts.add(new InHousePart(2,"15mm Bolt", 0.15, 204, 100, 2000, 54));
        allParts.add(new InHousePart(3,"Spring", 0.05, 749, 150, 5000, 7));
        allParts.add(new InHousePart(4,"Washer", 0.50, 38, 15, 1000, 2));
        //System.out.println("Initialize ran.");

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



}
