package ordermanager.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OutsourcedPart extends Part {

    private final SimpleStringProperty companyName;

    public OutsourcedPart(String companyName, String name, Double price, int partID, int inStock, int min, int max) {

        super(name, price, partID, inStock, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }

    public void setcompanyName(String s) {
        companyName.set(s);
    }

    public String getcompanyName() {
        return companyName.get();
    }

}
