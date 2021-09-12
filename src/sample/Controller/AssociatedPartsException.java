package sample.Controller;

/**
 * This exception is thrown if user attempts to delete a Product that has a non-empty
 * associated parts list.*/
public class AssociatedPartsException extends Exception {

    public AssociatedPartsException(String message) {
        super(message);
    }
}
