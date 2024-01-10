package hr.fer.oprpp1.hw07.gui.layouts;

/**
 * Class representing a layout exception.
 */
public class CalcLayoutException extends RuntimeException {

    /**
     * Constructs a new layout exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public CalcLayoutException() {
    }

    /**
     * Constructs a new layout exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public CalcLayoutException(String message) {
        super(message);
    }

}
