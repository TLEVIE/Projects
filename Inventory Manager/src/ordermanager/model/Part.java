package ordermanager.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Part {

    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inStock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Part(String name, Double price, int partID, int inStock, int min, int max) {
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.partID = new SimpleIntegerProperty(partID);
        this.inStock = new SimpleIntegerProperty(inStock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }

    public void setName(String n) {
        name.set(n);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setPrice(double p) {
        price.set(p);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setInStock(int in) {
        inStock.set(in);
    }

    public int getInStock() {
        return inStock.get();
    }

    public IntegerProperty inStockProperty() {
        return inStock;
    }

    public void setMin(int mi) {
        min.set(mi);
    }

    public int getMin() {
        return min.get();
    }

    public void setMax(int ma) {
        max.set(ma);
    }

    public int getMax() {
        return max.get();
    }

    public void setpartID(int p) {
        partID.set(p);
    }

    public int getpartID() {
        return partID.get();
    }

    public IntegerProperty partIDProperty() {
        return partID;
    }    
}
