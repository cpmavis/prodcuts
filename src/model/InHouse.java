package model;
/**
 * Model of parts that are In House
 * @author Corey
 */

public class InHouse extends Part{
    /**
     * ID of the Machine
     */
    private int machineID;

    /**
     * New In house Constructor
     * @param id ID of the part
     * @param name Name of the part
     * @param price Price of the part
     * @param stock Inventory of the part
     * @param min Minimum stock of the part
     * @param max Maximum stock of the part
     * @param machineID ID of the machine
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * Gets the ID of the Machine
     * @return machine ID
     */
    public int getMachineID() {
        return machineID;
    }

    /**
     * ID of the machine
     * @param machineID machine ID
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
