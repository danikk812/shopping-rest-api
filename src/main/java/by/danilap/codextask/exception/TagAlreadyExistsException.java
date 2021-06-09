package by.danilap.codextask.exception;

public class TagAlreadyExistsException extends RuntimeException {

    public TagAlreadyExistsException() {
    }

    public TagAlreadyExistsException(String message) {
        super(message);
    }

    public TagAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
