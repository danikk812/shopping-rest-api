package by.danilap.codextask.exception;

public class ItemIsInCartException extends RuntimeException {

    public ItemIsInCartException() {
    }

    public ItemIsInCartException(String message) {
        super(message);
    }

    public ItemIsInCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemIsInCartException(Throwable cause) {
        super(cause);
    }
}
