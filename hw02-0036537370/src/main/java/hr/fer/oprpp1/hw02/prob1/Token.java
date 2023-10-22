package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representing a token for lexical analyzer.
 */
public class Token {

    /**
     * Type of the token.
     */
    private final TokenType type;

    /**
     * Value of the token.
     */
    private final Object value;

    /**
     * Constructs an instance of token by its type and value.
     * @param type Type of the token
     * @param value Value of the token
     */
    public Token(TokenType type, Object value) {
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
    public TokenType getType() {
        return this.type;
    }

}
