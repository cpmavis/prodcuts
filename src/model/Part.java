package model;

/**
 * Supplied class Part.java
 */

/**
 *
 * @author Corey Mavis
 */
public abstract class Part {
    /**
     * ID of the part
     */
    private int id;
    /**
     * Name of the part
     */
    private String name;
    /**
     * Price of the part
     */
    private double price;
    /**
     * Inventory of the part
     */
    private int stock;
    /**
     * Minimum stock of the part
     */
    private int min;
    /**
     * Maximum stock of the part
     */
    private int max;

    /**
     * Constructor of part
     * @param id id of the part
     * @param name name of the part
     * @param price price of the part
     * @param stock inventory of the part
     * @param min min of the part
     * @param max max of the part
     */
    public Part(int id, String name, double price, int stock, int min, int max) {
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
     * @param min the min to set
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

}
