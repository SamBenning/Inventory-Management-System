package sample.Model;

public class OutsourcedPart extends Part {

    private String companyName;

    public OutsourcedPart(String name, double price, int stock, int min, int max, String companyName) {
        super(name, price, stock, min, max, false);
        this.companyName = companyName;
    }

    public OutsourcedPart() {
        super();
        super.setInHouse(false);
        this.companyName = "";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
