package hr.fer.oprpp1.hw04.db.lexer;

/**
 * Class representing a lexer for queries.
 */
public class QueryLexer {

    /**
     * Input text data.
     */
    private final char[] data;

    /**
     * Current token.
     */
    private QueryToken token;

    /**
     * Index of the first unprocessed symbol.
     */
    private int currentIndex;

    /**
     * Constructs a lexer from a document body text.
     * @param documentBody Document body to be analyzed
     */
    public QueryLexer(String documentBody) {
        this.data = documentBody.toCharArray();
    }

    /**
     * Generates and return the next token from the input text.
     * @return The next token
     */
    public QueryToken nextToken() {
        if (this.token != null && this.token.getType() == QueryTokenType.EOF) {
            throw new QueryLexerException("No more tokens left!");
        }

        clearWhitespace();

        if (this.currentIndex >= this.data.length) {
            this.token = new QueryToken(QueryTokenType.EOF, null);
            return this.token;
        }

        return this.tokenize();
    }

    /**
     * Returns the token that was lastly generated.
     * @return Last generated token
     */
    public QueryToken getToken() {
        return this.token;
    }

    /**
     * Function to create a token.
     * @return Analyzed token
     */
    private QueryToken tokenize() {
        if (this.isValidOperator()) {
            return this.tokenizeOperator();
        }

        if (Character.isLetter(this.data[this.currentIndex])) {
            return this.tokenizeIdentifier();
        }

        if (this.data[this.currentIndex] == '"') {
            return this.tokenizeString();
        }

        throw new QueryLexerException("Invalid token value!");
    }

    /**
     * Function to tokenize an operator.
     * @return Token representing an operator
     */
    private QueryToken tokenizeOperator() {
        if (String.valueOf(this.data[this.currentIndex]).equalsIgnoreCase("L")) {
            this.token = new QueryToken(QueryTokenType.OPERATOR, "LIKE");
            this.currentIndex += 4;
            return this.token;
        }

        if ((
                this.data[this.currentIndex] == '>' ||
                        this.data[this.currentIndex] == '<' ||
                        this.data[this.currentIndex] == '!'
        ) &&
            this.currentIndex + 1 < this.data.length &&
            this.data[this.currentIndex + 1] == '=')
        {
            this.token = new QueryToken(QueryTokenType.OPERATOR, this.data[this.currentIndex] + "=");
            this.currentIndex += 2;
            return this.token;
        }

        this.token = new QueryToken(QueryTokenType.OPERATOR, this.data[this.currentIndex]);
        this.currentIndex++;
        return this.token;
    }

    /**
     * Function to tokenize an identifier.
     * @return Token representing an identifier
     */
    private QueryToken tokenizeIdentifier() {
        StringBuilder sb = new StringBuilder();

        sb.append(this.data[this.currentIndex]);
        this.currentIndex++;

        while (this.currentIndex < this.data.length) {
            if (Character.isLetter(this.data[this.currentIndex])) {
                sb.append(this.data[this.currentIndex]);
                this.currentIndex++;
                continue;
            }

            break;
        }

        this.token = new QueryToken(QueryTokenType.IDENTIFIER, sb.toString());
        return this.token;
    }

    /**
     * Function to tokenize a string.
     * @return Token representing a string
     */
    private QueryToken tokenizeString() {
        this.currentIndex++;

        StringBuilder sb = new StringBuilder();

        boolean terminated = false;

        while (this.currentIndex < this.data.length) {
            if (this.data[this.currentIndex] == '"') {
                terminated = true;
                this.currentIndex++;
                break;
            }

            if (this.data[this.currentIndex] == '\\' && this.currentIndex + 1 < this.data.length) {
                if (this.data[this.currentIndex + 1] == 'n') {
                    this.currentIndex += 2;
                    sb.append('\n');
                    continue;
                }

                if (this.data[this.currentIndex + 1] == 'r') {
                    this.currentIndex += 2;
                    sb.append('\r');
                    continue;
                }

                if (this.data[this.currentIndex + 1] == 't') {
                    this.currentIndex += 2;
                    sb.append('\t');
                    continue;
                }

                if (this.data[this.currentIndex + 1] == '"') {
                    this.currentIndex += 2;
                    sb.append('"');
                    continue;
                }

                if (this.data[this.currentIndex + 1] == '\\') {
                    this.currentIndex += 2;
                    sb.append('\\');
                    continue;
                }

                throw new QueryLexerException("Invalid escape inside of a tag string!");
            }

            sb.append(this.data[this.currentIndex]);
            this.currentIndex++;
        }

        if (!terminated) {
            throw new QueryLexerException("String not terminated!");
        }

        this.token = new QueryToken(QueryTokenType.STRING, sb.toString());
        return this.token;
    }

    /**
     * Function that checks whether the next symbol is a valid operator.
     * @return Boolean representing whether the next symbol is a valid operator
     */
    private boolean isValidOperator() {
        return this.data[this.currentIndex] == '>' ||
                this.data[this.currentIndex] == '<' ||
                this.data[this.currentIndex] == '=' ||
                (this.currentIndex + 1 < this.data.length && this.data[this.currentIndex + 1] == '=' &&
                        (
                                (this.data[this.currentIndex] == '>') ||
                                        (this.data[this.currentIndex] == '<') ||
                                        (this.data[this.currentIndex] == '!')
                        )
                ) ||
                (this.currentIndex + 3 < this.data.length &&
                        String.valueOf(this.data[this.currentIndex]).equalsIgnoreCase("L") &&
                        String.valueOf(this.data[this.currentIndex + 1]).equalsIgnoreCase("I") &&
                        String.valueOf(this.data[this.currentIndex + 2]).equalsIgnoreCase("K") &&
                        String.valueOf(this.data[this.currentIndex + 3]).equalsIgnoreCase("E"));
    }

    /**
     * Function to check whether the provided char is a whitespace.
     * @param value Value to be checked
     * @return Boolean representing whether the provided char is a whitespace
     */
    private boolean isWhitespace(char value) {
        return value == '\r' || value == '\n' || value == '\t' || value == ' ';
    }

    /**
     * Function to clear all incoming whitespaces.
     */
    private void clearWhitespace() {
        while (true) {
            if (this.currentIndex < this.data.length && this.isWhitespace(this.data[this.currentIndex])) {
                this.currentIndex++;
                continue;
            }

            break;
        }
    }

}
