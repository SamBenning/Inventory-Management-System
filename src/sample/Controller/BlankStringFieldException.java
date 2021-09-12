package sample.Controller;

/**
 * Thrown when empty strings are entered in certain fields on add/modify forms.*/
public class BlankStringFieldException extends Exception{

    public BlankStringFieldException(String message) {
        super(message);
    }
}
