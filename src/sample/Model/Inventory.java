package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;

/** The inventory object stores ObservableLists for all parts and products in the program and manipulating those lists*/
public final class Inventory {
    private static ObservableList<Part> allParts;
    private static ObservableList<Product> allProducts;

    public Inventory() {
        allProducts = FXCollections.observableArrayList();
        allParts = FXCollections.observableArrayList();
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    public static Part lookupPart(int partId) {
        Iterator<Part> i = allParts.iterator();
        while(i.hasNext()) {
            Part part = i.next();
            if (part.getId() == partId) {
                return part;
            }
        }

        return null;
    }

    public static Product lookupProduct(int productId) {
        Iterator<Product> i = allProducts.iterator();
        while(i.hasNext()) {
            Product product = i.next();
            if (product.getId() == productId) {
                return product;
            }
        }

        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {

        ArrayList<Part> matchingParts = new ArrayList<Part>();

        Iterator i = allParts.iterator();
        while(i.hasNext()) {
            Part part = (Part)i.next();
            if (part.getName() == partName) {
                matchingParts.add(part);
            }
        }

        ObservableList<Part> observableMatchingParts = FXCollections.observableList(matchingParts);

        /* Found how to change ArrayList to obervableList at link below. Also, this link said
        something about adding a Change Listener to the obervable list. Not sure if I'll need it,
        but something to keep in mind.

        https://docs.oracle.com/javafx/2/collections/jfxpub-collections.htm*/

        return observableMatchingParts;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ArrayList<Product> matchingProducts = new ArrayList<Product>();

        Iterator i = allProducts.iterator();
        while(i.hasNext()) {
            Product product = (Product)i.next();
            if (product.getName() == productName) {
                matchingProducts.add(product);
            }
        }
        ObservableList<Product> observableMatchingProducts = FXCollections.observableList(matchingProducts);
        return observableMatchingProducts;
    }

    public static void updatePart (int index, Part updatedPart) {

        allParts.set(index, updatedPart);
        /*if(updatedPart.getClass().getSimpleName().equals("InHousePart")) {
            InHousePart part = new InHousePart();
            part.setId(selectedPart.getId());
            part.setName(updatedPart.getName());
            part.setStock(updatedPart.getStock());
            part.setPrice(updatedPart.getPrice());
            part.setMin(updatedPart.getMin());
            part.setMax(updatedPart.getMax());
            part.setMachineId(((InHousePart)updatedPart).getMachineId());
            allParts.set(index, part);
        } else if (updatedPart.getClass().getSimpleName().equals("OutsourcedPart")) {
            OutsourcedPart part = new OutsourcedPart();
            part.setId(selectedPart.getId());
            part.setName(updatedPart.getName());
            part.setStock(updatedPart.getStock());
            part.setPrice(updatedPart.getPrice());
            part.setMin(updatedPart.getMin());
            part.setMax(updatedPart.getMax());
            part.setCompanyName(((OutsourcedPart)updatedPart).getCompanyName());
            allParts.set(index, part);
        }
        System.out.println("Updated part to class " + updatedPart.getClass().getSimpleName());*/
    }

    public static void updateProduct(int index, Product newProduct) {

    }

    public static boolean deletePart(Part selectedPart) {
        try {
            allParts.remove(selectedPart);
            return true;
        } catch (Exception e) {
            System.out.println("Error deleting part " + e);
            return false;
        }
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return true;
    }

    public static ObservableList<Part> getAllParts () {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }




}
