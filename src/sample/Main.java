package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

        Product testProduct = new Product(1, "test", 1.05, 10, 1, 12);
        Product testProduct2 = new Product(2, "test", 1.11, 18, 1, 20);
        Part testPart = new Part(1, "testpart", 1.10, 8, 1, 15);
        Inventory testInventory = new Inventory();
        System.out.println(testProduct.getName());
        Inventory.addProduct(testProduct);
        Inventory.addProduct(testProduct2);
        Inventory.addPart(testPart);
        Inventory.lookupProduct("test").forEach( prod -> System.out.println(prod.getName() + " " + prod.getId()));
        System.out.println((Inventory.lookupPart(1)).getName());


        System.out.println("hi there");
    }
}
