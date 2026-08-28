package sage.exception;


/** Represents a user-facing error reported by Sage. */

public class SageException extends Exception {
    /** Creates an exception with the supplied user-facing message. */
    public SageException(String message) {
        super(message);
    }
}
