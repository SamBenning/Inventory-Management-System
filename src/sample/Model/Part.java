package sample.Model;

import javafx.collections.ObservableList;
import sample.Controller.UniqueID;

public abstract class Part {

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private boolean isInHouse;

    public Part(String name, double price, int stock, int min, int max, boolean isInHouse) {

        this.isInHouse = isInHouse;
        if (isInHouse) {
            this.id = UniqueID.generatePartId();
        } else {
            this.id = UniqueID.generatePartOutsourcedId();
        }


        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Part() {
        this.id = 0;
        this.name = "";
        this.price = -1;
        this.stock = -1;
        this.min = -1;
        this.max = -1;
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

    public boolean isInHouse() {
        return isInHouse;
    }

    public void setInHouse(boolean inHouse) {
        isInHouse = inHouse;
    }
}
