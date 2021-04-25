package software1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class
 * @author Logan
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product () {
        //empty constructor
    }

    public Product (int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the id to get.
     */
    public int getId() {
        return id;
    }

    /**
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the name to get.
     */
    public String getName() {
        return name;
    }

    /**
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the price to get.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param stock the stock to set.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the stock to get.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param min the min to set.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the min to get.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param max the max to set.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the max to get.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param part the part to add.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart the part to remove.
     * @return true if part was removed. Handled in AddProductFormController.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * @return list of associated parts.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
