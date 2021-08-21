package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.Part;

public class Product extends Part {

    private ObservableList<Part> associatedParts;

    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts) {
        super(id, name, price, stock, min, max);
        this.associatedParts = associatedParts;
    }

    public Product(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
        this.associatedParts = FXCollections.observableArrayList();
    }

    public void addAssociatedPart (Part part) {
        associatedParts.add(part);
    }

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
}
