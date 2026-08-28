package sage.exception;

/** Exception raised when Sage cannot process a user command. */
public class SageException extends Exception {
    /** Creates an exception with the supplied user-facing message. */
    public SageException(String message) {
        super(message);
    }
}
