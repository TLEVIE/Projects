package ordermanager.model;

import javafx.beans.property.SimpleIntegerProperty;

public class InhousePart extends Part {

    private final SimpleIntegerProperty machineID;

    public InhousePart(int machineID, String name, Double price, int partID, int inStock, int min, int max) {
        
        super(name, price, partID, inStock, min, max);
        this.machineID = new SimpleIntegerProperty(machineID);
    }

    public void setMachineID(int in) {
        machineID.set(in);
    }

    public int getmachineID() {
        return machineID.get();
    }

}
