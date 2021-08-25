package sample.Model;

public class InHousePart extends Part{

    private int machineId;

    public InHousePart(String name, double price, int stock, int min, int max, int machineId) {
        super(name, price, stock, min, max, true);
        this.machineId = machineId;
    }

    public InHousePart() {
        super();
        super.setInHouse(true);
        this.machineId = -1;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
