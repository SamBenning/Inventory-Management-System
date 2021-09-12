package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;

/** The inventory class stores ObservableLists for all parts and products in the program and manipulating those lists*/
public final class Inventory {
    private static ObservableList<Part> allParts;
    private static ObservableList<Product> allProducts;
    public Inventory() {
        allProducts = FXCollections.observableArrayList();
        allParts = FXCollections.observableArrayList();
    }

    /**
     * Adds a part to the private static ObservableList allParts
     * @param newPart New part object to be added to allParts ObservableList*/
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a prodcut to the private static OberservableList allProducts
     * @param newProduct New Product object to be added to allProducts ObservableList*/
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches the allParts ObservableList for a matching id and returns that Part object.
     * <br>
     * RUNTIME ERROR: Initially could not get search functionality to work for both name and id. I
     * could get id or name search working independently, but not both. Results would not display properly.
     * My solution is to have lookupPart and lookupProduct by id methods throw an InvalidParameterException if
     * no match found. This exception is caught in the search handlers in MainController.java and calls the
     * overloaded lookup methods to search by name if id search fails.
     * @param partId The value that will be checked against all Part Ids in allParts ObservableList
     * @return Part object matching the partId passed in. Returns null if no match found.
     * */
    public static Part lookupPart(int partId) {

        for (Part part: allParts) {
            if (part.getId() == partId) {
                System.out.println(part.getName());
                return part;
            }
        }
        throw new InvalidParameterException();
    }

    /**
     * Searches the allProducts ObservableList for a matching id and returns that Product object.
     * <br>
     * RUNTIME ERROR: Initially could not get search functionality to work for both name and id. I
     * could get id or name search working independently, but not both. Results would not display properly.
     * My solution is to have lookupPart and lookupProduct by id methods throw an InvalidParameterException if
     * no match found. This exception is caught in the search handlers in MainController.java and calls the
     * overloaded lookup methods to search by name if id search fails.
     * @param productId The value that will be checked against all Product Ids in allProducts ObservableList
     * @return Product object matching the productId passed in. Returns null if no match found.
     * @throws InvalidParameterException throws exception if no id matches are found.
     * */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                System.out.println(product.getName());
                return product;
            }
        }
        throw new InvalidParameterException();
    }

    /**
     * Searches the allParts ObservableList for all matching part names and returns an ObservableList of
     * matching Part objects.
     * @param partName The String that will be checked against all Part names in allParts ObservableList.
     * @return ObservableList of all matches. If no matches are found, an empty ObservableList is returned.
     * */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part part: allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * Searches the allProducts ObservableList for all matching product names and returns an ObservableList of
     * matching Product objects.
     * @param productName The String that will be checked against all Product names in allProducts ObservableList.
     * @return ObservableList of all matches. If no matches are found, an empty ObservableList is returned.
     * */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for (Product product: allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * Replaces a part in the allParts ObservableList with a new Part object.
     * @param index The index of the Part in allParts to be replaced.
     * @param updatedPart The new/updated Part object
     * */
    public static void updatePart (int index, Part updatedPart) {
        allParts.set(index, updatedPart);
    }

    /**
     * Replaces a product in the allProducts ObservableList with a new Product object.
     * @param index The index of the Product in allProducts to be replaced.
     * @param updatedProduct The new/updated Product object.
     * */
    public static void updateProduct(int index, Product updatedProduct) {
        allProducts.set(index, updatedProduct);
    }

    /**
     * Removes a part from the allParts ObservableList.
     * @param selectedPart The Part object to be removed from allParts.
     * @return Returns true if deletion was successful and false if unsuccessful.*/
    public static boolean deletePart(Part selectedPart) {
        try {
            allParts.remove(selectedPart);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting part " + e);
            return false;
        }
    }

    /**
     * Removes a product from the allProducts ObservableList.
     * @param selectedProduct The Product object to be removed from allProducts.
     * @return Returns true if deletion was successful and false if unsuccessful.*/
    public static boolean deleteProduct(Product selectedProduct) {
        try {
            allProducts.remove(selectedProduct);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting product. " + e);
            return false;
        }
    }

    /**
     * Allows access to private static allParts ObservableList.
     * @return allParts ObservableList
     * */
    public static ObservableList<Part> getAllParts () {
        return allParts;
    }

    /**
     * Allows access to private static allProducts ObservableList.
     * @return allProducts ObservableList
     * */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
