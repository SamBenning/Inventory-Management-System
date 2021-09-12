package sample.Model;

import sample.Controller.UniqueID;

/**
 * Class for outsourced parts.*/
public class OutsourcedPart extends Part {
    private String companyName;
    public OutsourcedPart(String name, double price, int stock, int min, int max, String companyName) {
        super(UniqueID.generatePartId(), name, price, stock, min, max);
        this.companyName = companyName;
    }
    public OutsourcedPart() {
        super(-1, "", -1, -1, -1, -1);
        this.companyName = "";
    }

    /**
     * @return name of company who supplied part
     * */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName name of company to set
     * */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
