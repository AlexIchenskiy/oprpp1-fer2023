package hr.fer.oprpp1.hw04.db;

/**
 * A generic exception for query parser.
 */
public class QueryParserException extends RuntimeException {

    /**
     * Constructs an exception with a message.
     * @param message Exception message
     */
    public QueryParserException(String message) {
        super(message);
    }

}
