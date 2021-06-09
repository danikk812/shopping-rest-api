package by.danilap.codextask.exception;

public class ItemAlreadyInCartException extends RuntimeException {

    public ItemAlreadyInCartException() {
    }

    public ItemAlreadyInCartException(String message) {
        super(message);
    }

    public ItemAlreadyInCartException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemAlreadyInCartException(Throwable cause) {
        super(cause);
    }
}
