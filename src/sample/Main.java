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

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/mainForm.fxml"));
        primaryStage.setTitle("Main Form");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {

    /*    Inventory testInv = new Inventory();

        InHousePart screw = new InHousePart(1, "screw", 0.55, 100, 25, 1000, 43);
        System.out.println(((InHousePart)screw).getMachineId());

        testInv.addPart(screw);

        String name = testInv.lookupPart(1).getName();
        int machineid = ((InHousePart)testInv.lookupPart(1)).getMachineId();
        System.out.println(name);
        System.out.println(machineid);
*/


        launch(args);


        //System.out.println((Inventory.lookupPart(1)).getName());
        System.out.println("hi there");
    }
}
