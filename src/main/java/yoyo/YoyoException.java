package yoyo;

/**
 * Custom exception class for the Yoyo application. Used to handle
 * application-specific errors.
 */
public class YoyoException extends Exception {

    /**
     * Constructs a new YoyoException with the specified detail message.
     *
     * @param msg the detail message
     */
    public YoyoException(String msg) {
        super(msg);
    }
}
