package by.danilap.codextask.exception;

public class CartIsEmptyException extends RuntimeException {

    public CartIsEmptyException() {
    }

    public CartIsEmptyException(String message) {
        super(message);
    }

    public CartIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public CartIsEmptyException(Throwable cause) {
        super(cause);
    }
}
