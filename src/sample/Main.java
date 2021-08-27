package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.InHousePart;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

public class Main extends Application {
    Inventory inventory = new Inventory();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/mainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        Inventory.addPart(new InHousePart("10mm Bolt", 0.10, 567, 100, 2000, 54));
        Inventory.addPart(new InHousePart("15mm Bolt", 0.15, 204, 100, 2000, 54));
        Inventory.addPart(new InHousePart("Spring", 0.05, 749, 150, 5000, 7));
        Inventory.addPart(new InHousePart("Washer", 0.50, 38, 15, 1000, 2));
        Inventory.addProduct(new Product(Inventory.getAllParts(), "TV", 100.4, 4,1,10));
        Inventory.addProduct(new Product(Inventory.getAllParts(), "Stereo", 50.75, 15, 1, 25));
        Inventory.addProduct(new Product(Inventory.getAllParts(), "Washing Machine", 75.00, 7, 1, 10));
        Inventory.addProduct(new Product(Inventory.getAllParts(), "Refrigerator", 150.99, 4, 1, 10));
    }


    public static void main(String[] args) {


        launch(args);


        //System.out.println((Inventory.lookupPart(1)).getName());
        System.out.println("hi there");
    }
}
