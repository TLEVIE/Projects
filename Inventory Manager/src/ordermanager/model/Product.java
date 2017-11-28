package ordermanager.model;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private final IntegerProperty productID, inStock, min, max;
    private final StringProperty name;
    private final DoubleProperty price;

    public Product(String name, Double price, int productID, int inStock, int min, int max) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.productID = new SimpleIntegerProperty(productID);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }

    public void setName(String s) {
        name.set(s);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPrice(double d) {
        price.set(d);
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public double getPrice() {
        return price.get();
    }

    public void setInStock(int i) {
        inStock.set(i);
    }

    public int getInStock() {
        return inStock.get();
    }

    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public void setMin(int i) {
        min.set(i);
    }

    public int getMin() {
        return min.get();
    }

    public void setMax(int i) {
        max.set(i);
    }

    public int getMax() {
        return max.get();
    }

    public void addAssociatedPart(Part p) {
        associatedParts.add(p);
    }

    public boolean removeAssociatedPart(int i) {

        boolean removed = false;

        for (Part p : associatedParts) {

            if (p.getpartID() == i) {
                associatedParts.remove(p);
                removed = true;
            }
        }
        return removed;
    }

    public Part lookupAssociatedPart(int i) {
        Part pReturn = null;
        for (Part p : associatedParts) {

            if (p.getpartID() == i) {
                pReturn = p;
            } else {
                pReturn = null;
            }
        }
        return pReturn;
    }

    public void setProductID(int i) {
        productID.set(i);
    }

    public int getProductID() {
        return productID.get();
    }

    public IntegerProperty productIDProperty() {
        return productID;
    }

}
