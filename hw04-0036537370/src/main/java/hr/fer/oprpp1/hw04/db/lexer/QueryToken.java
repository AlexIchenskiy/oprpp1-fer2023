package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Class representing a token for query lexical analyzer.
 */
public class QueryToken {

    /**
     * Type of the token.
     */
    private final QueryTokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Constructs an instance of token by its type and value.
     * @param type Type of the token
     * @param value Value of the token
     */
    public QueryToken(QueryTokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Returns the token value.
     * @return Token value
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * Returns the token type.
     * @return Token type
     */
    public QueryTokenType getType() {
        return this.type;
    }

}
