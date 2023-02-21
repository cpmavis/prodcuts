package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A Model of a product containing parts
 * @author Corey
 */
public class Product {
    /**
     * A list of all associated parts for the product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * ID of the product
     */
    private int id;
    /**
     * Name of the product
     */
    private String name;
    /**
     * Price of the product
     */
    private double price;
    /**
     * Inventory of the product
     */
    private int stock;
    /**
     * Minimum stock of the product
     */
    private int min;
    /**
     * Maximum stock of the product
     */
    private int max;
    /**
     * New product Constructor
     * @param id ID of the product
     * @param name Name of the product
     * @param price Price of the product
     * @param stock Inventory of the product
     * @param min Minimum stock of the product
     * @param max Maximum stock of the product
     */
    public Product (int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     *  @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds parts to associated parts list
     * @param part the part to add to the associated parts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * Removes part from the associated parts list
     * @param selectedAssociatedPart selected part
     * @return boolean value of success
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return true;
    }
    /**
     * Gets a list of parts associated with the product
     * @return associated parts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
