package hr.fer.oprpp1.custom.scripting.lexer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SmartScriptLexerTest {

    @Test
    public void testNotNull() {
        assertNotNull(new SmartScriptLexer("").nextToken());
    }

    @Test
    public void testNullInput() {
        assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
    }

    @Test
    public void testEmpty() {
        SmartScriptLexer lexer = new SmartScriptLexer("");

        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testGetReturnsLastNext() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        SmartScriptToken token = lexer.nextToken();

        assertEquals(token, lexer.getToken());
        assertEquals(token, lexer.getToken());
    }

    @Test
    public void testRadAfterEOF() {
        SmartScriptLexer lexer = new SmartScriptLexer("");
        lexer.nextToken();

        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testWhitespacesText() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    ");

        assertEquals(SmartScriptTokenType.STRING_BASIC, lexer.nextToken().getType());
        assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
    }

    @Test
    public void testText() {
        SmartScriptLexer lexer = new SmartScriptLexer("  Štefanija\r\n\t Automobil   ");
        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.STRING_BASIC, "  Štefanija\r\n\t Automobil   "),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testWordStartingWithEscape() {
        SmartScriptLexer lexer = new SmartScriptLexer("  \\{1st  \r\n\t   ");
        SmartScriptToken correctData[] = {
                new SmartScriptToken(SmartScriptTokenType.STRING_BASIC, "  {1st  \r\n\t   "),
                new SmartScriptToken(SmartScriptTokenType.EOF, null)
        };

        checkTokenStream(lexer, correctData);
    }

    @Test
    public void testInvalidEscapeEnding() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\");

        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testInvalidEscape() {
        SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

        assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
    }

    @Test
    public void testTagStart() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$");
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        assertEquals("{$", token.getValue());
    }

    @Test
    public void testTagEnd() {
        SmartScriptLexer lexer = new SmartScriptLexer("$}");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.TAG_END, token.getType());
        assertEquals("$}", token.getValue());
    }

    @Test
    public void testStringBasic() {
        SmartScriptLexer lexer = new SmartScriptLexer("This is a string.");
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.STRING_BASIC, token.getType());
        assertEquals("This is a string.", token.getValue());
    }

    @Test
    public void testStringTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("\"This is a string.\"");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.STRING_TAG, token.getType());
        assertEquals("This is a string.", token.getValue());
    }

    @Test
    public void testTagStartEscape() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$$");
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        assertEquals("{$", token.getValue());

        token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.STRING_BASIC, token.getType());
        assertEquals("$", token.getValue());
    }

    @Test
    public void testInteger() {
        SmartScriptLexer lexer = new SmartScriptLexer("42");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(42, token.getValue());
    }

    @Test
    public void testDouble() {
        SmartScriptLexer lexer = new SmartScriptLexer("3.14");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.DOUBLE, token.getType());
        assertEquals(3.14, (double) token.getValue());
    }

    @Test
    public void testValidIdentifier() {
        SmartScriptLexer lexer = new SmartScriptLexer("variable123");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("variable123", token.getValue());
    }

    @Test
    public void testOperator() {
        SmartScriptLexer lexer = new SmartScriptLexer("+");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.OPERATOR, token.getType());
        assertEquals('+', token.getValue());
    }

    @Test
    public void testFunction() {
        SmartScriptLexer lexer = new SmartScriptLexer("@functionName");
        lexer.setState(SmartScriptLexerState.TAG);
        SmartScriptToken token = lexer.nextToken();

        assertEquals(SmartScriptTokenType.FUNCTION, token.getType());
        assertEquals("functionName", token.getValue());
    }

    private void checkTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
        int counter = 0;
        for (SmartScriptToken expected : correctData) {
            SmartScriptToken actual = lexer.nextToken();
            assertEquals(expected.getType(), actual.getType());
            assertEquals(expected.getValue(), actual.getValue());
            counter++;
        }
    }

    @Test
    public void testForLoopTag() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 0 5 2 $}");

        SmartScriptToken token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        lexer.setState(SmartScriptLexerState.TAG);

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("FOR", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("i", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(0, token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(5, token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(2, token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_END, token.getType());
    }

    @Test
    public void testForLoopTagWithoutStep() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i 0 5 $}");

        SmartScriptToken token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        lexer.setState(SmartScriptLexerState.TAG);

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("FOR", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("i", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(0, token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.INTEGER, token.getType());
        assertEquals(5, token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_END, token.getType());
    }

    @Test
    public void testEchoTagVariable() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$= i $}");

        SmartScriptToken token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        lexer.setState(SmartScriptLexerState.TAG);

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("=", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("i", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_END, token.getType());
    }

    @Test
    public void testEchoTagExpression() {
        SmartScriptLexer lexer = new SmartScriptLexer("{$= i i * $}");

        SmartScriptToken token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_START, token.getType());
        lexer.setState(SmartScriptLexerState.TAG);

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("=", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("i", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.IDENTIFIER, token.getType());
        assertEquals("i", token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.OPERATOR, token.getType());
        assertEquals('*', token.getValue());

        token = lexer.nextToken();
        assertEquals(SmartScriptTokenType.TAG_END, token.getType());
    }

}
