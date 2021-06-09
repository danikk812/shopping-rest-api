package by.danilap.codextask.exception;

public class ItemNotInCartException extends RuntimeException {

    public ItemNotInCartException() {
    }

    public ItemNotInCartException(String message) {
        super(message);
    }

    public ItemNotInCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotInCartException(Throwable cause) {
        super(cause);
    }
}
