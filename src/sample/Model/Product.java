package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller.UniqueID;
import sample.Model.Part;

/**
 * Class for products.*/
public class Product {

    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(ObservableList<Part> associatedParts, String name, double price, int stock, int min, int max) {
        this.associatedParts = FXCollections.observableArrayList();
        //this.associatedParts = associatedParts;
        this.id = UniqueID.generateProductId();
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product() {
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Adds a part to the associatedParts ObservableList
     * @param part The Part object to be added to associatedParts
     * */
    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

    /**
     * Removes a part from the associatedParts ObservableList
     * @param selectedAssociatedPart The Part object to be removed from associatedParts
     * @return Returns true if deletion successful, false if not.
     * */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        boolean partRemoved = associatedParts.remove(selectedAssociatedPart);
        if(partRemoved) {
            return true;
        } else {
            return false;
        }
    }

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
