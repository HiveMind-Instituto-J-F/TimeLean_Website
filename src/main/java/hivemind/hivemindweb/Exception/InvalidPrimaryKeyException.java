package hivemind.hivemindweb.Exception;

public class InvalidPrimaryKeyException extends RuntimeException {
    public InvalidPrimaryKeyException(String message) {
        super(message);
    }

    public InvalidPrimaryKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
