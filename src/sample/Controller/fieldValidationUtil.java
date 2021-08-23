package sample.Controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public final class fieldValidationUtil {

    public static int parseId (TextField idTextField, int id, VBox errorLog) {
        try {
            id = Integer.parseInt(idTextField.getText());
            return id;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("ID must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return id;
        }
    }

    public static String parseName (TextField nameTextField, String name, VBox errorLog) {
        try {
            validateStringField(nameTextField.getText());
            name = nameTextField.getText();
            return name;
        } catch (BlankStringFieldException e) {
            Label errorMessage = new Label("Name must not be blank");
            errorLog.getChildren().add(errorMessage);
            return name;
            //noExceptions = false;
        }
    }

    public static int parseStock (TextField inventoryTextField, int stock, VBox errorLog) {
        try {
            stock = Integer.parseInt(inventoryTextField.getText());
            return stock;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Inv must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return stock;
            //noExceptions = false;
        }
    }

    public static double parsePrice (TextField priceTextField, double price, VBox errorLog) {
        try {
            price = Double.parseDouble(priceTextField.getText());
            return price;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Price/Cost must be a double.");
            errorLog.getChildren().add(errorMessage);
            return price;
            //noExceptions = false;
        }
    }

    public static int parseMin (TextField minTextField, int min, VBox errorLog) {
        try {
            min = Integer.parseInt(minTextField.getText());
            return min;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Min must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return min;
            //noExceptions = false;
        }
    }

    public static int parseMax (TextField maxTextField, int max, VBox errorLog) {
        try {
            max = Integer.parseInt(maxTextField.getText());
            return max;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Max must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return max;
            //noExceptions = false;
        }
    }

    public static int parseMachineId (TextField variableTextField, int machineId, VBox errorLog) {
        try {
            machineId = Integer.parseInt(variableTextField.getText());
            return machineId;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("MachineID must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return machineId;
            //noExceptions = false;
        }
    }

    public static String parseCompanyName (TextField variableTextField, String companyName, VBox errorLog) {
        try {
            companyName = variableTextField.getText();
            validateStringField(companyName);
            return companyName;
        } catch (BlankStringFieldException e) {
            Label errorMessage = new Label("Company Name must not be blank");
            errorLog.getChildren().add(errorMessage);
            return companyName;
        }
    }


    private static void validateStringField(String text) throws BlankStringFieldException {
        if (text.isBlank()) {
            throw new BlankStringFieldException("Text fields must not be blank.");
        }
    }
}
