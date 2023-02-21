package model;
/**
 * Model of parts that were outsourced
 * @author Corey
 */
public class Outsourced extends Part{
    /**
     * Name of the company
     */
    private String companyName;
    /**
     * New Outsourced Constructor
     * @param id ID of the part
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory of the part
     * @param min Minimum stock of the part
     * @param max Maximum stock of the part
     * @param companyName Name of the Company
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Gets the name of the company
     * @return name of the company
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName name of the company
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
