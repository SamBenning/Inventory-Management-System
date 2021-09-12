package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Model.*;

public class Main extends Application {
    Inventory inventory = new Inventory();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/mainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        Inventory.addPart(new InHousePart("Iron Plate", 0.50, 567, 100, 2000, 54));
        Inventory.addPart(new InHousePart("Iron Rod", 0.43, 204, 100, 2000, 55));
        Inventory.addPart(new InHousePart("Copper Plate", 0.05, 749, 150, 5000, 7));
        Inventory.addPart(new InHousePart("Copper Wire", 0.50, 38, 15, 1000, 2));
        Inventory.addPart(new OutsourcedPart("Steel Pipe", 1.25, 33, 10, 250,"Ficsit"));
        Inventory.addPart(new OutsourcedPart("Screw", 2.50, 17, 10, 250, "Ficsit"));
        Inventory.addProduct(new Product(FXCollections.observableArrayList(), "Reinforced Plate", 100.4, 4,1,10));
        Inventory.addProduct(new Product(FXCollections.observableArrayList(), "Modular Frame", 50.75, 15, 1, 25));
        Inventory.addProduct(new Product(FXCollections.observableArrayList(), "Cable", 75.00, 7, 1, 10));
        Inventory.addProduct(new Product(FXCollections.observableArrayList(), "Rotor", 150.99, 4, 1, 10));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
