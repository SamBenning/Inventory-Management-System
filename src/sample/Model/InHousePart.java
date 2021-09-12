package sample.Model;

import sample.Controller.UniqueID;

/**
 * Class for in-house parts.*/
public class InHousePart extends Part{
    private int machineId;
    public InHousePart(String name, double price, int stock, int min, int max, int machineId) {
        super(UniqueID.generatePartId(),name, price, stock, min, max);
        this.machineId = machineId;
    }
    public InHousePart() {
        super(-1, "", -1, -1, -1, -1);
        this.machineId = -1;
    }

    /**
     * @return the machineId
     * */
    public int getMachineId() {
        return machineId;
    }

    /**
     * @param machineId the machineID to set
     * */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
