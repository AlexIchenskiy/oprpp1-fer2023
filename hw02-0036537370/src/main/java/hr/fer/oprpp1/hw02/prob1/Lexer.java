package hr.fer.oprpp1.hw02.prob1;

/**
 * Class representing a lexical analyzer.
 */
public class Lexer {

    /**
     * Input text data.
     */
    private final char[] data;

    /**
     * Current token.
     */
    private Token token;

    /**
     * Index of the first unprocessed symbol.
     */
    private int currentIndex;

    /**
     * Current lexer state.
     */
    private LexerState state = LexerState.BASIC;

    /**
     * Constructs Lexer by input text that is going to be tokenized.
     * @param text Input text data
     */
    public Lexer(String text) {
        if (text == null) {
            throw new NullPointerException("Text cant be null!");
        }

        this.data = text.toCharArray();
        this.token = null;
        this.currentIndex = 0;
    }

    /**
     * Generates and return the next token from the input text.
     * @return The next token
     */
    public Token nextToken() {
        while (true) {
            if (this.currentIndex < this.data.length && this.isWhitespace(this.data[this.currentIndex])) {
                this.currentIndex++;
                continue;
            }

            break;
        }

        if (this.token != null && this.token.getType() == TokenType.EOF) {
            throw new LexerException("No more tokens left!");
        }

        if (this.currentIndex >= this.data.length) {
            this.token = new Token(TokenType.EOF, null);
            return this.token;
        }

        if (this.state == LexerState.EXTENDED) {
            StringBuilder sb = new StringBuilder();

            if (this.data[this.currentIndex] == '#') {
                this.token = new Token(TokenType.SYMBOL, '#');
                this.currentIndex++;
                return this.token;
            }

            while (true) {
                if (this.currentIndex >= this.data.length) {
                    break;
                }

                if (this.data[this.currentIndex] != '#' && !this.isWhitespace(this.data[this.currentIndex])) {
                    sb.append(this.data[this.currentIndex]);
                    this.currentIndex++;
                    continue;
                }

                break;
            }

            this.token = new Token(TokenType.WORD, sb.toString());
            return this.token;
        }

        if (Character.isLetter(this.data[this.currentIndex]) || this.data[this.currentIndex] == '\\') {
            StringBuilder sb = new StringBuilder();

            while (true) {
                if (this.currentIndex >= this.data.length) {
                    break;
                }

                if (Character.isLetter(this.data[this.currentIndex])) {
                    sb.append(this.data[this.currentIndex]);
                    this.currentIndex++;
                    continue;
                }

                if (this.data[this.currentIndex] == '\\') {
                    this.checkEscape();
                    sb.append(this.data[this.currentIndex + 1]);
                    this.currentIndex += 2;
                    continue;
                }

                break;
            }

            this.token = new Token(TokenType.WORD, sb.toString());
            return this.token;
        }

        if (Character.isDigit(this.data[this.currentIndex])) {
            StringBuilder sb = new StringBuilder();

            while (true) {
                if (this.currentIndex >= this.data.length) {
                    break;
                }

                if (Character.isDigit(this.data[this.currentIndex])) {
                    sb.append(this.data[this.currentIndex]);
                    this.currentIndex++;
                    continue;
                }

                break;
            }

            try {
                this.token = new Token(TokenType.NUMBER, Long.parseLong(sb.toString()));
                return this.token;
            } catch (Exception e) {
                throw new LexerException("Invalid number format!");
            }
        }

        if (!Character.isDigit(this.data[this.currentIndex]) && !Character.isLetter(this.data[this.currentIndex])) {
            this.token = new Token(TokenType.SYMBOL, this.data[this.currentIndex]);
            this.currentIndex++;
            return this.token;
        }

        throw new LexerException("Invalid input!");
    }

    /**
     * Returns the token that was lastly generated.
     * @return Last generated token
     */
    public Token getToken() {
        return this.token;
    }

    /**
     * Setter for the lexer state.
     * @param state State for lexer to be set
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException("State cant be null!");
        }

        this.state = state;
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
     * Function to check if escape is correctly used.
     */
    private void checkEscape() {
        if (this.data[this.currentIndex] == '\\' && this.currentIndex + 1 >= this.data.length ||
                this.data[this.currentIndex] == '\\' &&
                        !(this.data[this.currentIndex + 1] == '\\' || Character.isDigit(this.data[this.currentIndex + 1]))) {
            throw new LexerException("Invalid escape!");
        }
    }

}
