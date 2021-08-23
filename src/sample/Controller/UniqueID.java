package sample.Controller;

public class UniqueID {

    private static String partPrefixInHouse = "10";
    private static String partPrefixOutsourced = "20";
    private static String productPrefix = "30";
    private static int partInHouseCount = 0;
    private static int partOutsourcedCount = 0;
    private static int productCount = 0;



    public static int generatePartInHouseId() {
        int id;
        String idString = partPrefixInHouse + Integer.toString(++partInHouseCount);
        id = Integer.parseInt(idString);
        return id;
    }

    public static int generatePartOutsourcedId() {
        int id;
        String idString = partPrefixOutsourced + Integer.toString(++partOutsourcedCount);
        id = Integer.parseInt(idString);
        return id;
    }

    public static int generateProductId() {
        int id;
        String idString = productPrefix + Integer.toString(++productCount);
        id = Integer.parseInt(idString);
        return id;
    }

}
