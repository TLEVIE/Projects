package ordermanager.model;

import java.util.ArrayList;

public class Inventory {

    private final ArrayList<Product> products;
    private final ArrayList<Part> allParts;

    public Inventory(ArrayList<Product> products, ArrayList<Part> allParts) {
        this.products = new ArrayList<>();
        this.allParts = new ArrayList<>();
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public boolean removeProduct(int i) {

        boolean removed = false;

        for (Product p : products) {

            if (p.getProductID() == i) {
                products.remove(p);
                removed = true;
            }
        }
        return removed;
    }

    public Product lookupProduct(int i) {
        Product pReturn = new Product("", 0.0, i, 0, 0, 0);
        for (Product p : products) {

            if (p.getProductID() == i) {
                pReturn = p;
            } else {
                pReturn = null;
            }
        }
        return pReturn;
    }

    public void updateProduct(int i) {

    }

    public void addPart(Part p) {
        allParts.add(p);
    }

    public boolean removePart(int i) {

        boolean removed = false;

        for (Part p : allParts) {

            if (p.getpartID() == i) {
                allParts.remove(p);
                removed = true;
            }
        }
        return removed;
    }

    public Part lookupPart(int i) {
        Part pReturn = null;
        for (Part p : allParts) {

            if (p.getpartID() == i) {
                pReturn = p;
            } else {
                pReturn = null;
            }
        }
        return pReturn;
    }

    public void updatePart(int i) {

    }
}
