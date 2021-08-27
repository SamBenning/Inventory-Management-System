package sample.Controller;

public class UniqueID {

    private static String partPrefixInHouse = "10";
    private static String partPrefixOutsourced = "20";
    private static String productPrefix = "30";
    private static int partCount = 0;
    private static int partInHouseCount = 0;
    private static int partOutsourcedCount = 0;
    private static int productCount = 0;


    public static int generatePartId() {
        return ++partCount;
    }

    public static int generatePartInHouseId() {
        int id;
        String idString = partPrefixInHouse + (++partInHouseCount);
        id = Integer.parseInt(idString);
        return id;
    }

    public static int generatePartOutsourcedId() {
        int id;
        String idString = partPrefixOutsourced + (++partOutsourcedCount);
        id = Integer.parseInt(idString);
        return id;
    }

    public static int generateProductId() {
        return ++productCount;
    }

}
