package sample.Controller;

/**
 * Utility class used to store static part and product counts so that
 * unique IDs can be generated.*/
public class UniqueID {

    private static int partCount = 0;
    private static int productCount = 0;

    /**
     * Generates new part ID
     * @return New part ID.*/
    public static int generatePartId() {
        return ++partCount;
    }

    /**
    * Generates new product ID
    * @return New product ID.*/
    public static int generateProductId() {
        return ++productCount;
    }

}
