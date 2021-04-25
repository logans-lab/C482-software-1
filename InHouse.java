package software1;

/**
 * InHouse class
 * @author Logan
 */
public class InHouse extends Part {
    private int machineID;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }

    /**
     * @param machineID the machine id to set.
     */
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

    /**
     * @return the machine id.
     */
    public int getMachineID() {
        return machineID;
    }
}
