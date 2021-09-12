package sample.Controller;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

/**
 * Utility class with methods that handle most of the field validation logic for the add/modify forms
 * for both parts and products.
 * <br>
 * FUTURE ENHANCEMENT: Add additional validation methods to check for negative numbers. Add functionality
 * to prohibit strings that are over a certain length, or strings that match a previous entry.*/
public final class FieldValidationUtil {
    /**
     * Makes sure Name field is not blank and returns entered text. Adds errorMessage to
     * errorLog if exception caught.
     * @param nameTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return String of successfully parsed TextField, or empty String if unsuccessful.*/
    public static String parseName (TextField nameTextField, VBox errorLog) {
        try {
            validateStringField(nameTextField.getText());
            String name = nameTextField.getText();
            return name;
        } catch (BlankStringFieldException e) {
            Label errorMessage = new Label("Name must not be blank");
            errorLog.getChildren().add(errorMessage);
            return "";
        }
    }

    /**
     * Makes sure Stock field is a valid integer and returns int value. Adds errorMessage to errorLog
     * if exception caught.
     * @param inventoryTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return int data of successfully parsed TextField, or -1 if unsuccessful.*/
    public static int parseStock (TextField inventoryTextField, VBox errorLog) {
        try {
            int stock = Integer.parseInt(inventoryTextField.getText());
            return stock;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Inv must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return -1;
        }
    }

    /**
     * Makes sure Price field is a valid double and returns int value. Adds errorMessage to errorLog
     * if exception caught.
     * @param priceTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return double data of successfully parsed TextField, or -1 if unsuccessful.*/
    public static double parsePrice (TextField priceTextField, VBox errorLog) {
        try {
            double price = Double.parseDouble(priceTextField.getText());
            return price;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Price/Cost must be a double.");
            errorLog.getChildren().add(errorMessage);
            return -1;
        }
    }

    /**
     * Makes sure Min field is a valid integer and returns int value. Adds errorMessage to errorLog
     * if exception caught.
     * @param minTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return int data of successfully parsed TextField, or -1 if unsuccessful.*/
    public static int parseMin (TextField minTextField, VBox errorLog) {
        try {
            int min = Integer.parseInt(minTextField.getText());
            return min;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Min must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return -1;
        }
    }

    /**
     * Makes sure Max field is a valid integer and returns int value. Adds errorMessage to errorLog
     * if exception caught.
     * @param maxTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return int data of successfully parsed TextField, or -1 if unsuccessful.*/
    public static int parseMax (TextField maxTextField, VBox errorLog) {
        try {
            int max = Integer.parseInt(maxTextField.getText());
            return max;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("Max must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return -1;
        }
    }

    /**
     * Makes sure MachineId field is a valid integer and returns int value. Adds errorMessage to errorLog
     * if exception caught.
     * @param variableTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return int data of successfully parsed TextField, or -1 if unsuccessful.*/
    public static int parseMachineId (TextField variableTextField, VBox errorLog) {
        try {
            int machineId = Integer.parseInt(variableTextField.getText());
            return machineId;
        } catch (NumberFormatException e) {
            Label errorMessage = new Label("MachineID must be an integer.");
            errorLog.getChildren().add(errorMessage);
            return -1;
        }
    }

    /**
     * Makes sure companyName field is not blank and returns entered text. Adds errorMessage to
     * errorLog if exception caught.
     * @param variableTextField TextField object from add/modify form.
     * @param errorLog VBox passed in from add/modify form where error messages are displayed.
     * @return String of successfully parsed TextField, or empty String if unsuccessful.*/
    public static String parseCompanyName (TextField variableTextField, VBox errorLog) {
        try {
            String companyName = variableTextField.getText();
            validateStringField(companyName);
            return companyName;
        } catch (BlankStringFieldException e) {
            Label errorMessage = new Label("Company Name must not be blank");
            errorLog.getChildren().add(errorMessage);
            return "";
        }
    }

    /**
     * Checks the logic of given stock, min, and max values. Adds appropriate errorMessage to
     * errorLog if given values don't make sense.
     * @param stock Inventory/stock level passed in.
     * @param  min Minimum stock level passed in.
     * @param max Maximum stock level passed in.
     * @param errorLog VBox from add/modify form where error messages are displayed.*/
    public static void validateLogic(int stock, int min, int max, VBox errorLog) {
        if (stock < min) {
            Label errorMessage = new Label("Inv cannot be less than Min.");
            errorLog.getChildren().add(errorMessage);
        }
        if (stock > max) {
            Label errorMessage = new Label("Inv cannot be greater tan Max.");
            errorLog.getChildren().add(errorMessage);
        }
        if (min > max) {
            Label errorMessage = new Label("Min cannot be greater than Max");
            errorLog.getChildren().add(errorMessage);
        }
    }

    /**
     * Checks if given string is blank. Throws BlankStringFieldException if it is.
     * @param text String to be checked.
     * @throws BlankStringFieldException Caught by validator methods if String is invalid.*/
    private static void validateStringField(String text) throws BlankStringFieldException {
        if (text.isBlank()) {
            throw new BlankStringFieldException("Text fields must not be blank.");
        }
    }
}
